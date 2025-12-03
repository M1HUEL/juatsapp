package com.itson.juatsapp.ui.controller;

import com.itson.juatsapp.domain.Chat;
import com.itson.juatsapp.domain.Usuario;

public interface ConversacionController {

    void cargarMisChats();

    void seleccionarChat(Chat chat);

    void enviarMensaje(String contenido);

    void clicBotonCrearChat();

    void crearChatConfirmado(String nombre, Usuario usuario1, Usuario usuario2);

    void verPerfil();

    void cerrarSesion();

    String obtenerNombreChat(Chat chat); // Helper para formatear nombre
}
