package com.joshgm3z.smsrelay.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {

          fun getTextDate(instance: Long): String {
            val date = Date(instance)
            val sdf: SimpleDateFormat = if (DateUtils.isToday(instance)) {
                SimpleDateFormat("h:mm aa")
            } else {
                SimpleDateFormat("MMM dd, yyy")
            }
            return sdf.format(date).toLowerCase()
        }
    }

}