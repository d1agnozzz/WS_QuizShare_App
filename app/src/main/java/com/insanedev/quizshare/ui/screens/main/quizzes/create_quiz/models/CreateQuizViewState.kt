package com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz.models

import com.insanedev.quizshare.network.models.AnswerRespondRemote
import com.insanedev.quizshare.network.models.QuestionRespondRemote
import com.insanedev.quizshare.network.models.QuestionType

data class QuestionState(
    val questionNumber: Int,
    val title: String,
    val type: QuestionType
)


data class CreateQuizViewState(
    val titleValue: String = "",
    val descriptionValue: String = "",
    val groupValue: String = "",
    val questionMap: Map<QuestionRespondRemote, List<AnswerRespondRemote>>
        = mutableMapOf(
        Pair(
            QuestionRespondRemote(
                id = null,
                quizId = null,
                type = QuestionType.SingleChoice,
                title = "",
                questionNumber = 1,
            ),
            mutableListOf(
                AnswerRespondRemote(
                    id = 1,
                    questionId = null,
                    content = "Ответ 1",
                    correct = true
                ),
                AnswerRespondRemote(
                    id = 2,
                    questionId = null,
                    content = "Ответ 2",
                    correct = false
                )
            )
        )
    )

)
