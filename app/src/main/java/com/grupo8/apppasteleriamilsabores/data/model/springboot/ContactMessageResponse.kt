package com.grupo8.apppasteleriamilsabores.data.model.springboot

import kotlinx.serialization.Serializable

@Serializable
data class ContactMessageResponse(
    val status: String,
    val message: String
)