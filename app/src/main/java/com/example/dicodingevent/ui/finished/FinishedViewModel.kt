package com.example.dicodingevent.ui.finished

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

class FinishedViewModel : ViewModel() {
    private val _listFinished = MutableLiveData<List<ListEventsItem>>()
    val listFinished: LiveData<List<ListEventsItem>> = _listFinished

    companion object {
        private const val TAG = "FinishedViewModel"
        private const val ACTIVE = 0
    }

    init {
        getFinishedEvent()

    }

    private fun getFinishedEvent() {

        val client = ApiConfig.getApiService().getAllEvents(ACTIVE)

        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, response: Response<AllEventResponse>) {


                if (response.isSuccessful) {
                    _listFinished.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "aduh onFailure: ${response.message()}")
                }
            }

            override fun onFailure(p0: Call<AllEventResponse>, p1: Throwable) {
                Log.e(TAG, "aduh onFailure: ${p1.message}")

            }
        })
    }
}
