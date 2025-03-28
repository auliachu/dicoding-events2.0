package com.example.mydicodingevents.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydicodingevents.data.response.DicodingEventResponse
import com.example.mydicodingevents.data.response.ListEventsItem
import com.example.mydicodingevents.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    private val _upcomingListEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingListEvents : LiveData<List<ListEventsItem>> = _upcomingListEvents

    private val _finishedListEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedListEvents : LiveData<List<ListEventsItem>> = _finishedListEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        getupcomingListEvents()
        getFinishedListEvents()
    }

    private fun getupcomingListEvents(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(1)
        client.enqueue(object : Callback<DicodingEventResponse> {
            override fun onResponse(
                call: Call<DicodingEventResponse>,
                response: Response<DicodingEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _upcomingListEvents.value = response.body()?.listEvents
                } else{
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DicodingEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    private fun getFinishedListEvents(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(0)
        client.enqueue(object : Callback<DicodingEventResponse>{
            override fun onResponse(
                call: Call<DicodingEventResponse>,
                response: Response<DicodingEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _finishedListEvents.value = response.body()?.listEvents
                } else{
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DicodingEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}