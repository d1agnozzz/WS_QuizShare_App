package com.insanedev.quizshare.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val primaryBackground: Color,
    val primaryAccent: Color,
    val surface: Color,
    val secondaryContainer: Color,
    val background: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val completeStatus: Color,
    val textFieldUnfocusedBorder: Color
)

val lightPalette = Colors(
    primaryBackground = Color.White,
    primaryAccent = Color(0xff7F67BE),
    surface = Color(0xffF6EDFF),
    secondaryContainer = Color(0xffE8DEF8),
    background = Color(0xFFFFFFFF),
    primaryText = Color(0xff1C1B1F),
    secondaryText = Color(0xff49454F),
    completeStatus = Color(0xff66FFA3),
    textFieldUnfocusedBorder = Color(0x0C000000)
)