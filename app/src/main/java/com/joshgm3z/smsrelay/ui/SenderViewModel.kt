package com.joshgm3z.smsrelay.ui

import androidx.lifecycle.*
import com.joshgm3z.smsrelay.domain.SmsRepository
import com.joshgm3z.smsrelay.room.Sender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SenderViewModel(
    private val mSmsRepository: SmsRepository,
) : ViewModel() {

    var mSenderList: LiveData<List<Sender>>? = null

    init {
        //mSmsRepository.addSampleData()
        viewModelScope.launch {
//            mSenderList = mSmsRepository.getSenderList().asLiveData()
        }
    }

    fun onBlockedCheckboxClicked(name: String, isBlocked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            mSmsRepository.updateBlockedStatus(name, isBlocked)
        }
    }

}
