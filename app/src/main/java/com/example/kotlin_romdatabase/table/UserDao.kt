package com.example.kotlin_romdatabase.table

import androidx.room.*


@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<user>

    @Insert
    suspend fun insert(user: user):Long


    @Query("SELECT * FROM user WHERE id =:id")
    suspend fun getUserById(id : Int):user

    @Delete
    suspend fun delete(user: user):Int

    @Update
    suspend fun updateUser(user: user):Int

}