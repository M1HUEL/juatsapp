package com.itson.juatsapp.domain;

public class Direccion {

    private String calle;
    private String colonia;
    private String ciudad;

    public Direccion() {
    }

    public Direccion(String calle, String colonia, String ciudad) {
        this.calle = calle;
        this.colonia = colonia;
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Direccion{" + "calle=" + calle + ", colonia=" + colonia + ", ciudad=" + ciudad + '}';
    }

}
