package com.clearintentions.app.services

import arrow.core.Either
import arrow.core.left
import com.clearintentions.app.errors.ClearIntentionsError
import com.clearintentions.app.errors.PasswordMismatch
import com.clearintentions.server.RegistrationControllerApi
import com.clearintentions.server.model.RegistrationInput
import com.clearintentions.server.model.RegistrationOutcome
import com.clearintentions.app.data.model.Gender as AppGender

class RegistrationService(
    private val registrationControllerApi: RegistrationControllerApi,
    private val callHandler: CallHandlerService
) {
    fun registerNewUser(
        displayName: String,
        password: String,
        passwordConfirmation: String,
        email: String,
        phoneNumber: String,
        age: Int,
        gender: AppGender
    ): Either<ClearIntentionsError, RegistrationOutcome> {
        return if (password == passwordConfirmation) {
            callHandler.handle { registrationControllerApi.registerUser(RegistrationInput(displayName, password, passwordConfirmation, email, phoneNumber, RegistrationInput.Gender.valueOf(gender.toString()), age)) }
        } else {
            PasswordMismatch("Passwords did not match!").left()
        }
    }
}
