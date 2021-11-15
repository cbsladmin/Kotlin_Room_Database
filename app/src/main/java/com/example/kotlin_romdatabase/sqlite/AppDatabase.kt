package com.example.kotlin_romdatabase.sqlite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlin_romdatabase.table.UserDao
import com.example.kotlin_romdatabase.table.user

@Database(entities = [user::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}