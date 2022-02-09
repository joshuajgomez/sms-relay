package com.joshgm3z.smsrelay.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.smsrelay.R
import com.joshgm3z.smsrelay.room.Sender

class SenderAdapter(
    private val mCallback: AdapterClickListener
) : RecyclerView.Adapter<SenderAdapter.SenderViewHolder>(), AdapterClickListener {

    var mList: MutableList<Sender> = mutableListOf()

    fun setList(list: List<Sender>) {
        mList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SenderViewHolder {
        val view = LayoutInflater
            .from(parent.context)
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

            mCbBlock.setOnCheckedChangeListener { _, isChecked ->
                if (mSender.isBlocked != isChecked) {
                    mCallback.onBlockCheckboxToggle(
                        sender.name,
                        isChecked
                    )
                }
            }
        }
    }

    override fun onBlockCheckboxToggle(name: String, isBlocked: Boolean) {
        mCallback.onBlockCheckboxToggle(name, isBlocked)
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
        if (!isKnown) {
            // new sender
            mList.add(sender)
            notifyItemChanged(mList.size - 1)
        } else {
            // sender already exists
            mList[counter] = sender
            notifyItemChanged(counter)
        }
    }

}