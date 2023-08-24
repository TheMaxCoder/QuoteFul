package com.max.quotes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.max.quotes.R
import com.max.quotes.data.db.Quote
import com.max.quotes.databinding.ItemMainListBinding
import logcat.logcat
import timber.log.Timber

class MainAdapter(val quotes: MutableList<Quote>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemMainListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quote) {
            binding.quote.text = binding.root.context.getString(R.string.text_quote, quote.content)
            binding.author.text = binding.root.context.getString(R.string.text_author_name, quote.author)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = quotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quotes[position])
    }

    fun submit(newList: List<Quote>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(newList, quotes))
        quotes.clear()
        quotes.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffCallback(private val newList: List<Quote>, private val oldList: List<Quote>) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
