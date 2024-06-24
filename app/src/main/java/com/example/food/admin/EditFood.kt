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
import com.example.food.Adapters.FoodItemAdapter
import com.example.food.R
import com.example.food.databinding.ActivityEditFoodBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.FoodItem
import kotlinx.coroutines.launch

class EditFood : AppCompatActivity(), FoodItemAdapter.OnDeleteClickListener {

    private lateinit var binding: ActivityEditFoodBinding
    private lateinit var foodItemAdapter: FoodItemAdapter
    private lateinit var listFoodItems: ArrayList<FoodItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = MainDb.getDb(this)
        listFoodItems = ArrayList()

        lifecycleScope.launch {
            val foodItems = db.getDao().getAllfoodItems()

            listFoodItems.addAll(foodItems)
            foodItemAdapter.notifyDataSetChanged()
        }

        foodItemAdapter = FoodItemAdapter(this, listFoodItems)
        foodItemAdapter.setOnDeleteClickListener(this)
        binding.foodRV.layoutManager = LinearLayoutManager(this)
        binding.foodRV.adapter = foodItemAdapter

        binding.addFood.setOnClickListener {
            val intent = Intent(this@EditFood, AddFood::class.java)
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
                val db = MainDb.getDb(this@EditFood)
                val foodDao = db.getDao()

                val foodItemToDelete = listFoodItems[position]

                foodItemToDelete.id?.let { foodItemId -> db.getDao().deleteFoodItemById(foodItemId) }
                Toast.makeText(this@EditFood, "Блюдо успешно удалено", Toast.LENGTH_SHORT).show()

                listFoodItems.remove(foodItemToDelete) // Удаление элемента из списка
                foodItemAdapter.notifyDataSetChanged() // Обновление адаптера

                dialogBuilder.dismiss()
            }
        }

        dialogView.findViewById<AppCompatButton>(R.id.btn_consel)?.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }
}