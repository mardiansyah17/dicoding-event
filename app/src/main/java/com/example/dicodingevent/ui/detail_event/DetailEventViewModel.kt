package com.example.dicodingevent.ui.detail_event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.remote.response.DetailEventResponse
import com.example.dicodingevent.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel : ViewModel() {

    private val _detailEvent = MutableLiveData<DetailEventResponse>()
    val detailEvent: LiveData<DetailEventResponse> = _detailEvent

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getDetailEvent(id: Int) {
        if (_detailEvent.value != null) {
            return
        }

        _loading.postValue(true)


        val client = ApiConfig.getApiService().getDetailEvent(id)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {

                _loading.postValue(false)

                if (!response.isSuccessful) {
                    _error.postValue("Failed to get data")
                    return
                }
                if (response.isSuccessful) {
                    Log.d("DetailActivityLog", "onResponse: ${response.body()}")
                    _detailEvent.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _loading.postValue(false)
                _error.postValue(t.message)

                Log.d("DetailActivityLog", "onFailure: ${t.message}")
            }
        })
    }


}
