package com.joshgm3z.smsrelay.utils

import com.joshgm3z.smsrelay.room.Sender
import java.util.*

fun getSampleSender(): Sender = Sender(
    name = "Some guy #${Random().nextInt()}",
    count = Random().nextInt(50),
    dateModified = System.currentTimeMillis(),
    isBlocked = Random().nextBoolean()
)

fun getSampleList(): List<Sender> {
    return listOf(
        getSampleSender(),
        getSampleSender(),
        getSampleSender(),
        getSampleSender(),
        getSampleSender(),
        getSampleSender(),
        getSampleSender(),
        getSampleSender(),
    )
}