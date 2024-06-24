package com.example.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adapters.PopularAdapter
import com.example.food.Models.PopularModel
import com.example.food.Models.SharedModel
import com.example.food.databinding.FragmentSearchBinding
import com.example.food.db.MainDb
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var adapter : PopularAdapter
    private lateinit var list : ArrayList<PopularModel>
    private lateinit var sharedModel : SharedModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        sharedModel = ViewModelProvider(
            requireActivity()
        ).get(SharedModel :: class.java)

        val db = MainDb.getDb(requireContext())
        list = ArrayList()

        lifecycleScope.launch {
            val foodItems = db.getDao().getAllfoodItems()

            foodItems.forEach { foodItem ->

                list.add(PopularModel(foodItem.name, foodItem.price, foodItem.price, 1))

            }

            adapter.notifyDataSetChanged()

        }



        adapter= PopularAdapter(requireContext(), list)
        adapter.setSharedModel(sharedModel)
        binding.searchMenuRv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchMenuRv.adapter = adapter

        searchMenuFood()

        return binding.root
    }

    private fun searchMenuFood(){

        binding.searchMenuItem.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

    }


    private fun filterList(input : String?){
        val filteredList = if(input.isNullOrEmpty()){
            list
        }
        else{
            list.filter{item ->
                item.getFoodName().contains(input, ignoreCase = true)
            }
        }

        adapter.updateList(filteredList as ArrayList<PopularModel>)
    }
}