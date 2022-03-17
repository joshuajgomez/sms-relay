package com.joshgm3z.smsrelay.ui

import android.util.Log
import androidx.lifecycle.*
import com.joshgm3z.smsrelay.domain.SmsRepository
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.Logger
import com.joshgm3z.smsrelay.utils.SharedPref
import com.joshgm3z.smsrelay.utils.SortUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SenderViewModel
@Inject
constructor(
    private val mSmsRepository: SmsRepository,
) : ViewModel() {

    var mSenderList: LiveData<List<Sender>>? = null

    init {
        //mSmsRepository.addSampleData()
        viewModelScope.launch {
            mSenderList = mSmsRepository.getSenderList().asLiveData()
        }
    }

    fun onBlockedCheckboxClicked(name: String, isBlocked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            mSmsRepository.updateBlockedStatus(name, isBlocked)
        }
    }

}
