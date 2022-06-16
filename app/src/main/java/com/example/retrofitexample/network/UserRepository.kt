package com.example.retrofitexample.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.retrofitexample.ApiClient
import com.example.retrofitexample.ResultsItem
import com.example.retrofitexample.local.UserDatabase
import com.example.retrofitexample.local.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val database: UserDatabase) {
    val users: LiveData<List<ResultsItem>> = Transformations.map(database.userDao.getAll()) {
        it.asDomainModel()
    }

    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            val users = ApiClient.getApiClient().getUsers(100)
            database.userDao.insertAll(users.asDatabaseModel())
        }
    }


}


