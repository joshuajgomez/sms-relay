package com.joshgm3z.smsrelay.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.smsrelay.R
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.Logger

class SenderAdapter(
    private val mContext: Context,
    private val mCallback: AdapterClickListener
) : RecyclerView.Adapter<SenderAdapter.SenderViewHolder>(), AdapterClickListener {

    private lateinit var mList: MutableList<Sender>

    fun setList(list: List<Sender>) {
        mList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SenderViewHolder {
        val view = LayoutInflater
            .from(mContext)
            .inflate(R.layout.sender_item, parent, false)
        return SenderViewHolder(view, mCallback)
    }

    override fun onBindViewHolder(holder: SenderViewHolder, position: Int) {
        holder.setData(mList[position], position + 1)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SenderViewHolder(
        mItemView: View,
        private val mCallback: AdapterClickListener
    ) : RecyclerView.ViewHolder(mItemView) {

        private var mTvName: TextView = itemView.findViewById(R.id.tv_sender_name)
        private var mTvCount: TextView = itemView.findViewById(R.id.tv_count)
        private var mTvSerialNumber: TextView = itemView.findViewById(R.id.tv_serial_number)
        private var mCbBlock: CheckBox = itemView.findViewById(R.id.cb_block)
        private lateinit var mSender: Sender

        fun setData(sender: Sender, serialNumber: Int) {
            mSender = sender
            mTvName.text = sender.name
            var countText: String = sender.count.toString()
            countText += if (sender.count == 1) " message" else " messages"
            mTvCount.text = countText
            mTvSerialNumber.text = serialNumber.toString()
            mCbBlock.isChecked = sender.isBlocked

            mCbBlock.setOnCheckedChangeListener { _, isChecked ->
                mCallback?.onBlockChanged(
                    sender.name,
                    isChecked
                )
            }
        }
    }

    override fun onBlockChanged(name: String, isBlocked: Boolean) {
        mCallback.onBlockChanged(name, isBlocked)
    }

    fun updateSender(sender: Sender) {
        var counter: Int = 0
        var isKnown = false
        for (_sender in mList) {
            if (_sender.name == sender.name) {
                isKnown = true
                break;
            }
            counter++
        }
        Logger.log(Log.ASSERT, "adapter before", mList.toString())
        Logger.log(Log.ASSERT, "counter", counter.toString())
        if (!isKnown) {
            // new sender
            mList.add(sender)
            notifyItemChanged(mList.size - 1)
        } else {
            // sender already exists
            mList[counter] = sender
            notifyItemChanged(counter)
        }
        Logger.log(Log.ASSERT, "adapter after", mList.toString())
    }

}