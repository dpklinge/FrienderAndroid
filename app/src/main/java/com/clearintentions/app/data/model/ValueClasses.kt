package com.clearintentions.app.data.model

import android.util.Patterns

@JvmInline
value class DisplayName(val value: String) {
    init {
        require(value.length <= 50)
    }
}

@JvmInline
value class Password(val value: String)

@JvmInline
value class Email(val value: String) {
    init {
        require(Patterns.EMAIL_ADDRESS.matcher(value).matches())
    }
}

@JvmInline
value class PhoneNumber(val value: String) {
    init {
        require(Patterns.PHONE.matcher(value).matches())
    }
}

@JvmInline
value class Age(val value: Int) {
    init {
        require(value in 19..122)
    }
}

@JvmInline
value class Intention(val value: String) {
    init {
        require(value.length <= 50)
    }
}
