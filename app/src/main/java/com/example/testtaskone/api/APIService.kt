package com.example.testapppart1
import retrofit2.http.GET

interface APIService {
    @GET("job/Ant/api/json")
    suspend fun getJobs(): JobResponse
}