package com.joshgm3z.smsrelay.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.smsrelay.R
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterClickListener {

    private lateinit var mRvSenderList: RecyclerView
    private lateinit var mSenderAdapter: SenderAdapter
    private lateinit var mClErrorInfo: ConstraintLayout
    private lateinit var mClList: ConstraintLayout
    private lateinit var mTvErrorInfo: TextView
    private lateinit var mIvErrorInfo: ImageView
    private lateinit var mBtnPermission: Button

    private val senderListObserver = Observer<List<Sender>> { senderList ->
        Logger.log(Log.ASSERT, "mainactivity", "data change")
        mSenderAdapter.setList(senderList)
        checkSmsPermission()
    }

    @Inject
    lateinit var mSenderViewModel: SenderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        checkSmsPermission()
    }

    private fun initUI() {
        mRvSenderList = findViewById(R.id.rv_sender_list)
        mSenderAdapter = SenderAdapter(this)
        mRvSenderList.layoutManager = LinearLayoutManager(this)
        mRvSenderList.adapter = mSenderAdapter

        mClErrorInfo = findViewById(R.id.cl_info)
        mClList = findViewById(R.id.cl_sender_list)
        mTvErrorInfo = findViewById(R.id.tv_error_info)
        mIvErrorInfo = findViewById(R.id.iv_error_info)
        mBtnPermission = findViewById(R.id.btn_permission)

        mSenderViewModel.mSenderList?.observe(this, senderListObserver)
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
        val senderList = mSenderViewModel.mSenderList?.value
        if (senderList != null && senderList.isNotEmpty()) {
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

    override fun onBlockCheckboxToggle(name: String, isBlocked: Boolean) {
        mSenderViewModel.onBlockedCheckboxClicked(name, isBlocked)
    }

}