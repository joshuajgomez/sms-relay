package com.joshgm3z.smsrelay.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SmsReceiver(private var mSmsRepository: SmsRepository) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (SMS_RECEIVED == intent.action) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<*>?
                GlobalScope.launch(Dispatchers.IO) {
                    mSmsRepository.onNewSmsReceived(pdus)
                }
            }
        }
    }

    companion object {
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        private const val TAG = "SmsRelay"
    }
}