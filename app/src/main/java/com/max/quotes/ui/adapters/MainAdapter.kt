package com.max.quotes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.max.quotes.R
import com.max.quotes.data.db.Quote
import com.max.quotes.databinding.ItemMainListBinding

class MainAdapter(val quotes: MutableList<Quote>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private lateinit var clickListener: QuoteClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = quotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quotes[position], clickListener)
    }

    fun setQuoteClickListener(listener: QuoteClickListener) {
        this.clickListener = listener
    }

    fun submit(newList: List<Quote>) {
        val diffResult = DiffUtil.calculateDiff(MainAdapterDiffCallback(newList, quotes))
        quotes.clear()
        quotes.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ItemMainListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quote, quoteClickListener: QuoteClickListener) {
            binding.quote.text = binding.root.context.getString(R.string.text_quote, quote.content)
            binding.author.text = binding.root.context.getString(R.string.text_author_name, quote.author)
            binding.buttonFavorite.setOnClickListener {
                it.isSelected = it.isSelected.not()
                quoteClickListener.onFavoriteClicked(quote)
            }
            binding.buttonShareQuote.setOnClickListener {
                quoteClickListener.onShareClicked(quote)
            }
        }
    }

    interface QuoteClickListener {
        fun onFavoriteClicked(quote: Quote)
        fun onShareClicked(quote: Quote)
    }
}
