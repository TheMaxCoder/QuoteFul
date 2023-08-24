package com.max.quotes.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.max.quotes.R
import com.max.quotes.data.db.Quote
import com.max.quotes.databinding.ItemMainListBinding

class MainAdapter(val quotes: MutableList<Quote>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemMainListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quote) {
            binding.quote.text = binding.root.context.getString(R.string.text_quote, quote.content)
            binding.author.text = binding.root.context.getString(R.string.text_author_name, quote.author)
            binding.buttonFavorite.setOnClickListener {
                val button = (it as Button)
                button.isSelected = button.isSelected.not()
                // TODO: Add to favorites..
            }
            binding.imageViewShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "${quote.content} \n ${quote.author}")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Share Quote")
                binding.root.context.startActivity(shareIntent)
            }
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
        val diffResult = DiffUtil.calculateDiff(MainAdapterDiffCallback(newList, quotes))
        quotes.clear()
        quotes.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}
