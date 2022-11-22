package com.example.newsit.api

import com.example.newsit.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(
        NewsInterface::
        class.java
    )

}
