package com.joshgm3z.smsrelay.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.joshgm3z.smsrelay.domain.SmsRepository
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: SmsRepository) : ViewModel() {

    var senderList: LiveData<List<Sender>> = repository.getSenderList()

    fun onSearchIconClick() = CoroutineScope(Dispatchers.IO)
        .launch {
            repository.addSampleData()
        }

    fun onCheckedChange(sender: Sender) {
        Logger.log("sender = $sender")
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateBlockedStatus(sender.name, !sender.isBlocked)
        }
    }
}