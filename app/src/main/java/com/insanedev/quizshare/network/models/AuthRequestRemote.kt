package com.insanedev.quizshare.network.models

@kotlinx.serialization.Serializable
data class AuthRequestRemote (
    val token: String
)