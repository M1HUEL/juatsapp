package com.itson.juatsapp.business.exception;

public class ServicioException extends Exception {

    public ServicioException() {
        super();
    }

    public ServicioException(String mensaje) {
        super(mensaje);
    }

    public ServicioException(Throwable causa) {
        super(causa);
    }

    public ServicioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
