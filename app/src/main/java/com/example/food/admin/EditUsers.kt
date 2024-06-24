package com.example.food.admin

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
import com.example.food.R
import com.example.food.databinding.ActivityEditUsersBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.User
import kotlinx.coroutines.launch

class EditUsers : AppCompatActivity(), UserAdapter.OnDeleteClickListener {

    private lateinit var binding: ActivityEditUsersBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var listUsers: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = MainDb.getDb(this)
        listUsers = ArrayList()

        lifecycleScope.launch {
            val users = db.getDao().getAllUsersWirhoutAdmins()

            listUsers.addAll(users)
            userAdapter.notifyDataSetChanged()
        }

        userAdapter = UserAdapter(this, listUsers)
        userAdapter.setOnDeleteClickListener(this)
        binding.userRV.layoutManager = LinearLayoutManager(this)
        binding.userRV.adapter = userAdapter

        binding.addUser.setOnClickListener {
            val intent = Intent(this@EditUsers, AddUser::class.java)
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
                val db = MainDb.getDb(this@EditUsers)
                val userDao = db.getDao()

                val userToDelete = listUsers[position]

                val orders = db.getDao().getOrderByUserId(userToDelete.id)
                orders.forEach {
                    it.id?.let { orderId -> db.getDao().deleteOrderFoodItemById(orderId) }
                }
                userToDelete.id?.let { userId -> db.getDao().deleteOrdersByUserId(userId) }

                userDao.deleteUserById(userToDelete.id ?: return@launch)
                Toast.makeText(this@EditUsers, "Аккаунт успешно удален", Toast.LENGTH_SHORT).show()

                listUsers.remove(userToDelete) // Удаление элемента из списка
                userAdapter.notifyDataSetChanged() // Обновление адаптера

                dialogBuilder.dismiss()
            }
        }

        dialogView.findViewById<AppCompatButton>(R.id.btn_consel)?.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }
}