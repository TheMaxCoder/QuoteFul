package com.max.quotes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.max.quotes.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    private val quotesAdapter = MainAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = quotesAdapter

        viewModel.getQuotes().observe(this) {
            quotesAdapter.submit(it)
        }
    }


}