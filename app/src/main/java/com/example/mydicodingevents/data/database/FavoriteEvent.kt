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
    var mediaCover : String? = "",

    @ColumnInfo(name = "imageLogo")
    var imageLogo : String? = "",

    @ColumnInfo(name = "summary")
    var summary : String? = "",

    @ColumnInfo(name = "ownerName")
    var ownerName : String? = "",

    @ColumnInfo(name = "quota")
    var quota : String? = "",

    @ColumnInfo(name = "description")
    var description : String? = "",

    @ColumnInfo(name = "registrant")
    var registrant: Int?,

    @ColumnInfo(name = "beginTime")
    var beginTime: String? = "",

    @ColumnInfo(name = "link")
    var link: String? = ""
) : Parcelable