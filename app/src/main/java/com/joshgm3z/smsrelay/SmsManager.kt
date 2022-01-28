package com.joshgm3z.smsrelay

import android.content.Context
import android.telephony.SmsMessage
import android.util.Log
import com.joshgm3z.smsrelay.room.AppDatabase
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.ui.AdapterClickListener
import com.joshgm3z.smsrelay.ui.SenderContract
import com.joshgm3z.smsrelay.utils.Logger

class SmsManager(mContext: Context, private val mView: SenderContract.View?) :
    AdapterClickListener {

    companion object {
        private const val TAG = "SmsManager"
    }

    private var mAppDatabase: AppDatabase = AppDatabase.getDatabase(mContext)

    fun onNewSmsReceived(pdus: Array<*>?) {
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

    private fun registerSender(name: String) {
        Logger.log("name=[$name]");
        var sender: Sender = mAppDatabase.senderDao().getSender(name)
        if (sender == null) {
            // new sender
            sender = Sender(name, 1)
            sender.isBlocked = false
        } else {
            // known sender. increment count
            sender.count++
        }
        mAppDatabase.senderDao().insert(sender)
        mView?.updateSender(sender)
        Logger.log(Log.ASSERT, "$mView")
    }

    fun getAllSenders(): List<Sender> {
        return mAppDatabase.senderDao().getAll()
    }

    override fun onBlockChanged(name: String, isBlocked: Boolean) {
        val sender = mAppDatabase.senderDao().getSender(name)
        sender.isBlocked = !sender.isBlocked
        mAppDatabase.senderDao().insert(sender)
        var message: String = name
        message += if (isBlocked)
            " is blocked"
        else
            " is unblocked"
        mView?.showMessage(message)
    }

}