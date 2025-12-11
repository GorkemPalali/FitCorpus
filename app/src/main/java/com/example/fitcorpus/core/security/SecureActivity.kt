package com.example.fitcorpus.core.security

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity

/**
 * Base activity that applies security flags to prevent screenshots and screen recording
 */
abstract class SecureActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableSecureFlag()
    }
    
    override fun onResume() {
        super.onResume()
        enableSecureFlag()
    }
    
    private fun enableSecureFlag() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}




