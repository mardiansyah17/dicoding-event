package com.example.dicodingevent.data.retrofit

import com.example.dicodingevent.data.response.AllEventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    fun getAllEvents(
        @Query("active") status: Int
    ): Call<AllEventResponse>


}
