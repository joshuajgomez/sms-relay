package com.joshgm3z.smsrelay.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.smsrelay.R
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.Logger
import com.joshgm3z.smsrelay.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var mRvSenderList: RecyclerView

    @Inject
    lateinit var mSenderAdapter: SenderAdapter

    private lateinit var mClErrorInfo: LinearLayout
    private lateinit var mClList: LinearLayout
    private lateinit var mTvErrorInfo: TextView
    private lateinit var mIvErrorInfo: ImageView
    private lateinit var mBtnPermission: Button

    @Inject
    lateinit var sharedPref: SharedPref

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
        mRvSenderList.layoutManager = LinearLayoutManager(this)
        mRvSenderList.adapter = mSenderAdapter
        mSenderAdapter.setCallback(this)

        mClErrorInfo = findViewById(R.id.cl_info)
        mClList = findViewById(R.id.cl_sender_list)
        mTvErrorInfo = findViewById(R.id.tv_error_info)
        mIvErrorInfo = findViewById(R.id.iv_error_info)
        mBtnPermission = findViewById(R.id.btn_permission)

        mSenderViewModel.mSenderList?.observe(this, senderListObserver)

        val spinner: Spinner = findViewById(R.id.spinner_sort_order)
        ArrayAdapter.createFromResource(
            this,
            R.array.sort_order,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(sharedPref.getSortOrder())
        spinner.onItemSelectedListener = this

        val rlSearchContainer: RelativeLayout = findViewById(R.id.rl_search_view)
        val rlToolBar: RelativeLayout = findViewById(R.id.rl_tool_bar)
        val ivSearch: ImageView = findViewById(R.id.iv_search)
        val ivClear: ImageView = findViewById(R.id.iv_clear)
        val ivBackSearch: ImageView = findViewById(R.id.iv_back)
        val etSearchText: EditText = findViewById(R.id.et_search)
        rlSearchContainer.visibility = View.GONE
        rlToolBar.visibility = View.VISIBLE
        ivSearch.setOnClickListener {
            rlToolBar.visibility = View.GONE
            rlSearchContainer.visibility = View.VISIBLE
            showKeyboard(etSearchText)
        }
        ivBackSearch.setOnClickListener {
            rlSearchContainer.visibility = View.GONE
            rlToolBar.visibility = View.VISIBLE
            etSearchText.text.clear()
            hideKeyboard(etSearchText)
            mSenderAdapter.clearFilter()
        }
        ivClear.setOnClickListener {
            etSearchText.text.clear()
            mSenderAdapter.clearFilter()
        }
        etSearchText.addTextChangedListener { text ->
            if (text != null) {
                if (text.trim().isNotEmpty())
                    mSenderAdapter.filterList(text.trim().toString())
                else if (text.isEmpty())
                    mSenderAdapter.clearFilter()
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        view.requestFocus()
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

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, item: Int, p3: Long) {
        sharedPref.setSortOrder(item)
        mSenderAdapter.refreshList()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}