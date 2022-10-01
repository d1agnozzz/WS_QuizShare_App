package com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz.models

sealed class CreateQuizEvent {
    object QuizDone : CreateQuizEvent()

    data class QuizTitleChanged(
        val value: String
    ) : CreateQuizEvent()

    data class QuizDescriptionChanged(
        val value : String
    ) : CreateQuizEvent()

    data class QuizGroupChanged(
        val value: String
    ) : CreateQuizEvent()

    data class QuestionTitleChanged(
        val questionNumber: Int,
        val value: String
    ) : CreateQuizEvent()

    data class AnswerOptionClicked(
        val questionNumber: Int,
        val answerId: Int
    ) : CreateQuizEvent()

    data class AnswerContentChanged(
        val questionNumber: Int,
        val answerId: Int,
        val value: String
    ) : CreateQuizEvent()

    data class AddAnswerClicked(
        val questionNumber: Int
    ) : CreateQuizEvent()

    object AddQuestionClicked : CreateQuizEvent()

    data class RemoveQuestionClicked(
        val questionNumber: Int
    )  : CreateQuizEvent()

}