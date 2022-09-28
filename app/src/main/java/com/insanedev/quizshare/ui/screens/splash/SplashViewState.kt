package com.insanedev.quizshare.ui.screens.splash

sealed class SplashAction {
    data class OpenMainScreen(val email: String): SplashAction()
    object OpenLoginScreen : SplashAction()
    object None: SplashAction()
}

data class SplashViewState(
    val splashAction: SplashAction = SplashAction.None
)