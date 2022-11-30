package com.neotica.headlines

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Step 13

object RetrofitInstance {
    //Step 14: Create a new api variable
    //Step 15: Initialize by lazy (it won't be initialized right away before we first accessed the api.
    val api: TodoApi by lazy {
        //Step 16: Use Retrofit.Builder
        Retrofit.Builder()
        //Step 17: Pass the base URL
            .baseUrl("https://drive.google.com")
        //Step 18: Add converter factory to convert JSON to GSON
            .addConverterFactory(GsonConverterFactory.create())
        //Step 19: We have finished constructing our API, finish up by commanding build.
            .build()
        //Step 20: Specify the class for our API, in this case TodoApi
            .create(TodoApi::class.java)
    }
}