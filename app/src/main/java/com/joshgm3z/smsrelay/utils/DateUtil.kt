package com.joshgm3z.smsrelay.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        fun getTextDate(dateInstance: Long): String {
            val instance = Calendar.getInstance()
            instance.time = Date(dateInstance)
            val month: String = getMonth(instance)
            val day = instance.get(Calendar.DAY_OF_MONTH)
            val year = instance.get(Calendar.YEAR)
            val minute = instance.get(Calendar.MINUTE)
            val hour = instance.get(Calendar.HOUR)
            val am_pm = instance.get(Calendar.AM_PM)

            var outputDate: String
            if (DateUtils.isToday(dateInstance)) {
                outputDate = "$hour:$minute$am_pm"
            } else {
                outputDate = "$month $day, $year"
            }

            return outputDate
        }

        fun getTextDate2(instance: Long): String {
            val date = Date(instance)
            val sdf: SimpleDateFormat = if (DateUtils.isToday(instance)) {
                SimpleDateFormat("h:m aa")
            } else {
                SimpleDateFormat("MMM DD, YYYY")
            }
            return sdf.format(date).toLowerCase()
        }

        private fun getMonth(instance: Calendar): String {
            when (instance.get(Calendar.MONTH)) {
                1 -> return "jan"
                2 -> return "feb"
                3 -> return "mar"
                4 -> return "apr"
                5 -> return "may"
                6 -> return "jun"
                7 -> return "jul"
                8 -> return "aug"
                9 -> return "sep"
                10 -> return "oct"
                11 -> return "nov"
                12 -> return "dec"
            }
            return ""
        }
    }

}