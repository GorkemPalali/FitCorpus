package com.example.fitcorpus.core.util

import timber.log.Timber

/**
 * Secure logging utility that redacts PII (Personally Identifiable Information)
 */
object Logger {
    
    private val piiPatterns = listOf(
        Regex("""password\s*[:=]\s*([^\s,}]+)""", RegexOption.IGNORE_CASE),
        Regex("""token\s*[:=]\s*([^\s,}]+)""", RegexOption.IGNORE_CASE),
        Regex("""email\s*[:=]\s*([^\s,}]+)""", RegexOption.IGNORE_CASE),
        Regex("""phone\s*[:=]\s*([^\s,}]+)""", RegexOption.IGNORE_CASE),
        Regex("""\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b"""), // Email pattern
        Regex("""\b\d{4}[\s-]?\d{4}[\s-]?\d{4}[\s-]?\d{4}\b"""), // Credit card pattern
    )
    
    /**
     * Redacts PII from log messages
     */
    private fun redactPII(message: String): String {
        var redacted = message
        piiPatterns.forEach { pattern ->
            redacted = pattern.replace(redacted) { matchResult ->
                "[REDACTED]"
            }
        }
        return redacted
    }
    
    fun d(message: String, vararg args: Any?) {
        Timber.d(redactPII(message), *args)
    }
    
    fun i(message: String, vararg args: Any?) {
        Timber.i(redactPII(message), *args)
    }
    
    fun w(message: String, vararg args: Any?) {
        Timber.w(redactPII(message), *args)
    }
    
    fun e(throwable: Throwable? = null, message: String, vararg args: Any?) {
        Timber.e(throwable, redactPII(message), *args)
    }
    
    fun v(message: String, vararg args: Any?) {
        Timber.v(redactPII(message), *args)
    }
}




