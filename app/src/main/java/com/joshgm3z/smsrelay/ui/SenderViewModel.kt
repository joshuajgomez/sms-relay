package com.joshgm3z.smsrelay.ui

import android.util.Log
import androidx.lifecycle.*
import com.joshgm3z.smsrelay.domain.SmsRepository
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SenderViewModel
@Inject
constructor(
    private val mSmsRepository: SmsRepository,
) : ViewModel(), SenderContract.ViewModel {

    private var mSenderList: LiveData<List<Sender>>? = null
    private lateinit var mView: SenderContract.View

    init {
        Logger.log(Log.ASSERT, "viewmodel", "init")
        mSmsRepository.setViewModel(this)
        mSmsRepository.fetchSenderList()
    }

    fun getSenderList(): LiveData<List<Sender>>? {
        Logger.log(Log.ASSERT, "viewmodel", "getsenderlist")
        return mSenderList
    }

    fun onBlockedCheckboxClicked(name: String, isBlocked: Boolean) {
        mSmsRepository.updateBlockedStatus(name, isBlocked)
    }

    override fun onSenderListFetched(senderList: Flow<List<Sender>>) {
        Logger.log(Log.ASSERT, "viewmodel", "onSenderListFetched")
        mSenderList = senderList.asLiveData()
        mView.onDataFetched()
    }

    override fun showMessage(message: String) {
        Logger.log(Log.ASSERT, "viewmodel", "showmessage $message")
    }

    fun setView(view: SenderContract.View) {
        mView = view
    }

}
