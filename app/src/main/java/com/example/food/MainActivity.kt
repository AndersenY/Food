package com.example.food

import HistoryFragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.food.databinding.ActivityMainBinding
import com.example.food.databinding.ActivityStartBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val openFragment = intent.getStringExtra("open_fragment")
        if (openFragment == "ProfileFragment") {
            changeFragment(ProfileFragment())
            binding.bottomNavigationView.selectedItemId = R.id.profile
        } else {
            changeFragment(HomeFragment())
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    changeFragment(HomeFragment())
                }

                R.id.cart -> {
                    changeFragment(CartFragment())
                }

                R.id.search -> {
                    changeFragment(SearchFragment())
                }

                R.id.history -> {
                    changeFragment(HistoryFragment())
                }

                R.id.profile -> {
                    changeFragment(ProfileFragment())
                }
            }



            return@setOnItemSelectedListener true
        }
    }
    fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =  fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}