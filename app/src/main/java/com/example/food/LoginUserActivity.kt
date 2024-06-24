package com.example.food

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.admin.MainActivityAdmin
import com.example.food.databinding.ActivityLoginUserBinding
import com.example.food.db.MainDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goSignUpUser.setOnClickListener{
            val intent = Intent(this@LoginUserActivity, SignUpUserActivity::class.java)
            startActivity(intent)
        }
//        applicationContext.deleteDatabase("test.db")
        val db = MainDb.getDb(this)

//        lifecycleScope.launch {
//
//            val foodItem = FoodItem(
//                name = "Burger",
//                description = "Tasty burger description",
//                ingridients = "Жопа, meat, lettuce, tomato, cheese",
//                price = 500
//            )
//
//            db.getDao().insertFoodItem(foodItem)
//
//        }



        binding.button3.setOnClickListener {
            val login = binding.signInUserLoginn.text.toString()
            val password = binding.signInPassword.text.toString()

            GlobalScope.launch(Dispatchers.Main) {
                val user = db.getDao().getUser(login, password)
                if (user != null) {
                    val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("login", login) // сохраняем имя пользователя или другие данные
                    editor.putString("password", password)
                    editor.apply()

                    if(user.isAdmin == false){
                        val intent = Intent(this@LoginUserActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        val intent = Intent(this@LoginUserActivity, MainActivityAdmin::class.java)
                        startActivity(intent)
                        finish()
                    }

                } else {
                    Toast.makeText(this@LoginUserActivity, "Пользователь с такими данными не найден", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}