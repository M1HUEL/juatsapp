package com.itson.juatsapp.persistence.dao.impl;

import com.itson.juatsapp.domain.Mensaje;
import com.itson.juatsapp.persistence.dao.MensajeDAO;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import com.itson.juatsapp.persistence.mapper.MensajeMapper;
import com.itson.juatsapp.persistence.util.MongoConnection;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MensajeDAOImpl implements MensajeDAO {

    private final MongoCollection<Document> collection;
    private final MensajeMapper mapper;

    public MensajeDAOImpl() {
        this.collection = MongoConnection.getInstance().getDatabase().getCollection("mensajes");
        this.mapper = new MensajeMapper();
    }

    @Override
    public List<Mensaje> findAll() throws PersistenciaException {
        try {
            List<Mensaje> mensajes = new ArrayList<>();
            for (Document doc : collection.find()) {
                mensajes.add(mapper.toEntity(doc));
            }
            return mensajes;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al consultar todos los mensajes: " + e.getMessage());
        }
    }

    @Override
    public List<Mensaje> findAllByChatId(String chatId) throws PersistenciaException {
        try {
            List<Mensaje> mensajes = new ArrayList<>();

            collection.find(Filters.eq("chatId", new ObjectId(chatId)))
                    .sort(Sorts.ascending("timestamp"))
                    .forEach(doc -> mensajes.add(mapper.toEntity(doc)));

            return mensajes;
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del chat proporcionado no es válido.");
        } catch (MongoException e) {
            throw new PersistenciaException("Error al consultar mensajes del chat: " + e.getMessage());
        }
    }

    @Override
    public List<Mensaje> findAllByUsuarioId(String usuarioId) throws PersistenciaException {
        try {
            List<Mensaje> mensajes = new ArrayList<>();

            collection.find(Filters.eq("usuarioId", new ObjectId(usuarioId)))
                    .forEach(doc -> mensajes.add(mapper.toEntity(doc)));

            return mensajes;
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del usuario proporcionado no es válido.");
        } catch (MongoException e) {
            throw new PersistenciaException("Error al consultar mensajes del usuario: " + e.getMessage());
        }
    }

    @Override
    public Mensaje findById(String id) throws PersistenciaException {
        try {
            Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return mapper.toEntity(doc);
            }
            return null;
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID del mensaje no es válido.");
        } catch (MongoException e) {
            throw new PersistenciaException("Error al buscar mensaje por ID: " + e.getMessage());
        }
    }

    @Override
    public void crear(Mensaje mensaje) throws PersistenciaException {
        try {
            Document doc = mapper.toDocument(mensaje);
            collection.insertOne(doc);
            mensaje.setId(doc.getObjectId("_id").toString());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar el mensaje: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(String id, Mensaje mensaje) throws PersistenciaException {
        try {
            Document doc = mapper.toDocument(mensaje);
            collection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar el mensaje: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
        } catch (MongoException e) {
            throw new PersistenciaException("Error al eliminar el mensaje: " + e.getMessage());
        }
    }
}
