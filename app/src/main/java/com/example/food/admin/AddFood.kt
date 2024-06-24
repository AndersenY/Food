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
import com.example.food.databinding.ActivityAddFoodBinding
import com.example.food.databinding.ActivityAddUserBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.FoodItem
import com.example.food.db.tables.User
import kotlinx.coroutines.launch

class AddFood : AppCompatActivity() {

    private lateinit var binding: ActivityAddFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_food)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MainDb.getDb(this)

        binding.addFooditem.setOnClickListener {
            lifecycleScope.launch {
                val nameText = binding.name.text.toString()
                val descriptionText = binding.description.text.toString()
                val ingredientsText = binding.ingredients.text.toString()
                val priceText = binding.price.text.toString()

                val item = db.getDao().getFoodItem(nameText)

                if(item == null){
                    if (nameText.isBlank()) {
                        Toast.makeText(this@AddFood, "Введите название", Toast.LENGTH_SHORT).show()
                    } else if (descriptionText.isBlank()) {
                        Toast.makeText(this@AddFood, "Введите описание", Toast.LENGTH_SHORT).show()
                    } else if (ingredientsText.isBlank()) {
                        Toast.makeText(this@AddFood, "Введите ингредиенты", Toast.LENGTH_SHORT).show()
                    } else {
                        val price = priceText.toIntOrNull()

                        if (price == null) {
                            Toast.makeText(this@AddFood, "Введите корректную цену", Toast.LENGTH_SHORT).show()
                        } else {
                            val new_food = FoodItem(
                                null,
                                nameText,
                                descriptionText,
                                ingredientsText,
                                price
                            )

                            db.getDao().insertFoodItem(new_food)

                            Toast.makeText(this@AddFood, "Позиция успешно создана", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@AddFood, EditFood::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    Toast.makeText(this@AddFood, "Блюдо уже существует", Toast.LENGTH_SHORT).show()
                }

            }

        }

        binding.backStack.setOnClickListener {
            val intent = Intent(this@AddFood, EditFood::class.java)
            startActivity(intent)
            finish()
        }
    }
}