package com.joshgm3z.smsrelay.utils

import android.content.Context
import android.preference.PreferenceManager
import com.joshgm3z.smsrelay.utils.SortUtil.Companion.ORDER_NEWEST

class SharedPref(private val context: Context) {

    companion object SortOrder {
        const val KEY = "KEY_SORT_ORDER"
    }

    fun getSortOrder(): Int {
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context)
        return sharedPreferences
            .getInt(KEY, ORDER_NEWEST)
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