package com.example.fitcorpus.core.security

import okhttp3.CertificatePinner as OkHttpCertificatePinner

/**
 * Certificate pinning configuration for production
 * 
 * IMPORTANT: Update these pins with your actual server certificates
 * To get certificate pins, use: openssl s_client -connect your-domain.com:443 -showcerts
 * Then extract the public key hash using: openssl x509 -pubkey -noout -in cert.pem | openssl pkey -pubin -outform der | openssl dgst -sha256 -binary | openssl enc -base64
 */
object CertificatePinner {
    
    /**
     * Creates certificate pinner for production builds
     * For debug builds, returns null (no pinning)
     */
    fun create(hostname: String, isDebug: Boolean = false): OkHttpCertificatePinner? {
        if (isDebug) {
            // No pinning in debug mode for easier development
            return null
        }
        
        // TODO: Replace with actual certificate pins from your server
        // Example format: "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="
        val pins: List<String> = listOf(
            // Add your certificate pins here
            // "sha256/your-certificate-pin-here",
        )
        
        if (pins.isEmpty()) {
            // No pins configured, return null
            return null
        }
        
        return OkHttpCertificatePinner.Builder()
            .apply {
                pins.forEach { pin ->
                    add(hostname, pin)
                }
            }
            .build()
    }
}

