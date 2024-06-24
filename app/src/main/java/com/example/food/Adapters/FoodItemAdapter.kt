package com.example.food.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.databinding.FoodItemAdminBinding
import com.example.food.db.tables.FoodItem

class FoodItemAdapter(
    private val context: Context,
    private var list: List<FoodItem>
) : RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>() {

    private var onDeleteClickListener: OnDeleteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val binding = FoodItemAdminBinding.inflate(LayoutInflater.from(context), parent, false)
        return FoodItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val foodItem = list[position]

        holder.foodName.text = foodItem.name
//        holder.foodDescription.text = foodItem.description
//        holder.foodIngredients.text = foodItem.ingridients
        holder.foodPrice.text = addRubleSymbol(foodItem.price.toString())

        holder.deleteBtn.setOnClickListener {
            onDeleteClickListener?.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FoodItemViewHolder(binding: FoodItemAdminBinding) : RecyclerView.ViewHolder(binding.root) {
        val foodName = binding.foodName
//        val foodDescription = binding.f
//        val foodIngredients = binding.foodIngredients
        val foodPrice = binding.foodPrice
        val deleteBtn = binding.deleteBtn
    }

    fun updateList(newList: List<FoodItem>) {
        list = newList
        notifyDataSetChanged()
    }

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int)
    }

    fun addRubleSymbol(input: String): String {
        return "$input ₽"
    }
}