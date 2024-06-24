package com.example.food.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Models.PopularModel
import com.example.food.databinding.CartAddItemBinding
import com.example.food.databinding.HomeFoodItemBinding

class CardAdapter(
    val context : Context,
    var list : ArrayList<PopularModel>
    ):RecyclerView.Adapter<CardAdapter.CartViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.CartViewHolder {
        val binding = CartAddItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardAdapter.CartViewHolder, position: Int) {
        val listModel = list[position]

        holder.foodName.text = listModel.getFoodName()
        holder.foodPrice.text = addRubleSymbol(listModel.getFoodPrice().toString())
//        listModel.getFoodImage()?.let { holder.foodImage.setImageResource(it) }


        holder.foodCount.text = listModel.getFoodCount().toString()
        holder.plus.setOnClickListener{
            if(listModel.getFoodCount() < 10){
                val count = listModel.getFoodCount()+1
                listModel.setFoodCount(count)
                val price = listModel.getFoodPriceConstant() * listModel.getFoodCount()
                listModel.setFoodPrice(price)
                holder.foodPrice.text = addRubleSymbol(listModel.getFoodPrice().toString())
                holder.foodCount.text = listModel.getFoodCount().toString()
            }
        }

        holder.minus.setOnClickListener{
            if(listModel.getFoodCount() > 1){
                val count = listModel.getFoodCount() - 1
                listModel.setFoodCount(count)
                val price = listModel.getFoodPriceConstant() * listModel.getFoodCount()
                listModel.setFoodPrice(price)
                holder.foodPrice.text = addRubleSymbol(listModel.getFoodPrice().toString())
                holder.foodCount.text = listModel.getFoodCount().toString()
            }
            else{
                holder.bindItem()
            }
        }

        holder.deleteBtn.setOnClickListener{
            holder.bindItem()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CartViewHolder(binding: CartAddItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val foodImage = binding.homeFoodImage
        val foodName = binding.homeFoodName
        val foodPrice = binding.homeFoodPrice
        val foodCount = binding.foodCount


        val plus = binding.plusBtn
        val minus = binding.minusBtn
        val deleteBtn = binding.deleteBtn

        fun bindItem(){
            if (adapterPosition != RecyclerView.NO_POSITION){
                deleteItem(adapterPosition)
            }
        }

        fun deleteItem(position : Int){
            if (position in 0..list.size){
                list.removeAt(position)
                notifyDataSetChanged()
                notifyItemRangeChanged(position, list.size)
            }
        }
    }
    fun addRubleSymbol(input: String): String {
        return "$input ₽"
    }

}