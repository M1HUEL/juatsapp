package com.itson.juatsapp.business.service.impl;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.business.service.MensajeService;
import com.itson.juatsapp.domain.Mensaje;
import com.itson.juatsapp.persistence.dao.MensajeDAO;
import com.itson.juatsapp.persistence.dao.impl.MensajeDAOImpl;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

public class MensajeServiceImpl implements MensajeService {

    private final MensajeDAO mensajeDAO;

    public MensajeServiceImpl() {
        this.mensajeDAO = new MensajeDAOImpl();
    }

    @Override
    public List<Mensaje> findAllMensajes() throws ServicioException {
        try {
            return mensajeDAO.findAll();
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al recuperar todos los mensajes: " + e.getMessage());
        }
    }

    @Override
    public List<Mensaje> findAllMensajesByChatId(String chatId) throws ServicioException {
        try {
            if (chatId == null || chatId.trim().isEmpty()) {
                throw new ServicioException("El ID del chat es requerido.");
            }
            return mensajeDAO.findAllByChatId(chatId);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al recuperar el historial del chat: " + e.getMessage());
        }
    }

    @Override
    public List<Mensaje> findAllMensajesByUsuarioId(String usuarioId) throws ServicioException {
        try {
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                throw new ServicioException("El ID del usuario es requerido.");
            }
            return mensajeDAO.findAllByUsuarioId(usuarioId);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al recuperar los mensajes del usuario: " + e.getMessage());
        }
    }

    @Override
    public Mensaje findMensajeById(String mensajeId) throws ServicioException {
        try {
            Mensaje mensaje = mensajeDAO.findById(mensajeId);
            if (mensaje == null) {
                throw new ServicioException("No se encontró el mensaje con el ID especificado.");
            }
            return mensaje;
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al buscar el mensaje: " + e.getMessage());
        }
    }

    @Override
    public void crearMensaje(Mensaje mensaje) throws ServicioException {
        try {
            if (mensaje.getChatId() == null || mensaje.getUsuarioId() == null) {
                throw new ServicioException("El mensaje debe estar vinculado a un Chat y a un Usuario.");
            }
            if (mensaje.getContenido() == null || mensaje.getContenido().trim().isEmpty()) {
                throw new ServicioException("No se puede enviar un mensaje vacío.");
            }
            mensaje.setTimestamp(LocalDateTime.now());
            mensajeDAO.crear(mensaje);
        } catch (PersistenciaException e) {
            throw new ServicioException("No se pudo enviar el mensaje: " + e.getMessage());
        }
    }

    @Override
    public void actualizarMensaje(String mensajeId, Mensaje mensaje) throws ServicioException {
        try {
            if (mensajeDAO.findById(mensajeId) == null) {
                throw new ServicioException("El mensaje que intenta actualizar no existe.");
            }
            if (mensaje.getContenido() == null || mensaje.getContenido().trim().isEmpty()) {
                throw new ServicioException("El contenido del mensaje editado no puede estar vacío.");
            }
            mensajeDAO.actualizar(mensajeId, mensaje);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al actualizar el mensaje: " + e.getMessage());
        }
    }

    @Override
    public void eliminarMensaje(String mensajeId) throws ServicioException {
        try {
            if (mensajeDAO.findById(mensajeId) == null) {
                throw new ServicioException("El mensaje que intenta eliminar no existe.");
            }
            mensajeDAO.eliminar(mensajeId);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al eliminar el mensaje: " + e.getMessage());
        }
    }
}
