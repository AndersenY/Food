package com.example.food.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adapters.UserAdapter
import com.example.food.LoginUserActivity
import com.example.food.R
import com.example.food.databinding.ActivityEditAdminsBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.User
import kotlinx.coroutines.launch

class EditAdmins : AppCompatActivity(), UserAdapter.OnDeleteClickListener {

    private lateinit var binding: ActivityEditAdminsBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var listAdmins: ArrayList<User>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditAdminsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = MainDb.getDb(this)
        listAdmins = ArrayList()

        lifecycleScope.launch {
            val admins = db.getDao().getAllAdmins()

            listAdmins.addAll(admins)
            userAdapter.notifyDataSetChanged()
        }

        userAdapter = UserAdapter(this, listAdmins)
        userAdapter.setOnDeleteClickListener(this)
        binding.adminRV.layoutManager = LinearLayoutManager(this)
        binding.adminRV.adapter = userAdapter

        binding.addAdmin.setOnClickListener {
            val intent = Intent(this@EditAdmins, AddAdmin::class.java)
            startActivity(intent)
            finish()
        }

        binding.backStack.setOnClickListener {
            finish()
        }
    }

    override fun onDeleteClick(position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<AppCompatButton>(R.id.btn_yes)?.setOnClickListener {
            lifecycleScope.launch {
                val db = MainDb.getDb(this@EditAdmins)
                val userDao = db.getDao()

                val adminToDelete = listAdmins[position]

                userDao.deleteUserById(adminToDelete.id ?: return@launch)

                val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val login = sharedPreferences.getString("login", "").toString()
                val password = sharedPreferences.getString("password", "").toString()

                val user = userDao.getUser(login, password)
                if (user != null) {
                    if (adminToDelete.id == user.id) {
                        Toast.makeText(this@EditAdmins, "Ваш аккаунт администратора успешно удален", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@EditAdmins, LoginUserActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@EditAdmins, "Аккаунт администратора успешно удален", Toast.LENGTH_SHORT).show()

                        listAdmins.remove(adminToDelete) // Удаление элемента из списка
                        userAdapter.notifyDataSetChanged() // Обновление адаптера

                        dialogBuilder.dismiss()
                    }
                }
            }
        }

        dialogView.findViewById<AppCompatButton>(R.id.btn_consel)?.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }
}