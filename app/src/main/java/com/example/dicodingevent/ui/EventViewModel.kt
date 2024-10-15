package com.example.dicodingevent.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.EventRepository
import com.example.dicodingevent.data.model.EventItem
import kotlinx.coroutines.launch

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getAllEvent(status: Int) = eventRepository.getAllEvent(status)
    fun getDetailEvent(id: Int) = eventRepository.getDetailEvent(id)
    fun addFavoriteEvent(event: EventItem) {
        viewModelScope.launch {
            eventRepository.addFavoriteEvent(event)
        }
    }

    fun getFavoriteEventById(id: Int) = eventRepository.getFavoriteEventById(id)

    fun deleteFavoriteEventById(id: Int) {
        viewModelScope.launch {
            eventRepository.deleteFavoriteEventById(id)
        }
    }

}
