package com.example.dicodingevent.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.dicodingevent.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<EventEntity>>

}
