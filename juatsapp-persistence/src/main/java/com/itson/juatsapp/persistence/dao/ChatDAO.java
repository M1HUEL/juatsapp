package com.itson.juatsapp.persistence.dao;

import com.itson.juatsapp.domain.Chat;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import java.util.List;

public interface ChatDAO {

    List<Chat> findAll() throws PersistenciaException;

    List<Chat> findAllByUsuario1Id(String usuario1Id) throws PersistenciaException;

    List<Chat> findAllByUsuario2Id(String usuario2Id) throws PersistenciaException;

    Chat findById(String id) throws PersistenciaException;

    void crear(Chat chat) throws PersistenciaException;

    void actualizar(String id, Chat chat) throws PersistenciaException;

    void eliminar(String id) throws PersistenciaException;

}
