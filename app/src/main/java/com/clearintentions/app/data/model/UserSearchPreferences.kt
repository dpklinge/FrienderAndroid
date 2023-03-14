package com.clearintentions.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserSearchPreferences(
    val intentions: List<String>,
    val desiredIntentions: List<String>,
    val desiredGenders: List<Gender>,
    val desiredMinAge: Int,
    val desiredMaxAge: Int,
    val desiredMaxDistance: Int
) : Parcelable
