package com.example.mydicodingevents.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_event")
@Parcelize
data class FavoriteEvent (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "name")
    var name : String = "",
    @ColumnInfo(name = "mediaCover")
    var mediaCover : String? = null,
    @ColumnInfo("is_Favorite")
    var isFavorite : Boolean
) : Parcelable