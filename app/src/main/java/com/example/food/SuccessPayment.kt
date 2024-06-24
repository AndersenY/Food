package com.example.food

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.food.Models.SharedModel
import com.example.food.db.MainDb
import com.example.food.db.tables.OrderFoodItemCrossRef
import com.example.food.db.tables.Orders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import kotlin.math.log

class SuccessPayment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_success_payment, container, false)



        val reStart = view.findViewById<AppCompatButton>(R.id.return_home)
        reStart.setOnClickListener{

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }


        return view
    }
}