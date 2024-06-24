package com.example.food.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.food.DetailsActivity
import com.example.food.Models.PopularModel
import com.example.food.Models.SharedModel
import com.example.food.databinding.HomeFoodItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class PopularAdapter(
    val context : Context,
    var list : ArrayList<PopularModel>
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    private lateinit var sharedModel : SharedModel

    fun setSharedModel(videoModel: SharedModel){
        sharedModel = videoModel
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
        val binding = HomeFoodItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val listModel = list[position]

        holder.foodName.text = listModel.getFoodName()
        holder.foodPrice.text = addRubleSymbol(listModel.getFoodPrice().toString())
//        val imageUrl = listModel.getFoodImage()
//        imageUrl?.let {
//            loadImageFromUrl(it, holder.foodImage)
//        }


        holder.item.setOnClickListener{
            val intent = Intent(context, DetailsActivity :: class.java)
//            intent.putExtra("foodImage", listModel.getFoodImage())
            intent.putExtra("foodName", listModel.getFoodName())
            context.startActivity(intent)
        }

        holder.addBtn.setOnClickListener{
            if(sharedModel.inList(listModel)){
                sharedModel.deleteFromCart(listModel)
                holder.addBtn.setText("Добавить заказ")
            }
            else{
                sharedModel.addToCart(listModel)
                holder.addBtn.setText("Убрать из заказа")
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PopularViewHolder (binding : HomeFoodItemBinding): RecyclerView.ViewHolder(binding.root) {
        val foodImage = binding.homeFoodImage
        val foodName = binding.homeFoodName
        val foodPrice = binding.homeFoodPrice
        val addBtn = binding.homeFoodBtn
        val item = binding.root
    }

    fun updateList(newList : ArrayList<PopularModel>){
        list = newList
        notifyDataSetChanged()
    }

    fun addRubleSymbol(input: String): String {
        return "$input ₽"
    }
}