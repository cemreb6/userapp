package com.example.retrofitexample

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface UserApiService {
    @GET(".")
    suspend fun getUsers(@Query("results") result: Int): User

    @GET(".")
    suspend fun getUser(@Query("name.first")filter: String) : User
}

object ApiClient{
    const val BASE_URL="https://randomuser.me/api/"

    fun getApiClient():UserApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(UserApiService::class.java)
    }
}
