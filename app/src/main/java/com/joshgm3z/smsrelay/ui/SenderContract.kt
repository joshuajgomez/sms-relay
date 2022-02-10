package com.joshgm3z.smsrelay.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joshgm3z.smsrelay.room.Sender
import kotlinx.coroutines.flow.Flow

interface SenderContract {

    interface ViewModel {
        fun onSenderListFetched(senderList: Flow<List<Sender>>)
        fun showMessage(message: String)
    }

    interface View {
        fun onDataFetched()
    }

}