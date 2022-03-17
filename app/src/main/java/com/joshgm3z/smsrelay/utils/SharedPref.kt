package com.joshgm3z.smsrelay.utils

import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject

class SharedPref(private val context: Context) {

    companion object SortOrder {
        const val KEY = "KEY_SORT_ORDER"
         const val ORDER_DATE = 0
         const val ORDER_COUNT = 1
    }

    fun getSortOrder(): Int {
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context)
        return sharedPreferences
            .getInt(KEY, ORDER_DATE)
    }

    fun setSortOrder(sortOrder: Int) {
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context)
            .edit()
        sharedPreferences
            .putInt(KEY, sortOrder)
            .apply()
    }

}