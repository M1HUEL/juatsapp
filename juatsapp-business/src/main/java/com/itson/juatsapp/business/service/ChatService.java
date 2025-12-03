package com.itson.juatsapp.business.service;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.domain.Chat;
import java.util.List;

public interface ChatService {

    List<Chat> findAllChats() throws ServicioException;

    List<Chat> findAllChatsByUsuario1Id(String usuario1Id) throws ServicioException;

    List<Chat> findAllChatsByUsuario2Id(String usuario2Id) throws ServicioException;

    Chat findChatById(String chatId) throws ServicioException;

    void crearChat(Chat chat) throws ServicioException;

    void actualizarChat(String chatId, Chat chat) throws ServicioException;

    void eliminarChat(String chatId) throws ServicioException;

}
