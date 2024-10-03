package com.example.dicodingevent.data.retrofit

import com.example.dicodingevent.data.response.AllEventResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("events")
    fun getAllEvents(): Call<AllEventResponse>
}
