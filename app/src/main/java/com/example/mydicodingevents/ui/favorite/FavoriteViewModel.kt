package com.example.mydicodingevents.ui.favorite

import FavoriteEventRepository
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mydicodingevents.data.database.FavoriteEvent

//kelas ini sebagai penghubung antara fragment dan repository
class FavoriteViewModel (application: Application) : ViewModel(){
    private val mFavoriteEventRepository : FavoriteEventRepository = FavoriteEventRepository(application)

    fun getAllFavoriteEvents() : LiveData<List<FavoriteEvent>> = mFavoriteEventRepository.getAllFavoriteEvents()
}