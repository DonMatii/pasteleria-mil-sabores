package com.grupo8.apppasteleriamilsabores.data.model.springboot

import kotlinx.serialization.Serializable

@Serializable
data class ContactMessageRequest(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val mensaje: String
)