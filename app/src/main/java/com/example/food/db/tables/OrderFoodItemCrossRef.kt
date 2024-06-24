package com.example.food.db.tables
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_food_item")
data class OrderFoodItemCrossRef(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "order_id")
    val orderId: Int?,
    @ColumnInfo(name = "food_item_id")
    val foodItemId: Int?,
    @ColumnInfo(name = "count")
    val count: Int
)
