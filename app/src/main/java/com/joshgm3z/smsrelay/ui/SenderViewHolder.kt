package com.joshgm3z.smsrelay.ui

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.smsrelay.R
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.DateUtil

class SenderViewHolder(
    mItemView: View,
    private val mCallback: AdapterClickListener?
    ) : RecyclerView.ViewHolder(mItemView) {

        private var mTvName: TextView = itemView.findViewById(R.id.tv_sender_name)
        private var mTvTime: TextView = itemView.findViewById(R.id.tv_time)
        private var mTvCount: TextView = itemView.findViewById(R.id.tv_count)
        private var mTvSerialNumber: TextView = itemView.findViewById(R.id.tv_serial_number)
        private var mCbBlock: CheckBox = itemView.findViewById(R.id.cb_block)
        private lateinit var mSender: Sender

        init {
            mTvName = itemView.findViewById(R.id.tv_sender_name)
            mTvCount = itemView.findViewById(R.id.tv_count)
            mTvSerialNumber = itemView.findViewById(R.id.tv_serial_number)
            mCbBlock = itemView.findViewById(R.id.cb_block)
            mItemView.setOnClickListener { mCbBlock.performClick() }
        }


        fun setData(sender: Sender, serialNumber: Int) {
            mSender = sender
            mTvName.text = sender.name
            var countText: String = sender.count.toString()
            countText += if (sender.count == 1) " message" else " messages"
            mTvCount.text = countText
            mTvSerialNumber.text = serialNumber.toString()
            mCbBlock.isChecked = sender.isBlocked
            mTvTime.text = DateUtil.getTextDate(sender.dateModified)

            mCbBlock.setOnCheckedChangeListener { _, isChecked ->
                if (mSender.isBlocked != isChecked) {
                    mCallback?.onBlockCheckboxToggle(
                        sender.name,
                        isChecked
                    )
                }
            }
        }
    }