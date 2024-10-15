package com.example.dicodingevent.data.remote.retrofit

import com.example.dicodingevent.data.remote.response.AllEventResponse
import com.example.dicodingevent.data.remote.response.DetailEventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    suspend fun getAllEvents(
        @Query("active") status: Int,
        @Query("q") query: String? = null
    ): AllEventResponse

    @GET("events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: Int
    ): DetailEventResponse

}
