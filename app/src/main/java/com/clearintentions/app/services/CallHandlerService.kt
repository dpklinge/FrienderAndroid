package com.clearintentions.app.services

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.clearintentions.app.errors.ClearIntentionsError
import com.clearintentions.app.errors.ClientError
import com.clearintentions.app.errors.ServerError
import com.clearintentions.app.errors.UnknownErrorException
import com.clearintentions.server.infrastructure.ClientException
import com.clearintentions.server.infrastructure.ServerException
import kotlinx.coroutines.runBlocking

class CallHandlerService {
    fun <T>handle(function: suspend () -> T): Either<ClearIntentionsError, T> = runBlocking {
        try {
            function.invoke().right()
        } catch (e: Exception) {
            when (e) {
                is ClientException -> processClientException(e)
                is ServerException -> processServerException(e)
                else -> processUnknownException(e)
            }
        }
    }

    private fun <T> processServerException(e: ServerException): Either<ClearIntentionsError, T> {
        Log.w("CallHandlerService", e.response!!.toString())
        return ServerError(e.message!!).left()
    }

    private fun <T> processClientException(e: ClientException): Either<ClearIntentionsError, T> {
        Log.d("CallHandlerService", e.response!!.toString())
        return ClientError(e.message!!).left()
    }
    private fun <T> processUnknownException(e: Exception): Either<ClearIntentionsError, T> {
        Log.e("CallHandlerService", e.message.toString())
        return UnknownErrorException("I'm sorry, something seems to have gone wrong - please try again!").left()
    }
}
