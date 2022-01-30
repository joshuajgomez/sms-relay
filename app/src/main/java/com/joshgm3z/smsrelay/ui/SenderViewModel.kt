package com.joshgm3z.smsrelay.ui

import androidx.lifecycle.*
import com.joshgm3z.smsrelay.domain.SmsRepository
import com.joshgm3z.smsrelay.room.Sender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SenderViewModel
@Inject
constructor(
    private val mSmsRepository: SmsRepository,
) : ViewModel() {

    private val mSenderList: LiveData<List<Sender>> = mSmsRepository.getSenderList().asLiveData()

    fun getSenderList(): LiveData<List<Sender>> {
        return mSenderList
    }

    fun onBlockedCheckboxClicked(name: String, isBlocked: Boolean) {
        viewModelScope.launch {
            mSmsRepository.updateBlockedStatus(name, isBlocked)
        }
    }

}
