package com.example.dicodingevent.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.dicodingevent.data.local.entity.EventEntity
import com.example.dicodingevent.data.local.room.EventDao
import com.example.dicodingevent.data.model.EventItem
import com.example.dicodingevent.data.remote.retrofit.ApiService
import com.example.dicodingevent.utils.SettingPreferences

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val pref: SettingPreferences
) {

    fun getAllEvent(status: Int, query: String? = null): LiveData<Result<List<EventItem>>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = apiService.getAllEvents(status = status, query = query)
                val events = response.listEvents.map {
                    EventItem(
                        id = it.id,
                        name = it.name,
                        mediaCover = it.mediaCover
                    )
                } ?: emptyList()
                emitSource(liveData { emit(Result.Success(events)) })
            } catch (e: Exception) {
                Log.d("EventRepository", "Failed to get data: ${e.message}")
                emitSource(liveData { emit(Result.Error("Failed to get data: ${e.message}")) })
            }


        }


    fun getDetailEvent(id: Int): LiveData<Result<EventItem>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getDetailEvent(id)
            val event = EventItem(
                id = response.event.id,
                name = response.event.name,
                mediaCover = response.event.mediaCover,
                description = response.event.description,
                date = response.event.beginTime,
                registrants = response.event.registrants,
                link = response.event.link,
                quota = response.event.quota,
                ownerName = response.event.ownerName,
            )
            emitSource(liveData { emit(Result.Success(event)) })
        } catch (e: Exception) {
            emitSource(liveData { emit(Result.Error("Failed to get data: ${e.message}")) })
        }
    }

    fun getFavoriteEvents(): LiveData<List<EventItem>> {
        return eventDao.getFavoriteEvents()
    }


    suspend fun addFavoriteEvent(event: EventItem) {
        eventDao.insertFavoriteEvent(EventEntity(event.id, event.name, event.mediaCover))

    }

    fun getFavoriteEventById(id: Int): LiveData<Boolean> = liveData {
        emitSource(eventDao.getFavoriteEventById(id))
    }

    suspend fun deleteFavoriteEventById(id: Int) {
        eventDao.deleteFavoriteEventById(id)

    }

    fun getTheme(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    suspend fun saveTheme(isDarkMode: Boolean) {
        pref.saveThemeSetting(isDarkMode)
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao,
            pref: SettingPreferences
        ): EventRepository = instance ?: synchronized(this) {
            instance ?: EventRepository(apiService, eventDao, pref)
        }.also { instance = it }
    }
}