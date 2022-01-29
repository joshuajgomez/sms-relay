package com.joshgm3z.smsrelay.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.smsrelay.R
import com.joshgm3z.smsrelay.domain.SmsManager
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SenderContract.View {

    private lateinit var mRvSenderList: RecyclerView
    private lateinit var mSenderAdapter: SenderAdapter

    private lateinit var mClErrorInfo: ConstraintLayout
    private lateinit var mClList: ConstraintLayout
    private lateinit var mTvErrorInfo: TextView
    private lateinit var mIvErrorInfo: ImageView
    private lateinit var mBtnPermission: Button

    @Inject
    lateinit var mSmsManager: SmsManager

//    private val mViewModel: SenderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRvSenderList = findViewById(R.id.rv_sender_list)

        mSmsManager.mView = this

        mSenderAdapter = SenderAdapter(applicationContext, mSmsManager)

        mRvSenderList.layoutManager = LinearLayoutManager(this)
        mRvSenderList.adapter = mSenderAdapter

        initUI()
        val allSenders = mSmsManager.getAllSenders()
        mSenderAdapter.setList(allSenders)

        checkSmsPermission()
    }

    private fun initUI() {
        mClErrorInfo = findViewById(R.id.cl_info)
        mClList = findViewById(R.id.cl_sender_list)
        mTvErrorInfo = findViewById(R.id.tv_error_info)
        mIvErrorInfo = findViewById(R.id.iv_error_info)
        mBtnPermission = findViewById(R.id.btn_permission)

        mIvErrorInfo.setOnClickListener { mSmsManager.registerSender("Test1") }
    }

    private fun checkSmsPermission() {
        val status = checkSelfPermission(Manifest.permission.RECEIVE_SMS)
        if (status == PackageManager.PERMISSION_GRANTED) {
            // permission already present
            showListUI()
        } else {
            showPermissionErrorUI()
        }
    }

    private fun showListUI() {
        if (mSenderAdapter.mList.isNotEmpty()) {
            // non-empty list
            mClErrorInfo.visibility = View.GONE
            mClList.visibility = View.VISIBLE
        } else {
            // empty list
            mTvErrorInfo.text = getString(R.string.string_error_empty_list)
            mClErrorInfo.visibility = View.VISIBLE
            mClList.visibility = View.GONE
            mBtnPermission.visibility = View.INVISIBLE
        }
    }

    private fun showPermissionErrorUI() {
        mClList.visibility = View.GONE
        mClErrorInfo.visibility = View.VISIBLE
        mBtnPermission.visibility = View.VISIBLE
        mTvErrorInfo.text = getString(R.string.string_error_permission)
        mBtnPermission.setOnClickListener {
            // permission not granted. send request
            requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                // if list is empty, show some message
                showListUI()
            } else {
                // Show error
                showPermissionErrorUI()
            }
        }

    override fun updateData(list: List<Sender>) {
        mSenderAdapter.setList(list)
        showListUI()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateSender(sender: Sender) {
        if (!mRvSenderList.isComputingLayout) {
            mSenderAdapter.updateSender(sender)
        }
        showListUI()
    }

}