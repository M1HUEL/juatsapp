package com.itson.juatsapp.persistence.dao;

import com.itson.juatsapp.domain.Mensaje;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import java.util.List;

public interface MensajeDAO {

    List<Mensaje> findAll() throws PersistenciaException;

    List<Mensaje> findAllByChatId(String chatId) throws PersistenciaException;

    List<Mensaje> findAllByUsuarioId(String usuarioId) throws PersistenciaException;

    Mensaje findById(String id) throws PersistenciaException;

    void crear(Mensaje mensaje) throws PersistenciaException;

    void actualizar(String id, Mensaje mensaje) throws PersistenciaException;

    void eliminar(String id) throws PersistenciaException;

}
