package com.example.dicodingevent.data.remote.retrofit

import com.example.dicodingevent.data.remote.response.AllEventResponse
import com.example.dicodingevent.data.remote.response.DetailEventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    fun getAllEvents(
        @Query("active") status: Int,
        @Query("q") query: String? = null
    ): Call<AllEventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: Int
    ): Call<DetailEventResponse>

}
