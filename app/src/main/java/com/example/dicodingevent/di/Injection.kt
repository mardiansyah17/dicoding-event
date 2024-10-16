package com.example.dicodingevent.di

import android.content.Context
import com.example.dicodingevent.data.EventRepository
import com.example.dicodingevent.data.local.room.EventDatabase
import com.example.dicodingevent.data.remote.retrofit.ApiConfig
import com.example.dicodingevent.utils.SettingPreferences
import com.example.dicodingevent.utils.dataStore

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        val pref = SettingPreferences.getInstance(context.dataStore)
        return EventRepository.getInstance(apiService, dao, pref)
    }
}
