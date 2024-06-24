package com.example.food.Models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.Serializable

class OrderModel : Serializable {
    private var userId : Int = 0
    private var orderNumber : Int = 0
    private var totalPrice : Int = 0

    constructor()
    constructor(userId:  Int, totalPrice: Int, orderNumber : Int) {
        this.userId = userId
        this.totalPrice = totalPrice
        this.orderNumber = orderNumber
    }

    fun getuserId() : Int{
        return userId
    }

    fun gettotalPrice() : Int{
        return totalPrice
    }

    fun getOrderNumber() : Int{
        return  orderNumber
    }

    fun setuserId(userId: Int){
        this.userId = userId
    }

    fun settotalPrice(totalPrice: Int){
        this.totalPrice = totalPrice
    }

    fun setOrderNumber(orderNumber: Int){
        this.orderNumber = orderNumber
    }

}