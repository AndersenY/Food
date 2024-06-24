package com.example.food.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.food.R
import com.example.food.databinding.ActivityAddAdminBinding
import com.example.food.databinding.ActivityAddUserBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.User
import kotlinx.coroutines.launch

class AddAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityAddAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_admin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityAddAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MainDb.getDb(this)

        binding.addAdmin.setOnClickListener {
            lifecycleScope.launch {
                val loginText = binding.login.text.toString()
                val passwordText = binding.password.text.toString()
                val emailText = binding.email.text.toString()
                val addressText = binding.address.text.toString()
                val phoneText = binding.phone.text.toString()

                val user = db.getDao().getUser(loginText, passwordText)

                if(user == null){
                    if (loginText.isBlank()) {
                        Toast.makeText(this@AddAdmin, "Введите логин", Toast.LENGTH_SHORT).show()
                    } else if (passwordText.isBlank()) {
                        Toast.makeText(this@AddAdmin, "Введите пароль", Toast.LENGTH_SHORT).show()
                    } else if (emailText.isBlank()) {
                        Toast.makeText(this@AddAdmin, "Введите email", Toast.LENGTH_SHORT).show()
                    } else if (addressText.isBlank()) {
                        Toast.makeText(this@AddAdmin, "Введите адрес", Toast.LENGTH_SHORT).show()
                    } else if (phoneText.isBlank()) {
                        Toast.makeText(this@AddAdmin, "Введите номер телефона", Toast.LENGTH_SHORT).show()
                    } else {
                        val newAdmin = User(
                            null,
                            loginText,
                            passwordText,
                            emailText,
                            addressText,
                            phoneText,
                            true
                        )

                        db.getDao().insertUser(newAdmin)

                        Toast.makeText(this@AddAdmin, "Администратор успешно создан", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@AddAdmin, EditAdmins::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else{
                    Toast.makeText(this@AddAdmin, "Администратор уже существует", Toast.LENGTH_SHORT).show()
                }

            }

        }

        binding.backStack.setOnClickListener {
            val intent = Intent(this@AddAdmin, EditAdmins::class.java)
            startActivity(intent)
            finish()
        }
    }
}