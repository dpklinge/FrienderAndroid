package com.clearintentions.app.errors

sealed class ClearIntentionsError(val message: String)
class ServerError(message: String) : ClearIntentionsError(message)
class ClientError(message: String) : ClearIntentionsError(message)
class PasswordMismatch(message: String) : ClearIntentionsError(message)
class UnknownErrorException(message: String) : ClearIntentionsError(message)
