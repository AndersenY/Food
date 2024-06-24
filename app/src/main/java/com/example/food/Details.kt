package com.example.food

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.food.Models.PopularModel
import com.example.food.Models.SharedModel
import com.example.food.databinding.ActivityDeatailsBinding
import com.example.food.databinding.ActivityDetailsBinding
import com.example.food.databinding.FragmentSearchBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.OrderFoodItemCrossRef
import com.example.food.db.tables.Orders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Details : AppCompatActivity() {

    private lateinit var binding: ActivityDeatailsBinding
    private lateinit var sharedModel: SharedModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeatailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backStack.setOnClickListener{
            onBackPressed()
        }

        sharedModel = ViewModelProvider(this@Details).get(SharedModel::class.java)
        val currentList = sharedModel.cartItem.value
        val currentCartItem = intent.getSerializableExtra("current_cart_items") as ArrayList<PopularModel>?
        Log.d("DetailsActivity", "Current cart items: $currentCartItem")
        Log.d("DetailsActivity", "Number of items in current cart: ${currentCartItem?.size}")


//        currentCartItem.forEach{
//
//        }
//        currentCartItem.forEach{
//            binding.login.text = it.getFoodName()
//        }

//        if(currentCartItem == null){
//            Toast.makeText(this@Details, "No000", Toast.LENGTH_SHORT).show()
//        }
//        else{
//            Toast.makeText(this@Details, "yeah", Toast.LENGTH_SHORT).show()
//        }

        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val login = sharedPreferences.getString("login", "").toString()
        val password = sharedPreferences.getString("password", "").toString()


        val db = MainDb.getDb(this)
        binding.login.text = login


        lifecycleScope.launch {
            val user = db.getDao().getUser(login, password)

            binding.phone.text = user?.phone.toString()
            binding.address.text = user?.address.toString()
            var totalPrice = intent.getStringExtra("TotalPrice")
            val editor = sharedPreferences.edit()
            editor.putString("totalPrice", totalPrice) // сохраняем имя пользователя или другие данные
            editor.apply()
            if(totalPrice == "null")
                totalPrice="0"
            binding.textPrice.text = totalPrice?.let { addRubleSymbol(it) }

            binding.placeMyOrder.setOnClickListener{

                val sharedPreferences = this@Details.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val login = sharedPreferences.getString("login", "").toString()
                val password = sharedPreferences.getString("password", "").toString()
                val totalPrice = sharedPreferences.getString("totalPrice", "").toString()

                lifecycleScope.launch {
                    if (currentCartItem != null) {
                        val db = MainDb.getDb(this@Details)
                        val user = db.getDao().getUser(login, password)

                        val order = Orders(
                            null,
                            user?.id,
                            totalPrice.toInt()
                        )

                        val orderId = db.getDao().insertOrder(order)

                        currentCartItem.forEach {

                                val foodItem = db.getDao().getFoodItem(it.getFoodName())
                                val orderFoodItem = OrderFoodItemCrossRef(
                                    null,
                                    orderId.toInt(),
                                    foodItem.id,
                                    it.getFoodCount()
                                )

                                db.getDao().insertOrderFoodItemCrossRef(orderFoodItem)
                        }
                    }
                    else {
                        Toast.makeText(this@Details, "No items in the cart", Toast.LENGTH_SHORT).show()
                    }
                }
                val bottomFragment = SuccessPayment()
                bottomFragment.show(supportFragmentManager, "Test")
            }

        }

        binding.changeData.setOnClickListener {
            val intent = Intent(this@Details, MainActivity::class.java)
            intent.putExtra("open_fragment", "ProfileFragment")
            startActivity(intent)
        }


    }

    fun addRubleSymbol(input: String): String {
        return "$input ₽"
    }

}