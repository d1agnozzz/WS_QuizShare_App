package com.insanedev.quizshare.network.models

@kotlinx.serialization.Serializable
data class AuthResponseRemote(
    val email: String
)