package com.max.quotes.ui

import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun AppCompatActivity.setFullScreen() {
//    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
//    windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
//    windowInsetsController.isAppearanceLightNavigationBars = true
    drawBehindSysBars()
}

private fun AppCompatActivity.drawBehindSysBars() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}