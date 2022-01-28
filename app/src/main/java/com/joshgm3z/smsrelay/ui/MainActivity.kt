package com.joshgm3z.smsrelay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joshgm3z.smsrelay.R
import com.joshgm3z.smsrelay.SmsManager
import com.joshgm3z.smsrelay.room.Sender

class MainActivity : AppCompatActivity(), SenderContract.View {

    private lateinit var mRvSenderList: RecyclerView
    private lateinit var mSenderAdapter: SenderAdapter
    private lateinit var mSmsManager: SmsManager

//    private val mViewModel: SenderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRvSenderList = findViewById(R.id.rv_sender_list)
        mSmsManager = SmsManager(applicationContext, this)

        mSenderAdapter = SenderAdapter(applicationContext, mSmsManager)
        val allSenders = mSmsManager.getAllSenders()
        mSenderAdapter.setList(allSenders)

        mRvSenderList.layoutManager = LinearLayoutManager(this)
        mRvSenderList.adapter = mSenderAdapter
    }

    override fun updateData(list: List<Sender>) {
        mSenderAdapter.setList(list)
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateSender(sender: Sender) {
        if (!mRvSenderList.isComputingLayout) {
            mSenderAdapter.updateSender(sender)
        }
    }

}