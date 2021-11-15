package com.example.kotlin_romdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.kotlin_romdatabase.sqlite.AppDatabase
import com.example.kotlin_romdatabase.table.user
import com.example.kotlin_romdatabase.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(private val appdatabase: AppDatabase):ViewModel() {
    fun getAllUserDetails():LiveData<Resource<List<user>>>{
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(appdatabase.userDao().getAll()))
            }catch (e : Exception){
                emit(Resource.error(e.message.toString(),null))
            }
        }
    }


    fun insertUser(userInputData : user) :LiveData<Resource<Long>>{
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(appdatabase.userDao().insert(userInputData)))
            }catch (e : Exception){
                emit(Resource.error(e.message.toString(),null))
            }
        }
    }


    fun getUserById(id : Int) :LiveData<Resource<user>>{
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(appdatabase.userDao().getUserById(id)))
            }catch (e : Exception){
                emit(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun deleteUserByModel(deleteModel : user) :LiveData<Resource<Int>>{
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(appdatabase.userDao().delete(deleteModel)))
            }catch (e : Exception){
                emit(Resource.error(e.message.toString(),null))
            }
        }
    }


    fun updateUserByModel(updateModel : user) :LiveData<Resource<Int>>{
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(appdatabase.userDao().updateUser(updateModel)))
            }catch (e : Exception){
                emit(Resource.error(e.message.toString(),null))
            }
        }
    }

}