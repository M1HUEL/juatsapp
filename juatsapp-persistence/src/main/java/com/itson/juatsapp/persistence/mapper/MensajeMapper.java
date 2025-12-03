package com.itson.juatsapp.persistence.mapper;

import com.itson.juatsapp.domain.Mensaje;
import java.time.ZoneId;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MensajeMapper {

    public Document toDocument(Mensaje mensaje) {
        Document doc = new Document();

        if (mensaje.getId() != null && !mensaje.getId().isEmpty()) {
            doc.append("_id", new ObjectId(mensaje.getId()));
        }

        if (mensaje.getChatId() != null && !mensaje.getChatId().isEmpty()) {
            doc.append("chatId", new ObjectId(mensaje.getChatId()));
        }

        if (mensaje.getUsuarioId() != null && !mensaje.getUsuarioId().isEmpty()) {
            doc.append("usuarioId", new ObjectId(mensaje.getUsuarioId()));
        }

        doc.append("contenido", mensaje.getContenido());

        if (mensaje.getTimestamp() != null) {
            Date date = Date.from(mensaje.getTimestamp()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            doc.append("timestamp", date);
        }

        return doc;
    }

    public Mensaje toEntity(Document doc) {
        Mensaje mensaje = new Mensaje();

        ObjectId id = doc.getObjectId("_id");
        if (id != null) {
            mensaje.setId(id.toString());
        }

        ObjectId chatId = doc.getObjectId("chatId");
        if (chatId != null) {
            mensaje.setChatId(chatId.toString());
        }

        ObjectId usuarioId = doc.getObjectId("usuarioId");
        if (usuarioId != null) {
            mensaje.setUsuarioId(usuarioId.toString());
        }

        mensaje.setContenido(doc.getString("contenido"));

        Date date = doc.getDate("timestamp");
        if (date != null) {
            mensaje.setTimestamp(date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }

        return mensaje;
    }
}
