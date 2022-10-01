package com.insanedev.quizshare.network.models

@kotlinx.serialization.Serializable
data class AnswerRespondRemote(
    val id: Int,
    val questionId: Int?,
    val content: String,
    val correct: Boolean?
)