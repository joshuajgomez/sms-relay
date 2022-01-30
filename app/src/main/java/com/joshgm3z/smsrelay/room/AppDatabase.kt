package com.joshgm3z.smsrelay.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Sender::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun senderDao(): SenderDao

}