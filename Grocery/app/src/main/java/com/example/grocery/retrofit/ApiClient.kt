package com.example.grocery.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    //http://192.168.1.104:8090/groceryApp/usersadd
    //http://localhost:8090/groceryApp/getCategory

   // private const val BASE_URL="http://192.168.1.105:8090/groceryApp/"
    private const val BASE_URL="http://192.168.1.106:8080/groceryApp-0.0.1-SNAPSHOT/"

    private val retrofit:Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getClient(): ApiService {

        return retrofit.create(ApiService::class.java)

    }


}