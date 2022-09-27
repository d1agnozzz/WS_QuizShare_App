package com.insanedev.quizshare.ui.components

sealed class QuizType {
    object Quiz : QuizType()
    data class Test(val correctQuestions: Int, val totalQuestions: Int) : QuizType()
}