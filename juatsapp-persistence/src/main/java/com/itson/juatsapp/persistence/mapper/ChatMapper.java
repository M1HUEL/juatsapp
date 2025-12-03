package com.itson.juatsapp.persistence.mapper;

import com.itson.juatsapp.domain.Chat;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ChatMapper {

    public Document toDocument(Chat chat) {
        Document doc = new Document();

        if (chat.getId() != null && !chat.getId().isEmpty()) {
            doc.append("_id", new ObjectId(chat.getId()));
        }

        if (chat.getNombre() != null) {
            doc.append("nombre", chat.getNombre());
        }

        if (chat.getUsuario1id() != null && !chat.getUsuario1id().isEmpty()) {
            doc.append("usuario1id", new ObjectId(chat.getUsuario1id()));
        }

        if (chat.getUsuario2id() != null && !chat.getUsuario2id().isEmpty()) {
            doc.append("usuario2id", new ObjectId(chat.getUsuario2id()));
        }

        return doc;
    }

    public Chat toEntity(Document doc) {
        Chat chat = new Chat();

        ObjectId id = doc.getObjectId("_id");
        if (id != null) {
            chat.setId(id.toString());
        }

        chat.setNombre(doc.getString("nombre"));

        ObjectId usuario1Id = doc.getObjectId("usuario1id");
        if (usuario1Id != null) {
            chat.setUsuario1id(usuario1Id.toString());
        }

        ObjectId usuario2Id = doc.getObjectId("usuario2id");
        if (usuario2Id != null) {
            chat.setUsuario2id(usuario2Id.toString());
        }

        return chat;
    }
}
