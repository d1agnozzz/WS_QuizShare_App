package com.insanedev.quizshare.ui.screens.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.insanedev.quizshare.common.AuthResult
import com.insanedev.quizshare.common.EventHandler
import com.insanedev.quizshare.data.tokenStore
import com.insanedev.quizshare.network.ApiService
import com.insanedev.quizshare.ui.screens.login.models.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(application: Application) : AndroidViewModel(application),
    EventHandler<LoginEvent> {

    private val apiService by lazy {
        ApiService.create()
    }

    init {
        checkToken()
    }

    private val _viewState: MutableLiveData<SplashViewState> = MutableLiveData(SplashViewState())
    val viewState: LiveData<SplashViewState> = _viewState

    val context = application.applicationContext

//    val tokenFlow: Flow<Token> = context.tokenStore.data.catch { exception ->
//        if (exception is IOException) {
//            Log.e("E", "Error reading sort order preferences.", exception)
//            emit(Token.getDefaultInstance())
//        } else {
//            throw exception
//        }
//    }

    // val allValid = MediatorLiveData<Boolean>()


    // val context = getApplication<Application>()

    override fun obtainEvent(event: LoginEvent) {
        when (event) {
            else -> {}
        }
    }

    private suspend fun updateToken(token: String) {
        context.tokenStore.updateData { currentToken ->
            currentToken.toBuilder().setToken(token).build()
        }
    }



    private fun checkToken() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = apiService.tryAuth(context.tokenStore.data.first().token)
            when (result) {
                is AuthResult.Ok -> _viewState.postValue(_viewState.value?.copy(splashAction = SplashAction.OpenMainScreen(result.email)))
                is AuthResult.Err -> _viewState.postValue(_viewState.value?.copy(splashAction = SplashAction.OpenLoginScreen))
            }
        }
    }


}