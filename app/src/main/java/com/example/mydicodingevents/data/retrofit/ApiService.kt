package com.example.mydicodingevents.data.retrofit

import com.example.mydicodingevents.data.response.DicodingDetailEventResponse
import com.example.mydicodingevents.data.response.DicodingEventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvent(
        @Query("active") number: Int
    ) : Call<DicodingEventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ) : Call<DicodingDetailEventResponse>
}