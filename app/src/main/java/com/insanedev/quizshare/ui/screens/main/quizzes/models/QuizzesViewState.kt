package com.insanedev.quizshare.ui.screens.main.quizzes.models

import com.insanedev.quizshare.network.models.QuizesRespondRemote

data class QuizzesViewState(
    val quizList: List<QuizesRespondRemote>
)