package com.clearintentions.app.utils

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.clearintentions.app.styles.AppModifiers

fun <T>changeActivity(context: Context, clazz: Class<T>, baggageKeys: Array<String>? = null, baggages: Array<Parcelable>? = null) {
    val intent = Intent(context, clazz)
    if (baggageKeys != null && baggages != null) {
        for (i in baggageKeys.indices) {
            intent.putExtra(baggageKeys[i], baggages[i])
        }
    }
    context.startActivity(intent)
}

fun <T>updateMutableField(field: MutableState<T>, value: T) {
    field.value = value
}

@Composable
fun defaultTextBlock(message: String, textAlign: TextAlign = TextAlign.Center, modifier: Modifier = AppModifiers.DEFAULT_TEXT, color: Color = Color.Black) =
    Text(text = message, textAlign = textAlign, modifier = modifier, color = color)
