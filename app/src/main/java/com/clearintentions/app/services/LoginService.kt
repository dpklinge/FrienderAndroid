package com.clearintentions.app.services

import android.util.Log
import arrow.core.Either
import com.clearintentions.app.errors.ClearIntentionsError
import com.clearintentions.server.LoginControllerApi
import com.clearintentions.server.model.AppUser
import com.clearintentions.server.model.LoginPacket

class LoginService(
    private val loginControllerApi: LoginControllerApi,
    private val callHandler: CallHandlerService
) {
    suspend fun checkLoginInfo(username: String, password: String): Either<ClearIntentionsError, AppUser> {
        Log.d("app", "Calling loginservice at ${loginControllerApi.baseUrl}")
        val returnVal = callHandler.handle { loginControllerApi.loginUser(LoginPacket(username, password)) }
        Log.d("app", "Value received: $returnVal")
        return returnVal
    }
}
