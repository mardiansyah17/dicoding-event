package com.example.dicodingevent.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventItem(
    val id: Int,
    val name: String,
    val mediaCover: String,
    val registrants: Int? = null,
    val date: String? = null,
    val quote: Int? = null,
    val link: String? = null,
    val description: String? = null,
    val ownerName: String? = null,
) : Parcelable