package com.clearintentions.app.ui.login

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.clearintentions.app.R
import com.clearintentions.app.data.model.Gender
import com.clearintentions.app.data.model.ParcelableString
import com.clearintentions.app.services.RegistrationService
import com.clearintentions.app.styles.AppModifiers
import com.clearintentions.app.utils.changeActivity
import com.clearintentions.app.utils.defaultTextBlock
import com.clearintentions.app.utils.updateMutableField
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    @Inject lateinit var registrationService: RegistrationService

    override fun onCreate(savedInstanceState: Bundle?) {
        val errorMessage = intent.extras?.getParcelable<ParcelableString>("errorMessage") ?: ""
        super.onCreate(savedInstanceState)
        setContent {
            val displayNameInput = remember { mutableStateOf("") }
            val passwordInput = remember { mutableStateOf("") }
            val passwordConfirmationInput = remember { mutableStateOf("") }
            val emailInput = remember { mutableStateOf("") }
            val phoneNumberInput = remember { mutableStateOf("") }
            val ageInput = remember { mutableStateOf(0) }
            val genderInput = remember { mutableStateOf(Gender.values()[0]) }
            val errorMessage = remember { mutableStateOf("") }
            RegistrationSubmission(
                displayNameInput = displayNameInput,
                passwordInput = passwordInput,
                passwordConfirmationInput = passwordConfirmationInput,
                emailInput = emailInput,
                phoneNumberInput = phoneNumberInput,
                ageInput = ageInput,
                genderInput = genderInput,
                errorMessage = errorMessage,
                registrationService = registrationService
            )
        }
    }
}

@Composable
fun RegistrationSubmission(
    modifier: Modifier = Modifier,
    displayNameInput: MutableState<String>,
    passwordInput: MutableState<String>,
    passwordConfirmationInput: MutableState<String>,
    emailInput: MutableState<String>,
    phoneNumberInput: MutableState<String>,
    ageInput: MutableState<Int>,
    genderInput: MutableState<Gender>,
    errorMessage: MutableState<String>,
    registrationService: RegistrationService
) {
    var expanded by remember { mutableStateOf(false) }
    val items = Gender.values()
    var selectedIndex by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier.fillMaxWidth(.75f), horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                label = { Text(stringResource(R.string.registration_input_display_name)) },
                value = displayNameInput.value,
                onValueChange = { updateMutableField(displayNameInput, it) },
                modifier = AppModifiers.FORM_INPUT_FIELD
            )
            TextField(
                label = { Text(stringResource(R.string.registration_input_password)) },
                value = passwordInput.value,
                onValueChange = { updateMutableField(passwordInput, it) },
                modifier = AppModifiers.FORM_INPUT_FIELD
            )
            TextField(
                label = { Text(stringResource(R.string.registration_input_password_confirmation)) },
                value = passwordConfirmationInput.value,
                onValueChange = { updateMutableField(passwordConfirmationInput, it) },
                modifier = AppModifiers.FORM_INPUT_FIELD
            )
            TextField(
                label = { Text(stringResource(R.string.registration_input_email)) },
                value = emailInput.value,
                onValueChange = { updateMutableField(emailInput, it) },
                modifier = AppModifiers.FORM_INPUT_FIELD
            )
            TextField(
                label = { Text(stringResource(R.string.registration_input_phone_number)) },
                value = phoneNumberInput.value,
                onValueChange = { updateMutableField(phoneNumberInput, it) },
                modifier = AppModifiers.FORM_INPUT_FIELD
            )
            TextField(
                label = { Text(stringResource(R.string.registration_input_age)) },
                value = ageInput.value.toString(),
                onValueChange = { updateMutableField(ageInput, it.toInt()) },
                modifier = AppModifiers.FORM_INPUT_FIELD,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Box(contentAlignment = Alignment.Center) {
                defaultTextBlock(
                    items[selectedIndex].toString(),
                    modifier = Modifier.fillMaxWidth().clickable(onClick = { expanded = true })
                        .background(
                            Color.LightGray
                        )
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth().background(
                        Color.LightGray
                    )
                ) {
                    items.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            selectedIndex = index
                            genderInput.value = items[index]
                            expanded = false
                        }) {
                            Text(text = s.value)
                        }
                    }
                }
            }
            val context = LocalContext.current
            Button(
                modifier = AppModifiers.CENTERED_BUTTON,
                onClick = {
                    register(
                        context,
                        displayNameInput,
                        passwordInput,
                        passwordConfirmationInput,
                        emailInput,
                        phoneNumberInput,
                        ageInput,
                        genderInput,
                        errorMessage,
                        registrationService
                    )
                }) {
                Text(stringResource(R.string.registration_submission_button))
            }
            Button(modifier = AppModifiers.CENTERED_BUTTON, onClick = { goToLogin(context) }) {
                Text(stringResource(R.string.go_to_login))
            }

            Text(text = errorMessage.value)
        }
    }
}

fun goToLogin(context: Context){
    changeActivity(context, LoginActivity::class.java)
}

fun register(
    context: Context,
    displayNameInput: MutableState<String>,
    passwordInput: MutableState<String>,
    passwordConfirmationInput: MutableState<String>,
    emailInput: MutableState<String>,
    phoneNumberInput: MutableState<String>,
    ageInput: MutableState<Int>,
    genderInput: MutableState<Gender>,
    errorMessage: MutableState<String>,
    registrationService: RegistrationService
) {
    CoroutineScope(Dispatchers.IO).launch {
        registrationService.registerNewUser(
            displayNameInput.value,
            passwordInput.value,
            passwordConfirmationInput.value,
            emailInput.value,
            phoneNumberInput.value,
            ageInput.value,
            genderInput.value
        )
            .fold({
                errorMessage.value = it.message
            }, {
                if (it.didSucceed) {
                    changeActivity(
                        context,
                        LoginActivity::class.java,
                        arrayOf("message"),
                        arrayOf(ParcelableString("Thanks for registering! Let's try out that login to make sure we got it right."))
                    )
                } else {
                    errorMessage.value = it.errors.joinToString()
                }
            })
    }
}
