package com.joshgm3z.smsrelay.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.smsrelay.R
import com.joshgm3z.smsrelay.room.Sender
import com.joshgm3z.smsrelay.utils.SharedPref
import com.joshgm3z.smsrelay.utils.SortUtil

class SenderAdapter
constructor(
    val mSharedPrefs: SharedPref,
) : RecyclerView.Adapter<SenderViewHolder>(), AdapterClickListener {

    var mList: MutableList<Sender> = mutableListOf()
    var mBackupList: MutableList<Sender> = mutableListOf()

    var mCallback: AdapterClickListener? = null

    fun setCallback(callback: AdapterClickListener) {
        mCallback = callback
    }

    fun setList(list: List<Sender>) {
        if (mBackupList.isNotEmpty()) {
            mBackupList = SortUtil
                .sorted(list, mSharedPrefs.getSortOrder())
                .toMutableList()
        } else {
            mList = SortUtil
                .sorted(list, mSharedPrefs.getSortOrder())
                .toMutableList()
            notifyDataSetChanged()
        }
    }

    fun refreshList() {
        setList(mList)
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

    override fun onBlockCheckboxToggle(name: String, isBlocked: Boolean) {
        mCallback?.onBlockCheckboxToggle(name, isBlocked)
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

    fun filterList(keyword: String) {
        if (mBackupList.isEmpty())
            mBackupList = ArrayList(mList)
        val filter = mBackupList.filter { sender -> sender.name.contains(keyword, ignoreCase = true) }
        mList = filter.toMutableList()
        notifyDataSetChanged()
    }

    fun clearFilter() {
        if (mBackupList.isNotEmpty()) {
            mList = ArrayList(mBackupList)
            mBackupList.clear()
            notifyDataSetChanged()
        }
    }

}