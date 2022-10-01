package com.insanedev.quizshare.ui.screens.main.quizzes.models

sealed class QuizzesEvent {
    object createQuizClicked : QuizzesEvent()
    object joinQuizClicked : QuizzesEvent()
}