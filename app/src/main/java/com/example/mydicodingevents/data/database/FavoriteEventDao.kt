package com.example.mydicodingevents.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: FavoriteEvent)

    @Query("SELECT * FROM favorite_event WHERE id= :id")
    fun getData(id: Int) : LiveData<FavoriteEvent>

    @Query("SELECT * FROM favorite_event")
    fun getFavorite(): LiveData<List<FavoriteEvent>>

    @Delete
    fun delete(event: FavoriteEvent)
}