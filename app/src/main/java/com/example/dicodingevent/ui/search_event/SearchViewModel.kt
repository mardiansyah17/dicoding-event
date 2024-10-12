package com.example.dicodingevent.ui.search_event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.remote.response.AllEventResponse
import com.example.dicodingevent.data.remote.response.ListEventsItem
import com.example.dicodingevent.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun findEventByQuery(query: String?) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents(-1, query)
        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, response: Response<AllEventResponse>) {
                Log.e(TAG, "onFailure: ${response.message()}")
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listEvent.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(p0: Call<AllEventResponse>, error: Throwable) {
                _isLoading.value = false
                _errorMessage.value = error.message

                Log.e(TAG, "onFailure: ${error.message}")

            }
        })
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }

}
