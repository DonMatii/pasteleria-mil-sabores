package com.grupo8.apppasteleriamilsabores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.grupo8.apppasteleriamilsabores.ui.HomeScreen
import com.grupo8.apppasteleriamilsabores.ui.theme.AppPasteleriaMilSabores_Grupo8Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppPasteleriaMilSabores_Grupo8Theme {
                HomeScreen()
            }
        }
    }
}
