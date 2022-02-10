package com.joshgm3z.smsrelay.domain

import android.telephony.SmsMessage
import com.joshgm3z.smsrelay.room.AppDatabase
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.ui.SenderContract
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SmsRepository
@Inject
constructor(private val mAppDatabase: AppDatabase) {

    companion object {
        private const val TAG = "SmsManager"
    }

    suspend fun onNewSmsReceived(pdus: Array<*>?) {
        val sender = getSender(pdus)
        val message = getMessage(pdus)

        registerSender(sender)
        checkAndRelayMessage(sender, message)
    }

    private fun checkAndRelayMessage(sender: String, message: String) {
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
            sender = Sender(name, 1)
            sender.isBlocked = false
            mAppDatabase.senderDao().insert(sender)
        } else {
            // known sender. increment count
            sender.count++
            mAppDatabase.senderDao().update(sender)
        }
    }

    suspend fun getSenderList(): Flow<List<Sender>> {
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

}