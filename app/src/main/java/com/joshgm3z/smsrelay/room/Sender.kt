package com.joshgm3z.smsrelay.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sender(
    @PrimaryKey val name: String,
    var count: Int = 0,
    var isBlocked: Boolean = false
)