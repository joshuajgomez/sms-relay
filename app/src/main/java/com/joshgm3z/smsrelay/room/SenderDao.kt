package com.joshgm3z.smsrelay.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SenderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sender: Sender)

    @Update
    suspend fun update(sender: Sender)

    @Query("SELECT * FROM Sender")
    suspend fun getAll(): List<Sender>

    @Query("SELECT * FROM Sender")
    fun getAllSenders(): LiveData<List<Sender>>

    @Query("SELECT * FROM Sender WHERE name IS :name")
    suspend fun getSender(name: String): Sender

    @Query("DELETE FROM Sender")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(senderList: List<Sender>)
}