package com.clearintentions.app.data.model

import com.clearintentions.server.model.AppUser.Gender as ApiGender

enum class Gender(val value: String) {
    MALE("Male"), FEMALE("Female"), NONBINARY("Nonbinary"), OTHER("Other"), PREFER_NOT_TO_SAY("Prefer not to say")
}

fun ApiGender.toAppGender() = Gender.valueOf(this.toString())
