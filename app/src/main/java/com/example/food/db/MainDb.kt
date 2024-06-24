package com.example.food.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.food.db.migrations.MIGRATION_1_2
import com.example.food.db.migrations.MIGRATION_2_3
import com.example.food.db.tables.FoodItem
import com.example.food.db.tables.OrderFoodItemCrossRef
import com.example.food.db.tables.Orders
import com.example.food.db.tables.Restaurant
import com.example.food.db.tables.User

@Database (
    entities = [User::class, Restaurant::class, FoodItem::class, Orders::class, OrderFoodItemCrossRef::class],
    version = 4
)
abstract class MainDb : RoomDatabase() {

    abstract fun getDao() : Dao

    companion object{
        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "test.db"
            ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
        }
    }
}