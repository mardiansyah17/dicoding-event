package com.example.dicodingevent.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.dicodingevent.data.local.entity.EventEntity
import com.example.dicodingevent.data.local.room.EventDao
import com.example.dicodingevent.data.model.EventItem
import com.example.dicodingevent.data.remote.retrofit.ApiService
import com.example.dicodingevent.utils.AppExecutors

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val appExecutors: AppExecutors,
) {
    private val allEventResult = MediatorLiveData<Result<List<EventItem>>>()
    private val eventDetailResult = MediatorLiveData<Result<EventItem>>()

    fun getAllEvent(status: Int): LiveData<Result<List<EventItem>>> {
        allEventResult.value = Result.Loading

        try {
            val response = apiService.getAllEvents(status = status)
            val events = response.listEvents.map {
                EventItem(
                    id = it.id,
                    name = it.name,
                    mediaCover = it.mediaCover
                )
            } ?: emptyList()
            allEventResult.postValue(Result.Success(events))
        } catch (e: Exception) {
            Log.d("EventRepository", "Failed to get data: ${e.message}")
            allEventResult.postValue(Result.Error("Failed to get data: ${e.message}"))
        }

//        client.enqueue(object : Callback<AllEventResponse> {
//            override fun onResponse(call: Call<AllEventResponse>, res: Response<AllEventResponse>) {
//                if (res.isSuccessful) {
//                    val events = res.body()?.listEvents?.map {
//                        EventItem(
//                            id = it.id,
//                            name = it.name,
//                            mediaCover = it.mediaCover
//                        )
//                    } ?: emptyList()
//                    allEventResult.postValue(Result.Success(events))
//                } else {
//                    allEventResult.postValue(Result.Error("Failed to get data: ${res.message()}"))
//                }
//            }
//
//            override fun onFailure(call: Call<AllEventResponse>, t: Throwable) {
//                allEventResult.postValue(Result.Error("Failed to get data: ${t.message}"))
//            }
//        })

        return allEventResult
    }

    fun getDetailEvent(id: Int): LiveData<Result<EventItem>> {
        eventDetailResult.value = Result.Loading

        val response = apiService.getDetailEvent(id)
        val event = EventItem(
            id = response.event.id,
            name = response.event.name,
            mediaCover = response.event.mediaCover
        )
        eventDetailResult.postValue(Result.Success(event))

        return eventDetailResult
    }

    fun addFavoriteEvent(event: EventItem) {
        appExecutors.diskIO.execute {
            eventDao.insertFavoriteEvent(EventEntity(event.id, event.name, event.mediaCover))
        }
    }

    fun getFavoriteEventById(id: Int): LiveData<Boolean> {
        return eventDao.getFavoriteEventById(id)
    }

    fun deleteFavoriteEventById(id: Int) {
        appExecutors.diskIO.execute {
            eventDao.deleteFavoriteEventById(id)
        }
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao,
            appExecutors: AppExecutors
        ): EventRepository = instance ?: synchronized(this) {
            instance ?: EventRepository(apiService, eventDao, appExecutors)
        }.also { instance = it }
    }
}