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

    init {
        getUpComingEvent()
    }

    private fun getUpComingEvent() {

        val client = ApiConfig.getApiService().getAllEvents()
        Log.e("Tes312", "onFailure: ${client.request().body}")
        client.enqueue(object : Callback<AllEventResponse> {
            override fun onResponse(call: Call<AllEventResponse>, response: Response<AllEventResponse>) {
                Log.e("UpComingViewModel", "onFailure: ${response.message()}")

                if (response.isSuccessful) {
                    _listUpComing.value = response.body()?.listEvents
                    Log.d("UpComingViewModel", "onResponse: ${response.body()?.listEvents}")
                } else {
                    Log.e("UpComingViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(p0: Call<AllEventResponse>, p1: Throwable) {
                Log.e("UpComingViewModel", "onFailure: ${p1.message}")

            }
        })
    }
}
