package com.example.dicodingevent.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.dicodingevent.data.local.entity.EventEntity
import com.example.dicodingevent.data.local.room.EventDao
import com.example.dicodingevent.data.model.EventItem
import com.example.dicodingevent.data.remote.response.AllEventResponse
import com.example.dicodingevent.data.remote.response.DetailEventResponse
import com.example.dicodingevent.data.remote.retrofit.ApiService
import com.example.dicodingevent.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val appExecutors: AppExecutors,
) {
    private val allEventResult = MediatorLiveData<Result<List<EventItem>>>()
    private val eventDetailResult = MediatorLiveData<Result<EventItem>>()

    fun getAllEvent(status: Int): LiveData<Result<List<EventItem>>> {
        allEventResult.value = Result.Loading

        val client = apiService.getAllEvents(status = status)
        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, res: Response<AllEventResponse>) {
                if (res.isSuccessful) {
                    val events = res.body()?.listEvents?.map {
                        EventItem(
                            id = it.id,
                            name = it.name,
                            mediaCover = it.mediaCover
                        )
                    } ?: emptyList()
                    allEventResult.postValue(Result.Success(events))
                } else {
                    allEventResult.postValue(Result.Error("Failed to get data: ${res.message()}"))
                }
            }

            override fun onFailure(call: Call<AllEventResponse>, t: Throwable) {
                allEventResult.postValue(Result.Error("Failed to get data: ${t.message}"))
            }
        })

        return allEventResult
    }

    fun getDetailEvent(id: Int): LiveData<Result<EventItem>> {
        eventDetailResult.value = Result.Loading

        val client = apiService.getDetailEvent(id)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                if (response.isSuccessful) {
                    val eventDetail = response.body()?.event
                    if (eventDetail != null) {
                        val event = EventItem(
                            id = eventDetail.id,
                            name = eventDetail.name,
                            mediaCover = eventDetail.mediaCover,
                            registrants = eventDetail.registrants,
                            link = eventDetail.link,
                            description = eventDetail.description,
                            ownerName = eventDetail.ownerName,
                            quote = eventDetail.quota
                        )
                        eventDetailResult.postValue(Result.Success(event))
                    } else {
                        eventDetailResult.postValue(Result.Error("Event detail is null"))
                    }
                } else {
                    eventDetailResult.postValue(Result.Error("Failed to get data: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                eventDetailResult.postValue(Result.Error("Failed to get data: ${t.message}"))
            }
        })

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