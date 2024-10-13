package com.example.dicodingevent.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
class EventEntity(
 val name: String,

    @field:ColumnInfo(name = "mediaCover")
    val mediaCover: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "ownerName")
    val ownerName: String,

)
