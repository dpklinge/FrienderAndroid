package com.clearintentions.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppPicture(val data: Array<Byte>) : Parcelable
