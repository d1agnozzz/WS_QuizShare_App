package com.insanedev.quizshare.ui.screens.main.quizzes

import androidx.lifecycle.ViewModel
import com.insanedev.quizshare.common.EventHandler
import com.insanedev.quizshare.ui.screens.main.quizzes.models.QuizzesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizzesViewModel @Inject constructor() : ViewModel(), EventHandler<QuizzesEvent> {


    override fun obtainEvent(event: QuizzesEvent) {
        when (event) {
            is QuizzesEvent.createQuizClicked -> {}
            is QuizzesEvent.joinQuizClicked -> {}
        }
    }


}