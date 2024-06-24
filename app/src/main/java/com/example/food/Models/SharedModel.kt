package com.example.food.Models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedModel : ViewModel() {
    val cartItem = MutableLiveData<ArrayList<PopularModel>>()
    val buttonStates = ArrayList<Boolean>()
    val login = String

    fun getButtonStates() : List<Boolean>{
        return buttonStates
    }

    fun addToCart(item : PopularModel){
        val currentCartItem = cartItem.value ?: ArrayList<PopularModel>()
        currentCartItem.add(item)
        cartItem.value = currentCartItem
        buttonStates.add(true)
    }

    fun deleteFromCart(item : PopularModel){
        val currentCarItem = cartItem.value ?: ArrayList<PopularModel>()
        val index = currentCarItem.indexOf(item)
        if(index != -1){
            currentCarItem.removeAt(index)
            cartItem.value = currentCarItem
            buttonStates.removeAt(index)
        }

    }

    fun inList(item : PopularModel) : Boolean{
        val currentCartItem = cartItem.value ?: ArrayList<PopularModel>()
        currentCartItem.contains(item)
        return currentCartItem.contains(item)
    }
}