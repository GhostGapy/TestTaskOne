package com.example.testtaskone.api

import com.example.testapppart1.APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ci-builds.apache.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: APIService = retrofit.create(APIService::class.java)
}