package com.clearintentions.app.ui.login

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.clearintentions.app.R
import com.clearintentions.app.data.model.ParcelableString
import com.clearintentions.app.data.model.toBasicAppUser
import com.clearintentions.app.services.LoginService
import com.clearintentions.app.styles.AppModifiers
import com.clearintentions.app.ui.home.HomePageActivity
import com.clearintentions.app.utils.changeActivity
import com.clearintentions.app.utils.defaultTextBlock
import com.clearintentions.app.utils.updateMutableField
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject lateinit var loginService: LoginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val message = intent.extras?.getParcelable<ParcelableString>("message")?.message ?: ""
        val errorMessage = intent.extras?.getParcelable<ParcelableString>("errorMessage")?.message ?: ""
        setContent {
            val usernameInput = remember { mutableStateOf("") }
            val passwordInput = remember { mutableStateOf("") }
            LoginSubmission(
                usernameInput = usernameInput,
                passwordInput = passwordInput,
                startErrorMessage = errorMessage,
                loginService = loginService,
                message = message
            )
        }
    }
}

@Composable
fun LoginSubmission(
    loginService: LoginService,
    message: String,
    usernameInput: MutableState<String>,
    passwordInput: MutableState<String>,
    startErrorMessage: String,
    modifier: Modifier = Modifier
) {
    val errorMessage = remember { mutableStateOf(startErrorMessage) }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        defaultTextBlock(message)
        defaultTextBlock(errorMessage.value, color = AppModifiers.ERROR_TEXT_COLOR)
        TextField(
            label = { defaultTextBlock(stringResource(R.string.username_login_input_label)) },
            value = usernameInput.value,
            onValueChange = { updateMutableField(usernameInput, it) },
            modifier = AppModifiers.FORM_INPUT_FIELD
        )
        TextField(
            label = { defaultTextBlock(stringResource(R.string.password_input_label)) },
            value = passwordInput.value,
            onValueChange = { updateMutableField(passwordInput, it) },
            modifier = AppModifiers.FORM_INPUT_FIELD
        )
        val context = LocalContext.current
        Button(modifier = AppModifiers.CENTERED_BUTTON, onClick = { login(context, usernameInput, passwordInput, errorMessage, loginService) }) {
            Text(stringResource(R.string.login_submission_button))
        }
        Button(modifier = AppModifiers.CENTERED_BUTTON, onClick = { goToRegistration(context) }) {
            Text(stringResource(R.string.login_page_registration_link))
        }
    }
}

fun goToRegistration(context: Context) {
    changeActivity(context, RegistrationActivity::class.java)
}

fun login(context: Context, usernameInput: MutableState<String>, passwordInput: MutableState<String>, resultMessage: MutableState<String>, loginService: LoginService) {
    CoroutineScope(Dispatchers.IO).launch {
        loginService.checkLoginInfo(usernameInput.value, passwordInput.value)
            .fold(
                { resultMessage.value = it.message },
                {
                    changeActivity(
                        context,
                        HomePageActivity::class.java,
                        arrayOf("user"),
                        arrayOf(it.toBasicAppUser())
                    )
                }
            )
    }
}
