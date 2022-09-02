package com.insanedev.quizshare.network.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RegisterReceiveRemote(
    val email: String,
    val name: String,
    val secondName: String,
    val patronymicName: String,
    val password: String
)

