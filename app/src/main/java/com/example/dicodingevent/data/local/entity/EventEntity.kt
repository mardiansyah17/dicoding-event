package com.example.dicodingevent.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
class EventEntity(

    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "name")
    val name: String

)
