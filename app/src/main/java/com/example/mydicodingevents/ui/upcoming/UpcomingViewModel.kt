package com.example.mydicodingevents.ui.upcoming

import FavoriteEventRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mydicodingevents.data.response.DicodingEventResponse
import com.example.mydicodingevents.data.response.ListEventsItem
import com.example.mydicodingevents.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel() : ViewModel() {
    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent : LiveData<List<ListEventsItem>> = _listEvent

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "UpcomingViewModel"
    }

    init {
        getListEvents()
    }

    private fun getListEvents(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(1)
        client.enqueue(object : Callback<DicodingEventResponse> {
            override fun onResponse(
                call: Call<DicodingEventResponse>,
                response: Response<DicodingEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listEvent.value = response.body()?.listEvents
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DicodingEventResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

}