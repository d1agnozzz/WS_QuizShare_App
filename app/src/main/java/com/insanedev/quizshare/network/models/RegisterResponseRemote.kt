package com.insanedev.quizshare.network.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseRemote(
    val token: String
)