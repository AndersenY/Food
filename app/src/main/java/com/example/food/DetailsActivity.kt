package com.example.food

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.food.Models.PopularModel
import com.example.food.databinding.ActivityDetailsBinding
import com.example.food.db.MainDb
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val foodImage = intent.getIntExtra("foodImage", 0)
        val foodName = intent.getStringExtra("foodName")

        val db = MainDb.getDb(this)

        lifecycleScope.launch {
            val foodItem = db.getDao().getFoodItem(foodName.toString())
            binding.menuDFoodName.text = foodName
            binding.menuDFoodDescription.text = foodItem.description
            binding.menuDFoodIngridients.text = formatString(foodItem.ingridients)

            binding.backHome.setOnClickListener{
                finish()
            }

        }

//        binding.menuDFoodImage.setImageResource(foodImage)

    }
    fun formatString(input: String): String {
        val items = input.split(",").map { it.trim() }
        return items.joinToString(separator = "\n") { "• $it" }
    }
}