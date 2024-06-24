package com.example.food.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.food.db.tables.FoodItem
import com.example.food.db.tables.OrderFoodItemCrossRef
import com.example.food.db.tables.Orders
import com.example.food.db.tables.Restaurant
import com.example.food.db.tables.User

@Dao
interface Dao {
    @Insert
    suspend fun insertUser(item: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers() : List<User>

    @Query("SELECT * FROM users WHERE isAdmin = 0")
    suspend fun getAllUsersWirhoutAdmins() : List<User>

    @Delete
    suspend fun deleteUser(item: User)

    @Query("SELECT * FROM users WHERE isAdmin = 1")
    suspend fun getAllAdmins() : List<User>

    @Query("SELECT * FROM users WHERE login = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?

    @Query("UPDATE users SET login = :login, password = :password, email = :email, address = :address, phone = :phone WHERE login = :last_login and password = :last_password")
    suspend fun updateUser(last_login: String, last_password: String, login: String, password: String, email: String, address: String, phone: String)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)


    //************************************************************************************

    @Insert
    suspend fun insertRestaurant(item: Restaurant)

    @Query("SELECT * FROM restaurants")
    suspend fun getAllRestaurants() : List<Restaurant>

    @Delete
    suspend fun deleteRestaurant(item: Restaurant)

    //************************************************************************************

    @Query("SELECT * FROM FoodItem")
    suspend fun getAllfoodItems() : List<FoodItem>

    @Insert
    suspend fun insertFoodItem(foodItem: FoodItem)

    @Query("SELECT * FROM FoodItem WHERE name = :foodName")
    suspend fun getFoodItem(foodName: String): FoodItem

    @Query("DELETE FROM fooditem WHERE id = :foodId")
    suspend fun deleteFoodItemById(foodId: Int)

    //************************************************************************************
    @Insert
    suspend fun insertOrder(order: Orders): Long

    @Insert
    suspend fun insertOrderFoodItemCrossRef(crossRef: OrderFoodItemCrossRef): Long

    //************************************************************************************
    @Query("SELECT * FROM orders WHERE user_id = :userId")
    suspend fun getOrderByUserId(userId: Int?): List<Orders>

    @Query("SELECT * FROM order_food_item WHERE order_id = :orderId")
    suspend fun getOrderFoodItemsByOrderId(orderId: Int?): List<OrderFoodItemCrossRef>

    @Query("SELECT * FROM orders")
    fun getAllOrders(): LiveData<List<Orders>>

    @Delete
    suspend fun deleteOrder(order: Orders)

    @Query("DELETE FROM orders WHERE user_id = :userId")
    suspend fun deleteOrdersByUserId(userId: Int)

    @Query("DELETE FROM orders WHERE id = :orderId")
    suspend fun deleteOrderById(orderId: Int)

    @Query("SELECT id FROM orders WHERE user_id = :userId")
    suspend fun getOrderIdsByUserId(userId: Int): List<Int>


    //************************************************************************************
    @Query("DELETE FROM order_food_item WHERE order_id = :orderId")
    suspend fun deleteOrderFoodItemById(orderId: Int)


}