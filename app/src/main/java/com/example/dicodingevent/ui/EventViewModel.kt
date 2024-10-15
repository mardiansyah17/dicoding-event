package com.example.dicodingevent.ui

import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.EventRepository
import com.example.dicodingevent.data.model.EventItem

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getAllEvent(status: Int) = eventRepository.getAllEvent(status)
    fun getDetailEvent(id: Int) = eventRepository.getDetailEvent(id)
    fun addFavoriteEvent(event: EventItem) = eventRepository.addFavoriteEvent(event)
    fun getFavoriteEventById(id: Int) = eventRepository.getFavoriteEventById(id)
    fun deleteFavoriteEventById(id: Int) = eventRepository.deleteFavoriteEventById(id)
}
