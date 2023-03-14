package com.clearintentions.app.styles
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class AppModifiers {
    companion object {
        // Modifiers
        val FORM_INPUT_FIELD: Modifier = Modifier
            .background(color = Color.White)
            .padding(5.dp)
            .border(
                BorderStroke(5.dp, Brush.linearGradient(listOf(Color.Black, Color.DarkGray)))
            )
        val ERROR_TEXT_FORMAT: Modifier = Modifier.fillMaxWidth()
        val DEFAULT_TEXT: Modifier = Modifier.fillMaxWidth()
        val CENTERED_BUTTON: Modifier = Modifier.fillMaxWidth(.75f)

        // Colors
        val ERROR_TEXT_COLOR: Color = Color.Red
    }
}
