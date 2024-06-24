package com.example.food.admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.LoginUserActivity
import com.example.food.R
import com.example.food.databinding.ActivityMainAdmin2Binding

class MainActivityAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityMainAdmin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_admin2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainAdmin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.users.setOnClickListener {
            val intent = Intent(this@MainActivityAdmin, EditUsers::class.java)
            startActivity(intent)
        }

        binding.admins.setOnClickListener {
            val intent = Intent(this@MainActivityAdmin, EditAdmins::class.java)
            startActivity(intent)
        }

        binding.food.setOnClickListener {
            val intent = Intent(this@MainActivityAdmin, EditFood::class.java)
            startActivity(intent)
        }

        binding.exit.setOnClickListener {
            val intent = Intent(this@MainActivityAdmin, LoginUserActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}