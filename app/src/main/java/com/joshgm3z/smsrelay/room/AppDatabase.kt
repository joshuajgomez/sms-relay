package com.joshgm3z.smsrelay.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Sender::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun senderDao(): SenderDao

}