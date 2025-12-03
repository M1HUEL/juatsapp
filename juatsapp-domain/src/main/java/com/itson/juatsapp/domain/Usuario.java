package com.itson.juatsapp.domain;

import java.time.LocalDate;

public class Usuario {

    private String id;
    private String nombre;
    private String contrasena;
    private String telefono;
    private LocalDate fechaNacimiento;
    private Sexo sexo;
    private Direccion direccion;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String contrasena, String telefono, LocalDate fechaNacimiento, Sexo sexo, Direccion direccion) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.direccion = direccion;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", contrasena=" + contrasena + ", telefono=" + telefono + ", fechaNacimiento=" + fechaNacimiento + ", sexo=" + sexo + ", direccion=" + direccion + '}';
    }

}
