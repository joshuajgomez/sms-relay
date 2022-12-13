package com.joshgm3z.smsrelay.compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joshgm3z.smsrelay.domain.SmsRepository
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: SmsRepository) : ViewModel() {

    val senderList: LiveData<List<Sender>> = repository.getSenderList()

    var senderListResult: MutableLiveData<List<Sender>> = MutableLiveData()

    val isSearchMode = mutableStateOf(false)

    fun onSearchIconClick() {
        isSearchMode.value = true
        senderListResult.value = senderList.value
    }

    fun onAppTitleLongClick() {
        CoroutineScope(Dispatchers.IO)
            .launch {
                repository.addSampleData()
            }
    }

    fun onSearchInputChanged(input: String) {
        if (input.isNotEmpty()) {
            senderListResult.value = senderList.value?.filter { sender: Sender ->
                sender.name.contains(input)
            }
        } else {
            senderListResult.value = senderList.value
        }
        Logger.log("$input: ${senderListResult.value}")
    }

    fun onSearchBackIconClick() {
        isSearchMode.value = false
    }

    fun onCheckedChange(sender: Sender) {
        Logger.log("sender = $sender")
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateBlockedStatus(sender.name, !sender.isBlocked)
        }
    }
}