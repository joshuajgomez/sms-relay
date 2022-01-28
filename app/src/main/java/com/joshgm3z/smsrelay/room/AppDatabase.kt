package com.joshgm3z.smsrelay.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Sender::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun senderDao(): SenderDao

    companion object {
        @Volatile
        private var mAppDatabase: AppDatabase? = null
        fun getDatabase(
            context: Context,
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return mAppDatabase ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sms_sender_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                mAppDatabase = instance
                // return instance
                instance
            }
        }
    }

}