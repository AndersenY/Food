package com.example.food.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Adapters.PopularAdapter
import com.example.food.Models.PopularModel
import com.example.food.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var adapter: PopularAdapter
    private lateinit var menuList: ArrayList<PopularModel>
    private lateinit var menuRv : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_bottom_sheet, container, false)

        adapter= PopularAdapter(requireContext(), menuList)

        menuList = ArrayList()
//        menuList.add(PopularModel(R.drawable.menu_burger, "Burger",500, 500, 1))
//        menuList.add(PopularModel(R.drawable.menu_burger, "1Burger",500, 500, 1))
//        menuList.add(PopularModel(R.drawable.menu_burger, "2Burger",500, 500, 1))
//        menuList.add(PopularModel(R.drawable.menu_burger, "3Burger",500, 500, 1))
//        menuList.add(PopularModel(R.drawable.menu_burger, "4Burger",500, 500, 1))
//        menuList.add(PopularModel(R.drawable.menu_burger, "5Burger",500, 500, 1))
//        menuList.add(PopularModel(R.drawable.menu_burger, "6Burger",500, 500, 1))
//        menuList.add(PopularModel(R.drawable.menu_burger, "7Burger",500, 500, 1))
//        menuList.add(PopularModel(R.drawable.menu_burger, "8Burger",500, 500, 1))
//        menuList.add(PopularModel(R.drawable.menu_burger, "9Burger",500, 500, 1))


        menuRv = view.findViewById(R.id.menu_RV)
        menuRv.layoutManager = LinearLayoutManager(requireContext())
        menuRv.adapter = adapter

        return view
    }

}