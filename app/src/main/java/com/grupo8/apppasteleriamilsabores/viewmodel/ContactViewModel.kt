package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.grupo8.apppasteleriamilsabores.data.api.ContactRetrofitClient
import com.grupo8.apppasteleriamilsabores.data.model.springboot.ContactMessageRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * ViewModel para gestionar el envío y almacenamiento de mensajes de contacto
 * Ahora intenta enviar primero a Spring Boot, luego fallback a Firestore
 */
class ContactViewModel : ViewModel() {

    /**
     * Función para guardar mensajes de contacto
     * PRIMERO intenta Spring Boot, LUEGO Firestore como fallback
     */
    fun sendContactMessage(
        nombre: String,
        apellido: String,
        correo: String,
        mensaje: String
    ) {
        viewModelScope.launch {
            // PRIMERO: Intentar enviar a Spring Boot
            val springBootSuccess = trySendToSpringBoot(nombre, apellido, correo, mensaje)

            if (springBootSuccess) {
                println("✅ CONTACTO: Mensaje guardado en Spring Boot - $nombre $apellido")
            } else {
                // FALLBACK: Enviar a Firestore
                trySendToFirestore(nombre, apellido, correo, mensaje)
                println("✅ CONTACTO: Mensaje guardado en Firestore (fallback) - $nombre $apellido")
            }
        }
    }

    /**
     * Intenta enviar el mensaje al microservicio Spring Boot
     * Retorna true si fue exitoso, false si falló
     */
    private suspend fun trySendToSpringBoot(
        nombre: String,
        apellido: String,
        correo: String,
        mensaje: String
    ): Boolean {
        return try {
            // Crear request para Spring Boot
            val contactMessage = ContactMessageRequest(
                nombre = nombre,
                apellido = apellido,
                correo = correo,
                mensaje = mensaje
            )

            val response = ContactRetrofitClient.contactService.enviarMensaje(contactMessage)

            // Verificar si fue exitoso
            response.isSuccessful && response.body()?.status == "success"

        } catch (e: Exception) {
            println("❌ CONTACTO: Error enviando a Spring Boot - ${e.message}")
            false
        }
    }

    /**
     * Función original para enviar a Firestore
     * Se usa como fallback si Spring Boot falla
     */
    private suspend fun trySendToFirestore(
        nombre: String,
        apellido: String,
        correo: String,
        mensaje: String
    ) {
        try {
            // Preparar datos del mensaje para Firestore
            val contactData = mapOf(
                "nombre" to nombre,           // Nombre del usuario que contacta
                "apellido" to apellido,       // Apellido del usuario
                "correo" to correo,          // Correo electrónico para respuesta
                "mensaje" to mensaje,        // Contenido del mensaje o consulta
                "timestamp" to Timestamp.now() // Fecha y hora del mensaje
            )

            // Guardar en Firestore en la colección "contact_messages"
            FirebaseFirestore.getInstance()
                .collection("contact_messages") // Colección para mensajes de contacto
                .add(contactData) // Agregar documento con ID automático
                .await() // Esperar a que se complete la operación

        } catch (e: Exception) {
            // Manejo de errores en caso de fallo al guardar
            println("❌ CONTACTO: Error guardando en Firestore - ${e.message}")
            e.printStackTrace()
        }
    }
}