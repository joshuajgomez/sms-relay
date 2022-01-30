package com.joshgm3z.smsrelay.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SenderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sender: Sender)

    @Query("SELECT * FROM Sender")
    fun getAll(): List<Sender>

    @Query("SELECT * FROM Sender")
    fun getAllSenders(): Flow<List<Sender>>

    @Query("SELECT * FROM Sender WHERE name IS :name")
    fun getSender(name: String): Sender

    @Query("DELETE FROM Sender")
    fun deleteAll(): Int
}