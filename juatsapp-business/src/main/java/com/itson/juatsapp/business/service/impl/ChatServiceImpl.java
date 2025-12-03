package com.itson.juatsapp.business.service.impl;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.business.service.ChatService;
import com.itson.juatsapp.domain.Chat;
import com.itson.juatsapp.persistence.dao.ChatDAO;
import com.itson.juatsapp.persistence.dao.impl.ChatDAOImpl;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import java.util.List;

public class ChatServiceImpl implements ChatService {

    private final ChatDAO chatDAO;

    public ChatServiceImpl() {
        this.chatDAO = new ChatDAOImpl();
    }

    @Override
    public List<Chat> findAllChats() throws ServicioException {
        try {
            return chatDAO.findAll();
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al obtener la lista de chats: " + e.getMessage());
        }
    }

    @Override
    public List<Chat> findAllChatsByUsuario1Id(String usuario1Id) throws ServicioException {
        try {
            if (usuario1Id == null || usuario1Id.trim().isEmpty()) {
                throw new ServicioException("El ID del usuario 1 es requerido.");
            }
            return chatDAO.findAllByUsuario1Id(usuario1Id);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al buscar chats del usuario (remitente): " + e.getMessage());
        }
    }

    @Override
    public List<Chat> findAllChatsByUsuario2Id(String usuario2Id) throws ServicioException {
        try {
            if (usuario2Id == null || usuario2Id.trim().isEmpty()) {
                throw new ServicioException("El ID del usuario 2 es requerido.");
            }
            return chatDAO.findAllByUsuario2Id(usuario2Id);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al buscar chats del usuario (receptor): " + e.getMessage());
        }
    }

    @Override
    public Chat findChatById(String chatId) throws ServicioException {
        try {
            Chat chat = chatDAO.findById(chatId);
            if (chat == null) {
                throw new ServicioException("No se encontró el chat con el ID especificado.");
            }
            return chat;
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al buscar el chat: " + e.getMessage());
        }
    }

    @Override
    public void crearChat(Chat chat) throws ServicioException {
        try {
            if (chat.getUsuario1id() == null || chat.getUsuario2id() == null) {
                throw new ServicioException("Se requieren dos usuarios para crear un chat.");
            }

            if (chat.getUsuario1id().equals(chat.getUsuario2id())) {
                throw new ServicioException("No puedes iniciar un chat contigo mismo.");
            }

            List<Chat> chatsIniciados = chatDAO.findAllByUsuario1Id(chat.getUsuario1id());
            for (Chat c : chatsIniciados) {
                if (c.getUsuario2id().equals(chat.getUsuario2id())) {
                    throw new ServicioException("Ya existe un chat abierto entre estos usuarios.");
                }
            }

            List<Chat> chatsRecibidos = chatDAO.findAllByUsuario2Id(chat.getUsuario1id());
            for (Chat c : chatsRecibidos) {
                if (c.getUsuario1id().equals(chat.getUsuario2id())) {
                    throw new ServicioException("Ya existe un chat abierto entre estos usuarios.");
                }
            }

            chatDAO.crear(chat);

        } catch (PersistenciaException e) {
            throw new ServicioException("Error al crear el chat: " + e.getMessage());
        }
    }

    @Override
    public void actualizarChat(String chatId, Chat chat) throws ServicioException {
        try {
            if (chatDAO.findById(chatId) == null) {
                throw new ServicioException("El chat que intenta actualizar no existe.");
            }
            chatDAO.actualizar(chatId, chat);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al actualizar el chat: " + e.getMessage());
        }
    }

    @Override
    public void eliminarChat(String chatId) throws ServicioException {
        try {
            if (chatDAO.findById(chatId) == null) {
                throw new ServicioException("El chat que intenta eliminar no existe.");
            }
            chatDAO.eliminar(chatId);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al eliminar el chat: " + e.getMessage());
        }
    }
}
