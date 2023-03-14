package com.clearintentions.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableString(val message: String) : Parcelable