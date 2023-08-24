package com.max.quotes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.carousel.CarouselLayoutManager
import com.max.quotes.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    private val quotesAdapter = MainAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        viewModel.getQuotes().observe(this) {
            quotesAdapter.submit(it)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = quotesAdapter
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        val snapHelper = SnapHelperOneByOne()
        snapHelper.attachToRecyclerView(binding.recyclerView)
    }
}
