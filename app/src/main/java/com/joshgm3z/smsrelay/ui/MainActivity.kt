package com.joshgm3z.smsrelay.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.smsrelay.R
import com.joshgm3z.smsrelay.domain.SmsManager
import com.joshgm3z.smsrelay.room.Sender
import dagger.hilt.android.AndroidEntryPoint
import java.security.Permission
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SenderContract.View {

    private lateinit var mRvSenderList: RecyclerView
    private lateinit var mSenderAdapter: SenderAdapter

    @Inject
    lateinit var mSmsManager: SmsManager

//    private val mViewModel: SenderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRvSenderList = findViewById(R.id.rv_sender_list)

        mSmsManager.mView = this

        mSenderAdapter = SenderAdapter(applicationContext, mSmsManager)
        val allSenders = mSmsManager.getAllSenders()
        mSenderAdapter.setList(allSenders)

        mRvSenderList.layoutManager = LinearLayoutManager(this)
        mRvSenderList.adapter = mSenderAdapter

        checkSmsPermission()
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
        var layoutError: ConstraintLayout = findViewById(R.id.cl_info)
        var layoutList: ConstraintLayout = findViewById(R.id.cl_sender_list)
        layoutError.visibility = View.GONE
        layoutList.visibility = View.VISIBLE
    }

    private fun showPermissionErrorUI() {
        var layoutError: ConstraintLayout = findViewById(R.id.cl_info)
        var layoutList: ConstraintLayout = findViewById(R.id.cl_sender_list)
        layoutError.visibility = View.VISIBLE
        layoutList.visibility = View.GONE

        val btnPermission: Button = findViewById(R.id.btn_permission)
        btnPermission.setOnClickListener {
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