package com.max.quotes.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.max.quotes.data.db.Quote
import com.max.quotes.databinding.ActivityMainBinding
import com.max.quotes.ui.adapters.MainAdapter
import com.max.quotes.ui.viewmodels.MainViewModel
import com.max.quotes.ui.viewmodels.UiState
import com.max.quotes.util.drawBehindSystemBars
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    private val quotesAdapter = MainAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drawBehindSystemBars()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()

        viewModel.uiState.observe(this) {
            processState(it)
        }
    }

    private fun setupViews() {
        setupRecyclerView()
        binding.chipFav.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.showFavoriteQuotes()
            } else {
                viewModel.showAllQuotes()
            }
        }
    }

    private fun setupRecyclerView() {
        quotesAdapter.setQuoteClickListener(object : MainAdapter.QuoteClickListener {
            override fun onFavoriteClicked(quote: Quote) {
                viewModel.updateFavorite(quote)
            }

            override fun onShareClicked(quote: Quote) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "${quote.content} \n ${quote.author}")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Share Quote")
                startActivity(shareIntent)
            }
        })

        with(binding.recyclerView) {
            adapter = quotesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun processState(uiState: UiState) {
        when (uiState) {
            is UiState.Success -> {
                showContentView()
                quotesAdapter.submit(uiState.quotes)
            }

            is UiState.Loading -> {
                showLoadingView()
            }

            is UiState.Error -> {
                showErrorView()
                Toast.makeText(this@MainActivity, uiState.errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showContentView() {
        binding.recyclerView.isVisible = true
        binding.chipFav.isVisible = true
        binding.animationView.isGone = true
    }

    private fun showLoadingView() {
        binding.animationView.isVisible = true
        binding.recyclerView.isGone = true
        binding.chipFav.isGone = true
    }

    private fun showErrorView() {
        binding.chipFav.isVisible = true
        binding.recyclerView.isGone = true
        binding.animationView.isGone = true
    }
}
