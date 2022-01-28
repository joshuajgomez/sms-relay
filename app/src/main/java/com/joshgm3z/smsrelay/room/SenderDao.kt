package com.joshgm3z.smsrelay.room

import androidx.room.*

@Dao
interface SenderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sender: Sender)

    @Query("SELECT * from Sender")
    fun getAll(): List<Sender>

    @Query("SELECT * FROM Sender WHERE name IS :name")
    fun getSender(name: String): Sender

    @Query("DELETE FROM Sender")
    fun deleteAll(): Int
}