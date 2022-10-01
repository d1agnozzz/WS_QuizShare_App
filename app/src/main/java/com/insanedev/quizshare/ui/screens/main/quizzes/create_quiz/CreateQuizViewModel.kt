package com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.insanedev.quizshare.common.EventHandler
import com.insanedev.quizshare.network.models.AnswerRespondRemote
import com.insanedev.quizshare.network.models.QuestionRespondRemote
import com.insanedev.quizshare.network.models.QuestionType
import com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz.models.CreateQuizEvent
import com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz.models.CreateQuizViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateQuizViewModel @Inject constructor() : ViewModel(), EventHandler<CreateQuizEvent> {

    private val _viewState = MutableLiveData(CreateQuizViewState())
    val viewState: LiveData<CreateQuizViewState> = _viewState

    override fun obtainEvent(event: CreateQuizEvent) {
        when (event) {
            is CreateQuizEvent.QuizTitleChanged -> quizTitleChanged(event.value)
            is CreateQuizEvent.QuizDescriptionChanged -> quizDescriptionChanged(event.value)
            is CreateQuizEvent.QuizGroupChanged -> quizGroupChanged(event.value)

            is CreateQuizEvent.AnswerContentChanged -> answerContentChanged(
                event.questionNumber,
                event.answerId,
                event.value
            )
            is CreateQuizEvent.AnswerOptionClicked -> answerOptionClicked(
                event.questionNumber,
                event.answerId
            )
            is CreateQuizEvent.QuestionTitleChanged -> questionTitleChanged(
                event.questionNumber,
                event.value
            )
            is CreateQuizEvent.RemoveQuestionClicked -> removeQuestionClicked(
                event.questionNumber
            )
            is CreateQuizEvent.AddAnswerClicked -> addAnswerClicked(
                event.questionNumber
            )
            CreateQuizEvent.AddQuestionClicked -> addQuestionClicked()
            CreateQuizEvent.QuizDone -> TODO()
        }
    }

    private fun quizDoneClicked() {

    }

    private fun quizTitleChanged(value: String) {
        _viewState.value = _viewState.value?.copy(
            titleValue = value
        )
    }

    private fun quizDescriptionChanged(value: String) {
        _viewState.value = _viewState.value?.copy(
            descriptionValue = value
        )
    }

    private fun quizGroupChanged(value: String) {
        _viewState.value = _viewState.value?.copy(
            groupValue = value
        )
    }


    // TODO
    private fun removeAnswer(questionNumber: Int, answerId: Int) {
        val questionMap = _viewState.value!!.questionMap.toMutableMap()
        val questionKey = questionMap.keys.single {
            it.questionNumber == questionNumber
        }
        val answersList = questionMap[questionKey]!!.toMutableList()

        if (answersList.size == 1) {
            return
        }

        val answerToRemove = answersList.filter { it.id == answerId }.single()



        if (answerToRemove.correct == true) {
            answerOptionClicked(questionNumber, answerToRemove.id - 1)
        }


        answersList.remove(answerToRemove)

        answersList.forEachIndexed() { i, e ->
            val newId = answersList[i].id - 1
            if (e.id > answerId) {
                answersList[i] = answersList[i].copy(id = newId)
            }
        }

        questionMap[questionKey] = answersList

        _viewState.value = _viewState.value?.copy(
            questionMap = questionMap
        )


    }

    private fun answerContentChanged(questionNumber: Int, answerId: Int, value: String) {


        val questionMap = _viewState.value!!.questionMap.toMutableMap()
        val questionKey = questionMap.keys.single {
            it.questionNumber == questionNumber
        }
        var answersList = questionMap[questionKey]

//        TODO
//        if (value.isBlank() && answersList!!.size != 1) {
//            removeAnswer(questionNumber, answerId)
//            return
//        }

        answersList = answersList!!.map { answer ->
            if (answer.id == answerId) {
                answer.copy(content = value)
            } else {
                answer
            }
        }

        questionMap[questionKey] = answersList

        _viewState.value = _viewState.value?.copy(questionMap = questionMap)
    }

    private fun questionTitleChanged(questionNumber: Int, value: String) {
        var questionMap = _viewState.value!!.questionMap

        questionMap = questionMap.mapKeys {
            if (it.key.questionNumber == questionNumber) {
                it.key.copy(title = value)
            } else {
                it.key
            }
        }

        _viewState.value = _viewState.value?.copy(questionMap = questionMap)


    }

    private fun answerOptionClicked(questionNumber: Int, answerId: Int) {
        val questionMap = _viewState.value!!.questionMap.toMutableMap()
        val questionKey = questionMap.keys.single() {
            it.questionNumber == questionNumber
        }
        var answersList = questionMap[questionKey]
        val previouslyChecked = answersList?.single {
            it.correct == true
        }

        if (previouslyChecked?.id != answerId) {
            answersList = answersList?.map {
                when (it.id) {
                    previouslyChecked!!.id -> it.copy(correct = false)
                    answerId -> it.copy(correct = true)
                    else -> it
                }
            }

            questionMap[questionKey] = answersList!!
            _viewState.value = _viewState.value?.copy(
                questionMap = questionMap
            )
        }


    }

    private fun addAnswerClicked(questionNumber: Int) {
        val questionMap = _viewState.value!!.questionMap.toMutableMap()
        val questionKey = questionMap.keys.single {
            it.questionNumber == questionNumber
        }
        val answersList = questionMap[questionKey]?.toMutableList()
        val answersSize = answersList!!.size
        answersList.add(
            AnswerRespondRemote(
                id = answersSize + 1,
                questionId = null,
                content = "Ответ ${answersSize + 1}",
                correct = false
            )
        )
        questionMap[questionKey] = answersList

        _viewState.value = _viewState.value?.copy(
            questionMap = questionMap
        )


    }

    private fun getInitialQuestionModel(
        questionNumber: Int,
        type: QuestionType = QuestionType.SingleChoice
    ) :QuestionRespondRemote {
        return QuestionRespondRemote(
            id = null,
            quizId = null,
            questionNumber = questionNumber,
            type = type,
            title = ""
        )
    }

    private fun getInitialAnswersList() : List<AnswerRespondRemote> {
        return listOf(
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
    }


    private fun addQuestionClicked() {
        val questionMap = _viewState.value!!.questionMap.toMutableMap()

        val newQuestion = getInitialQuestionModel(questionMap.size + 1)

        val newAnswersList = getInitialAnswersList()

        questionMap.put(
            key = newQuestion,
            value = newAnswersList
        )

        _viewState.value = _viewState.value?.copy(
            questionMap = questionMap
        )
    }

    private fun removeQuestionClicked(questionNumber: Int) {


        var questionMap = _viewState.value!!.questionMap.toMutableMap()

        if (questionMap.size == 1) {
            return
        }

        val questionKey = questionMap.keys.single {
            it.questionNumber == questionNumber
        }

        questionMap.remove(questionKey)

        questionMap = questionMap.mapKeys {
            if (it.key.questionNumber > questionNumber) {
                it.key.copy(
                    questionNumber = it.key.questionNumber - 1
                )
            } else {
                it.key
            }
        }.toMutableMap()

        _viewState.value = _viewState.value?.copy(
            questionMap = questionMap
        )
    }


}