package com.max.quotes.util

import android.content.res.Resources.getSystem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()

fun AppCompatActivity.configureFullScreenMode() {
    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    windowInsetsController.isAppearanceLightNavigationBars = true
}

fun AppCompatActivity.drawBehindSystemBars() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}
