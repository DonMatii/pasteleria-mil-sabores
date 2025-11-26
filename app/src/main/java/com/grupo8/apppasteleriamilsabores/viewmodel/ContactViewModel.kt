package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * ViewModel para gestionar el envío y almacenamiento de mensajes de contacto
 * Se encarga de la comunicación con Firestore para guardar consultas de usuarios
 */
class ContactViewModel : ViewModel() {

    /**
     * Función para guardar mensajes de contacto en Firestore Database
     * Crea un documento en la colección "contact_messages" con los datos del formulario
     */
    fun sendContactMessage(
        nombre: String,
        apellido: String,
        correo: String,
        mensaje: String
    ) {
        viewModelScope.launch {
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

                // Mensaje de confirmación en logs para debugging
                println("✅ CONTACTO: Mensaje guardado en Firestore - $nombre $apellido")

            } catch (e: Exception) {
                // Manejo de errores en caso de fallo al guardar
                println("❌ CONTACTO: Error guardando mensaje - ${e.message}")
                e.printStackTrace()
            }
        }
    }
}