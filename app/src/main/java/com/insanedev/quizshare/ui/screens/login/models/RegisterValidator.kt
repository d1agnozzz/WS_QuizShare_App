package com.insanedev.quizshare.ui.screens.login.models

import com.insanedev.quizshare.R
import com.insanedev.quizshare.common.validateEmail

object RegisterValidator {
    fun getNameErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.requiered_field
            else -> null
        }
    }

    fun getEmailErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.requiered_field
            !validateEmail(input) -> R.string.email_not_valid
            else -> null
        }
    }

    fun getPasswordErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.requiered_field
            input.length < 4 -> R.string.password_too_short
            else -> null
        }
    }

    fun getPasswordRepeatErrorIdOrNull(input: String, inputRepeat: String): Int? {
        return when {
            input.isEmpty() -> R.string.requiered_field
            input != inputRepeat -> R.string.different_passwords
            else -> null
        }
    }
}