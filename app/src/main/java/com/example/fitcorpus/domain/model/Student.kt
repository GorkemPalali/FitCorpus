package com.example.fitcorpus.domain.model

data class Student(
    val id: String,
    val name: String,
    val surname: String,
    val photoUrl: String? = null,
    val matchId: String
)