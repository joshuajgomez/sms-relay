package com.joshgm3z.smsrelay.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var mSmsRepository: SmsRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (SMS_RECEIVED == intent.action) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<*>?
                mSmsRepository.onNewSmsReceived(pdus)
            }
        }
    }

    companion object {
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        private const val TAG = "SmsRelay"
    }
}