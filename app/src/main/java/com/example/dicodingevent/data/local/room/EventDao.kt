package com.example.dicodingevent.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dicodingevent.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteEvent(events: EventEntity)

    @Query("SELECT  EXISTS(SELECT * FROM favorite_events WHERE id = :id)")
    fun getFavoriteEventById(id: Int): LiveData<Boolean>

    @Query("DELETE FROM favorite_events where id = :id")
    suspend fun deleteFavoriteEventById(id: Int)

    @Query("SELECT * FROM favorite_events")
    suspend fun getFavoriteEvents(): List<EventEntity>


}
