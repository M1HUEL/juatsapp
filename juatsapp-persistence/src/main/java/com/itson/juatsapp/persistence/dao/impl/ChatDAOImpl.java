package com.itson.juatsapp.persistence.dao.impl;

import com.itson.juatsapp.domain.Chat;
import com.itson.juatsapp.persistence.dao.ChatDAO;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import com.itson.juatsapp.persistence.mapper.ChatMapper;
import com.itson.juatsapp.persistence.util.MongoConnection;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ChatDAOImpl implements ChatDAO {

    private final MongoCollection<Document> collection;
    private final ChatMapper mapper;

    public ChatDAOImpl() {
        this.collection = MongoConnection.getInstance().getDatabase().getCollection("chats");
        this.mapper = new ChatMapper();
    }

    @Override
    public List<Chat> findAll() throws PersistenciaException {
        try {
            List<Chat> chats = new ArrayList<>();
            for (Document doc : collection.find()) {
                chats.add(mapper.toEntity(doc));
            }
            return chats;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al consultar todos los chats: " + e.getMessage());
        }
    }

    @Override
    public List<Chat> findAllByUsuario1Id(String usuario1Id) throws PersistenciaException {
        try {
            List<Chat> chats = new ArrayList<>();

            collection.find(Filters.eq("usuario1id", new ObjectId(usuario1Id)))
                    .forEach(doc -> chats.add(mapper.toEntity(doc)));

            return chats;
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID de usuario1 proporcionado no es válido.");
        } catch (MongoException e) {
            throw new PersistenciaException("Error al buscar chats por usuario 1: " + e.getMessage());
        }
    }

    @Override
    public List<Chat> findAllByUsuario2Id(String usuario2Id) throws PersistenciaException {
        try {
            List<Chat> chats = new ArrayList<>();

            collection.find(Filters.eq("usuario2id", new ObjectId(usuario2Id)))
                    .forEach(doc -> chats.add(mapper.toEntity(doc)));

            return chats;
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID de usuario2 proporcionado no es válido.");
        } catch (MongoException e) {
            throw new PersistenciaException("Error al buscar chats por usuario 2: " + e.getMessage());
        }
    }

    @Override
    public Chat findById(String id) throws PersistenciaException {
        try {
            Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return mapper.toEntity(doc);
            }
            return null;
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del chat no es válido.");
        } catch (MongoException e) {
            throw new PersistenciaException("Error al buscar chat por ID: " + e.getMessage());
        }
    }

    @Override
    public void crear(Chat chat) throws PersistenciaException {
        try {
            Document doc = mapper.toDocument(chat);
            collection.insertOne(doc);
            chat.setId(doc.getObjectId("_id").toString());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al crear el chat: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(String id, Chat chat) throws PersistenciaException {
        try {
            Document doc = mapper.toDocument(chat);
            collection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar el chat: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
        } catch (MongoException e) {
            throw new PersistenciaException("Error al eliminar el chat: " + e.getMessage());
        }
    }
}
