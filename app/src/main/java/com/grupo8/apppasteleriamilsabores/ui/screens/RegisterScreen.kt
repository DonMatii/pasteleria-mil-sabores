package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar
import com.grupo8.apppasteleriamilsabores.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    vm: AuthViewModel,
    onRegistered: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Scaffold(
        topBar = { MilTopBar(title = "Crear cuenta") },
        bottomBar = { MilBottomNav(currentRoute, onNavigate) }
    ) { inner ->
        Column(Modifier.fillMaxSize().padding(inner).padding(16.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = apellido, onValueChange = { apellido = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = { vm.register(name, apellido, email, pass) { onRegistered() } },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Registrarme") }

            TextButton(onClick = { onNavigate("login") }) { Text("Ya tengo cuenta") }
        }
    }
}
