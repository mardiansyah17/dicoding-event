package com.example.dicodingevent.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.dicodingevent.data.local.entity.EventEntity
import com.example.dicodingevent.data.local.room.EventDao
import com.example.dicodingevent.data.remote.response.AllEventResponse
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
    private val result = MediatorLiveData<Result<List<EventEntity>>>()

    fun getAllEvent(): LiveData<Result<List<EventEntity>>> {
        result.value = Result.Loading

        val client = apiService.getAllEvents(status = 0)
        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, res: Response<AllEventResponse>) {
                if (res.isSuccessful) {
                    val events = res.body()?.listEvents
                    val eventList = ArrayList<EventEntity>()

                    appExecutors.diskIO.execute {
                        events?.forEach { eventData ->
                            val event = EventEntity(
                                eventData.id,
                                eventData.name,
                                eventData.mediaCover,
                                eventData.description,
                                eventData.ownerName
                            )

                            eventList.add(event)

                        }
                        eventDao.insertEvents(eventList)

                    }
                }
            }

            override fun onFailure(p0: Call<AllEventResponse>, p1: Throwable) {

            }

        })

        val localData = eventDao.getAllEvents()
        result.addSource(localData) { data: List<EventEntity> ->
            result.value = Result.Success(data)
        }

        return result
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
