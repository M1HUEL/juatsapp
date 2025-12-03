package com.itson.juatsapp.domain;

public class Chat {

    private String id;
    private String nombre;
    private String usuario1id;
    private String usuario2id;

    public Chat() {
    }

    public Chat(String id, String nombre, String usuario1id, String usuario2id) {
        this.id = id;
        this.nombre = nombre;
        this.usuario1id = usuario1id;
        this.usuario2id = usuario2id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario1id() {
        return usuario1id;
    }

    public void setUsuario1id(String usuario1id) {
        this.usuario1id = usuario1id;
    }

    public String getUsuario2id() {
        return usuario2id;
    }

    public void setUsuario2id(String usuario2id) {
        this.usuario2id = usuario2id;
    }

    @Override
    public String toString() {
        return "Chat{" + "id=" + id + ", nombre=" + nombre + ", usuario1id=" + usuario1id + ", usuario2id=" + usuario2id + '}';
    }

}
