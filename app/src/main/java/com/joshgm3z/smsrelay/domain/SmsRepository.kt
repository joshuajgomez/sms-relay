package com.joshgm3z.smsrelay.domain

import android.telephony.SmsMessage
import androidx.lifecycle.LiveData
import com.joshgm3z.smsrelay.room.AppDatabase
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.getSampleList

class SmsRepository(private val mAppDatabase: AppDatabase) {

    companion object {
        private const val TAG = "SmsManager"
    }

    suspend fun onNewSmsReceived(pdus: Array<*>?) {
        val sender = getSender(pdus)
        val message = getMessage(pdus)

        registerSender(sender)
        checkAndRelayMessage(sender, message)
    }

    private suspend fun checkAndRelayMessage(sender: String, message: String) {
        if (!mAppDatabase.senderDao().getSender(sender).isBlocked) {
            RelayManager().relaySms(sender, message)
        }
    }

    private fun getMessage(pdus: Array<*>?): String {
        val fromPdu = SmsMessage.createFromPdu(
            pdus!![0] as ByteArray
        )
        return fromPdu.messageBody
    }

    private fun getSender(pdus: Array<*>?): String {
        val fromPdu = SmsMessage.createFromPdu(
            pdus!![0] as ByteArray
        )
        return fromPdu.displayOriginatingAddress
    }

    private suspend fun registerSender(name: String) {
        var sender: Sender = mAppDatabase.senderDao().getSender(name)
        if (sender == null) {
            // new sender
            sender = Sender(name, 1, System.currentTimeMillis())
            sender.isBlocked = false
            mAppDatabase.senderDao().insert(sender)
        } else {
            // known sender. increment count
            sender.count++
            sender.dateModified = System.currentTimeMillis()
            mAppDatabase.senderDao().update(sender)
        }
    }

    fun getSenderList(): LiveData<List<Sender>> {
        return mAppDatabase.senderDao().getAllSenders()
    }

    suspend fun updateBlockedStatus(name: String, isBlocked: Boolean): String {
        val sender = mAppDatabase.senderDao().getSender(name)
        sender.isBlocked = !sender.isBlocked
        mAppDatabase.senderDao().update(sender)
        var message: String = name
        message += if (isBlocked)
            " is blocked"
        else
            " is unblocked"
        return message
    }

    suspend fun addSampleData() = mAppDatabase.senderDao().insertAll(getSampleList())

}