package com.joshgm3z.smsrelay.utils

import com.joshgm3z.smsrelay.room.Sender

class SortUtil {
    companion object {
        const val ORDER_NEWEST = 0
        private const val ORDER_OLDEST = 1
        private const val ORDER_MOST = 2
        private const val ORDER_FEWEST = 3

        fun sorted(list: List<Sender>, order: Int): List<Sender> {
            return when (order) {
                ORDER_NEWEST -> list.sortedBy { it.dateModified }.asReversed()
                ORDER_OLDEST -> list.sortedBy { it.dateModified }
                ORDER_MOST -> list.sortedBy { it.count }.asReversed()
                ORDER_FEWEST -> list.sortedBy { it.count }
                else -> list.sortedBy { it.dateModified }.asReversed()
            }
        }
    }
}