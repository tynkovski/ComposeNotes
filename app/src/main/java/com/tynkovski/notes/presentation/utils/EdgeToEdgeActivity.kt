package com.tynkovski.notes.presentation.utils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat

abstract class EdgeToEdgeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}