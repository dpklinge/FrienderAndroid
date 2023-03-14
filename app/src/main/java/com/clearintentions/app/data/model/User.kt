package com.clearintentions.app.data.model

import android.os.Parcelable
import com.clearintentions.server.model.AppUser
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val displayName: String,
    val password: String,
    val email: String,
    val phoneNumber: String,
    val account: UserAccount,
    val searchPreferences: UserSearchPreferences,
    val gender: Gender,
    val age: String,
    val images: List<AppPicture>
) : Parcelable

@Parcelize
data class BasicUser(
    val displayName: String,
    val email: String,
    val phoneNumber: String,
    val gender: Gender,
    val age: Int
) : Parcelable

fun AppUser.toBasicAppUser(): BasicUser = BasicUser(this.displayName, this.email, this.phoneNumber, this.gender.toAppGender(), this.age)
