package com.itson.juatsapp.domain;

import java.time.LocalDateTime;

public class Mensaje {

    private String id;
    private String chatId;
    private String usuarioId;
    private String contenido;
    private LocalDateTime timestamp;

    public Mensaje() {
    }

    public Mensaje(String id, String chatId, String usuarioId, String contenido, LocalDateTime timestamp) {
        this.id = id;
        this.chatId = chatId;
        this.usuarioId = usuarioId;
        this.contenido = contenido;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "id=" + id + ", chatId=" + chatId + ", usuarioId=" + usuarioId + ", contenido=" + contenido + ", timestamp=" + timestamp + '}';
    }

}
