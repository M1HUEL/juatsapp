package com.itson.juatsapp.business.service;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.domain.Mensaje;
import java.util.List;

public interface MensajeService {

    List<Mensaje> findAllMensajes() throws ServicioException;

    List<Mensaje> findAllMensajesByChatId(String chatId) throws ServicioException;

    List<Mensaje> findAllMensajesByUsuarioId(String usuarioId) throws ServicioException;

    Mensaje findMensajeById(String mensajeId) throws ServicioException;

    void crearMensaje(Mensaje mensaje) throws ServicioException;

    void actualizarMensaje(String mensajeId, Mensaje mensaje) throws ServicioException;

    void eliminarMensaje(String mensajeId) throws ServicioException;

}
