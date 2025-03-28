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

    @Query("SELECT * FROM favorite_event WHERE id= :id AND is_Favorite = 1")
    fun getData(id: Int) : LiveData<FavoriteEvent>

    @Query("SELECT * FROM favorite_event WHERE is_Favorite = 1")
    fun getFavorite(): LiveData<List<FavoriteEvent>>

    @Update
    fun updateEvent(event: FavoriteEvent)

    @Query("DELETE FROM favorite_event WHERE id= :id")
    fun deleteFavoriteEvent(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM favorite_event WHERE id= :id AND is_Favorite = 1)")
    fun isFavoriteEventAdd(id: Int) : Boolean
}