package com.moviles.unaroom.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.moviles.unaroom.ui.components.AppButton
import com.moviles.unaroom.ui.components.AppTextField
import com.moviles.unaroom.ui.components.LoginHeader
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import com.moviles.unaroom.ui.theme.UnaRoomTheme
import com.moviles.unaroom.ui.theme.AppError
import com.moviles.unaroom.ui.theme.AppBackground
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
                    containerColor = AppError,
                    contentColor = AppBackground,
                    dismissActionContentColor = AppBackground,
                    shape = RoundedCornerShape(18.dp),
                    snackbarData = snackbarData
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginHeader()

            Spacer(modifier = Modifier.height(32.dp))

            EmailTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            LoginButton(
                onClick = {
                    when (viewModel.validateLogin()) {
                        is LoginResult.Success -> onLoginClick()
                        is LoginResult.EmptyFields -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Please fill in all fields",
                                    withDismissAction = true
                                )
                            }
                        }
                        is LoginResult.InvalidEmailFormat -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Please enter a valid email address",
                                    withDismissAction = true
                                )
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AppTextField(
        value = value,
        label = "Email",
        placeholder = "student@university.edu",
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AppTextField(
        value = value,
        label = "Password",
        placeholder = "•••••••",
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun LoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppButton(
        text = "Login",
        onClick = onClick,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    UnaRoomTheme {
        LoginScreen(onLoginClick = {})
    }
}
