package com.example.food.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2= object: Migration(1,2){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS Restaurants (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT NOT NULL, " + // добавьте NOT NULL
                    "address TEXT NOT NULL)" // добавьте NOT NULL
        )
    }

}

val MIGRATION_2_3= object: Migration(2,3){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `FoodItem` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`name` TEXT NOT NULL, " +
                    "`description` TEXT NOT NULL, " +
                    "`ingridients` TEXT NOT NULL, " +
                    "`foodImage` BLOB)"
        )
    }

}