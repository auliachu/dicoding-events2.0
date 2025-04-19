package com.example.mydicodingevents.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEvent::class], version = 3)
abstract class EventRoomDatabase : RoomDatabase() {
    abstract fun favDao() : FavoriteEventDao

    companion object{
        @Volatile
        private var INSTANCE: EventRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) : EventRoomDatabase {
            if (INSTANCE == null){
                synchronized(EventRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        EventRoomDatabase::class.java, "fav_event_database").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE as EventRoomDatabase
        }
    }
}