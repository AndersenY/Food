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
import com.example.food.databinding.ActivityAddUserBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.User
import kotlinx.coroutines.launch

class AddUser : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MainDb.getDb(this)

        binding.addUser.setOnClickListener {
            lifecycleScope.launch {
                val loginText = binding.login.text.toString()
                val passwordText = binding.password.text.toString()
                val emailText = binding.email.text.toString()
                val addressText = binding.address.text.toString()
                val phoneText = binding.phone.text.toString()

                val user = db.getDao().getUser(loginText, passwordText)

                if(user == null){
                    if (loginText.isBlank()) {
                        Toast.makeText(this@AddUser, "Введите логин", Toast.LENGTH_SHORT).show()
                    } else if (passwordText.isBlank()) {
                        Toast.makeText(this@AddUser, "Введите пароль", Toast.LENGTH_SHORT).show()
                    } else if (emailText.isBlank()) {
                        Toast.makeText(this@AddUser, "Введите email", Toast.LENGTH_SHORT).show()
                    } else if (addressText.isBlank()) {
                        Toast.makeText(this@AddUser, "Введите адрес", Toast.LENGTH_SHORT).show()
                    } else if (phoneText.isBlank()) {
                        Toast.makeText(this@AddUser, "Введите номер телефона", Toast.LENGTH_SHORT).show()
                    } else {
                        val newUser = User(
                            null,
                            loginText,
                            passwordText,
                            emailText,
                            addressText,
                            phoneText,
                            false
                        )

                        db.getDao().insertUser(newUser)

                        Toast.makeText(this@AddUser, "Пользователь успешно создан", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@AddUser, EditUsers::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else{
                    Toast.makeText(this@AddUser, "Пользователь уже существует", Toast.LENGTH_SHORT).show()
                }

            }
        }

        binding.backStack.setOnClickListener {
            val intent = Intent(this@AddUser, EditUsers::class.java)
            startActivity(intent)
            finish()
        }


    }
}