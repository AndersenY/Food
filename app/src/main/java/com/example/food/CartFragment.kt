package com.example.food

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adapters.CardAdapter
import com.example.food.Models.PopularModel
import com.example.food.Models.SharedModel
import com.example.food.databinding.FragmentCartBinding
import com.example.food.databinding.FragmentSearchBinding


class CartFragment : Fragment() {

    private lateinit var binding : FragmentCartBinding
//    private lateinit var list: ArrayList<PopularModel>
    private lateinit var adapter : CardAdapter
    private lateinit var sharedModel : SharedModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        sharedModel = ViewModelProvider(
            requireActivity()
        ).get(SharedModel :: class.java)

        val currentCartItem = sharedModel.cartItem.value ?: ArrayList<PopularModel>()

        adapter = CardAdapter(requireContext(), sharedModel.cartItem.value ?: ArrayList())
        binding.cartRV.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRV.adapter = adapter

        binding.procceedBtn.setOnClickListener{
            val totalPrice = sharedModel.cartItem.value?.let { calPrice(it) } ?: 0

            if (totalPrice == 0) {
                Toast.makeText(requireContext(), "Пусто", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(requireContext(), Details::class.java)
                intent.putExtra("TotalPrice", totalPrice.toString())
                intent.putExtra("current_cart_items", currentCartItem)
                startActivity(intent)
            }
        }
        return binding.root
    }

    fun calPrice( itemPrices : List<PopularModel>) :Int{

        var totalPrice = 0
         itemPrices.forEach{ itemPrice ->
             val price = itemPrice.getFoodPriceConstant() * itemPrice.getFoodCount()
             totalPrice += price
         }

        return totalPrice
    }

}