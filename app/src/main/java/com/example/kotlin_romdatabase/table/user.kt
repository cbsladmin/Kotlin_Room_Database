package com.example.kotlin_romdatabase.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class user(
    @PrimaryKey(autoGenerate = true) val id: Int?=null,
    @ColumnInfo(name = "username")var username :String?,
    @ColumnInfo(name = "userpassword")var userpassword:String
)
