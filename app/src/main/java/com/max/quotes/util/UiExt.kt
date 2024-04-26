package com.max.quotes.util

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

fun AppCompatActivity.drawBehindSystemBars() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}
