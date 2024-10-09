package com.example.dicodingevent.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.response.AllEventResponse
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpComingViewModel : ViewModel() {
    private val _listUpComing = MutableLiveData<List<ListEventsItem>>()
    val listUpComing: LiveData<List<ListEventsItem>> = _listUpComing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    init {
        getUpComingEvent()
    }

    private fun getUpComingEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents(ACTIVE)

        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, response: Response<AllEventResponse>) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUpComing.value = response.body()?.listEvents
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
        private const val TAG = "UpComingViewModel"
        private const val ACTIVE = 1
    }
}
