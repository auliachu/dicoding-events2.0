package com.example.mydicodingevents.ui.finished

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

class FinishedViewModel() : ViewModel() {

    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent : LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        getFinishListEvent()
    }

    private fun getFinishListEvent(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(0)
        client.enqueue(object : Callback<DicodingEventResponse> {
            override fun onResponse(
                call: Call<DicodingEventResponse>,
                response: Response<DicodingEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listEvent.value = response.body()?.listEvents
                } else{
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DicodingEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "FinishedViewModel"
    }

}