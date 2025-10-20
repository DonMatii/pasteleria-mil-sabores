package com.grupo8.apppasteleriamilsabores.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo8.apppasteleriamilsabores.R

// Fuentes locales (en res/font/)
private val Lato = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold)
)

private val Pacifico = FontFamily(
    Font(R.font.pacifico_regular, FontWeight.Normal)
)

private val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Pacifico, fontWeight = FontWeight.Normal, fontSize = 30.sp, lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Pacifico, fontWeight = FontWeight.Normal, fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Lato, fontWeight = FontWeight.Bold, fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Lato, fontWeight = FontWeight.Normal, fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Lato, fontWeight = FontWeight.Medium, fontSize = 14.sp
    )
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small      = RoundedCornerShape(10.dp),
    medium     = RoundedCornerShape(16.dp),
    large      = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun MilSaboresTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val scheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = scheme,
        typography  = AppTypography,
        shapes      = AppShapes,
        content     = content
    )
}
