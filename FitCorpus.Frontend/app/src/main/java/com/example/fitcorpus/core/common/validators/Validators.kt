package com.example.fitcorpus.core.common.validators

object Validators {
    
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return emailRegex.toRegex().matches(email)
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && 
               password.any { it.isLetter() } && 
               password.any { it.isDigit() }
    }
    
    fun isValidName(name: String): Boolean {
        return name.trim().length >= 2 && name.all { it.isLetter() || it.isWhitespace() }
    }
    
    fun isValidHeight(height: Int): Boolean {
        return height in 50..250 // cm
    }
    
    fun isValidWeight(weight: Float): Boolean {
        return weight in 20f..300f // kg
    }
    
    fun isValidPrice(price: Int): Boolean {
        return price > 0
    }
    
    fun isValidDuration(duration: Int): Boolean {
        return duration > 0
    }
}