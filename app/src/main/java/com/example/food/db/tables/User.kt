package com.example.food.db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "login")
    var name: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "address")
    var address: String,
    @ColumnInfo(name = "phone")
    var phone: String,
    @ColumnInfo(name = "isAdmin")
    var isAdmin: Boolean
)
