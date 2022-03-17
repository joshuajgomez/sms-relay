package com.joshgm3z.smsrelay.utils

import androidx.lifecycle.LiveData
import com.joshgm3z.smsrelay.room.Sender

class SortUtil {
    companion object {
        fun byTime(list: List<Sender>): List<Sender> {
            return list?.sortedBy { it.dateModified }.asReversed()
        }

        fun byCount(list: List<Sender>): List<Sender> {
            return list?.sortedBy { it.count }.asReversed()
        }
    }
}