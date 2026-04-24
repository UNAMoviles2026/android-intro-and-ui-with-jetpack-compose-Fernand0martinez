package com.moviles.unaroom.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun validateLogin(): LoginResult {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        return when {
            email.isBlank() || password.isBlank() -> LoginResult.EmptyFields
            !emailPattern.matcher(email).matches() -> LoginResult.InvalidEmailFormat
            else -> LoginResult.Success
        }
    }
}

sealed class LoginResult {
    object Success : LoginResult()
    object EmptyFields : LoginResult()
    object InvalidEmailFormat : LoginResult()
}
