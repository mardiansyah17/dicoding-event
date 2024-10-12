package com.example.dicodingevent.ui

import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.EventRepository

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getAllEvent() = eventRepository.getAllEvent()
}
