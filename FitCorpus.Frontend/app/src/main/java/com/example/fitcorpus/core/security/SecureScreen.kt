package com.example.fitcorpus.core.security

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import android.view.WindowManager

/**
 * Composable that applies FLAG_SECURE to prevent screenshots
 * Use this for payment and code screens
 */
@Composable
fun SecureScreen(content: @Composable () -> Unit) {
    val view = LocalView.current
    
    DisposableEffect(Unit) {
        val activity = view.context as? android.app.Activity
        val window = activity?.window
        
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
    
    content()
}

