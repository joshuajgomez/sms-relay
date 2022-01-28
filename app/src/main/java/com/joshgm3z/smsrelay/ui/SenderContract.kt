package com.joshgm3z.smsrelay.ui

import com.joshgm3z.smsrelay.room.Sender

interface SenderContract {

    interface View {
        fun updateData(list: List<Sender>)
        fun showMessage(message: String)
        fun updateSender(sender: Sender)
    }

    interface ViewModel {
        fun startObserving()
    }

    interface Model {
        fun getData()
    }

}