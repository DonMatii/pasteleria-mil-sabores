package com.grupo8.apppasteleriamilsabores.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Paleta base
val CremePastel = Color(0xFFFFF5E1) // #FFF5E1
val Durazno     = Color(0xFFFAD7A0) // #FAD7A0
val CafeOscuro  = Color(0xFF5D4037) // #5D4037
val Marron      = Color(0xFF8B4513) // #8B4513
val Rosa        = Color(0xFFFFC0CB) // #FFC0CB

val LightColors = lightColorScheme(
    primary      = CafeOscuro,
    onPrimary    = Color.White,
    secondary    = Durazno,
    onSecondary  = Color.Black,
    tertiary     = Rosa,
    background   = CremePastel,
    surface      = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface    = Color(0xFF1C1B1F)
)

val DarkColors = darkColorScheme(
    primary      = Durazno,
    onPrimary    = Color.Black,
    secondary    = CafeOscuro,
    onSecondary  = Color.White,
    tertiary     = Rosa,
    background   = Color(0xFF121212),
    surface      = Color(0xFF1E1E1E),
    onBackground = Color(0xFFEAEAEA),
    onSurface    = Color(0xFFEAEAEA)
)
