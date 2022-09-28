package com.insanedev.quizshare.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.insanedev.quizshare.R
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel,
    onTokenFound: (email: String) -> Unit,
    onTokenNotFound: () -> Unit
) {
    val viewState = splashViewModel.viewState.observeAsState(SplashViewState())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_splash_icon),
            contentDescription = "splash icon",
            modifier = Modifier.align(Alignment.Center)
        )
    }

    LaunchedEffect(key1 = viewState.value.splashAction, block = {
        when (val action = viewState.value.splashAction) {
            is SplashAction.OpenLoginScreen -> onTokenNotFound()
            is SplashAction.OpenMainScreen -> onTokenFound(action.email)
            else -> Unit
        }
    })
}

//@Preview(widthDp = 360, heightDp = 640, showSystemUi = true, showBackground = true)
//@Composable
//fun SplashPreview() {
//    SplashScreen {}
//}