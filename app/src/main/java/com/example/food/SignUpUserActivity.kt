package com.example.food

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.databinding.ActivitySignUpUserBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = MainDb.getDb(this)


        binding = ActivitySignUpUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.goLoginUserPage.setOnClickListener{
            val intent = Intent( this@SignUpUserActivity, LoginUserActivity::class.java)
            startActivity(intent)
        }



        binding.button3.setOnClickListener {
            val login = binding.signUpUserName.text.toString()
            val password = binding.signInPassword.text.toString()

            GlobalScope.launch(Dispatchers.Main) {
                val user = withContext(Dispatchers.IO) {
                    db.getDao().getUser(login, password)
                }
                if (user != null) {
                    Toast.makeText(this@SignUpUserActivity, "Пользователь уже существует", Toast.LENGTH_SHORT).show()
                } else {
                    val new_user = User(
                        null,
                        binding.signUpUserName.text.toString(),
                        binding.signInPassword.text.toString(),
                        binding.signInUserEmail.text.toString(),
                        "",
                        "",
                        false
                    )
                    withContext(Dispatchers.IO) {
                        db.getDao().insertUser(new_user)
                    }
                    Toast.makeText(this@SignUpUserActivity, "Пользователь успешно зарегистрирован", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@SignUpUserActivity, LoginUserActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}