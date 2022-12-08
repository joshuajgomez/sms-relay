package com.joshgm3z.smsrelay.domain

import android.telephony.SmsMessage
import com.joshgm3z.smsrelay.room.AppDatabase
import com.joshgm3z.smsrelay.room.Sender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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

    fun addSampleData() {
        CoroutineScope(Dispatchers.IO).launch {
            mAppDatabase.senderDao().insert(Sender("Sender#1", 3, getRandomDate(0)))
            mAppDatabase.senderDao().insert(Sender("Sender#2", 1, getRandomDate(-100000000)))
            mAppDatabase.senderDao().insert(Sender("Sender#3", 3, getRandomDate(-1000000000)))
            mAppDatabase.senderDao().insert(Sender("Sender#4", 5, getRandomDate(1500000000)))
            mAppDatabase.senderDao().insert(Sender("Sender#5", 19, getRandomDate(30000000000)))
            mAppDatabase.senderDao().insert(Sender("Sender#6", 100, getRandomDate(-6000000000)))
            mAppDatabase.senderDao().insert(Sender("Sender#7", 7, getRandomDate(-20000000000)))
            mAppDatabase.senderDao().insert(Sender("Sender#8", 8, getRandomDate(-7000000000)))
            mAppDatabase.senderDao().insert(Sender("Sender#9", 4, getRandomDate(80000000000)))
            mAppDatabase.senderDao().insert(Sender("Sender#10", 2, getRandomDate(-4000000000)))
            mAppDatabase.senderDao().insert(Sender("Sender#11", 1, getRandomDate(-133000000000033)))
        }
    }

    private fun getRandomDate(random: Long): Long {
        return System.currentTimeMillis() + random
    }

}