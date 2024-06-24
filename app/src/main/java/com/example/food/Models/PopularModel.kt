package com.example.food.Models

import java.io.Serializable

class PopularModel : Serializable {
//    private var foodImage : String? = null
    private var foodName : String = ""
    private var foodPrice : Int = 0
    private var foodPriceConstant : Int = 0
    private var foodCount : Int = 1

    constructor()
    constructor(foodName: String, foodPrice: Int, foodPriceConstant: Int, foodCount: Int ) {
//        this.foodImage = foodImage
        this.foodName = foodName
        this.foodPrice = foodPrice
        this.foodCount = foodCount
        this.foodPriceConstant = foodPriceConstant
    }

//    fun getFoodImage() : String?{
//        return foodImage
//    }

    fun getFoodName() : String{
        return foodName
    }

    fun getFoodPrice() : Int{
        return foodPrice
    }

    fun getFoodPriceConstant() : Int{
        return foodPriceConstant
    }

    fun getFoodCount() : Int{
        return foodCount
    }

//    fun setFoodImage(foodImage: String?){
//        this.foodImage = foodImage
//    }

    fun setFoodName(foodName: String){
        this.foodName = foodName
    }

    fun setFoodPrice(foodPrice: Int){
        this.foodPrice = foodPrice
    }

    fun setFoodCount(foodCount: Int){
        this.foodCount = foodCount
    }

    fun setFoodPriceConstant(foodPrice: Int){
        this.foodPrice = foodPriceConstant
    }
}