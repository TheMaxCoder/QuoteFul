package com.max.quotes.ui

import androidx.recyclerview.widget.DiffUtil
import com.max.quotes.data.db.Quote

class MainAdapterDiffCallback(private val newList: List<Quote>, private val oldList: List<Quote>) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }