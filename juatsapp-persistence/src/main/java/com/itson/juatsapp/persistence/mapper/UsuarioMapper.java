package com.itson.juatsapp.persistence.mapper;

import com.itson.juatsapp.domain.Direccion;
import com.itson.juatsapp.domain.Sexo;
import com.itson.juatsapp.domain.Usuario;
import java.time.ZoneId;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UsuarioMapper {

    /**
     * Convierte un objeto de dominio Usuario a un Documento de MongoDB.
     *
     * @param usuario La entidad de dominio.
     * @return Documento listo para insertar en MongoDB.
     */
    public Document toDocument(Usuario usuario) {
        Document doc = new Document();

        if (usuario.getId() != null && !usuario.getId().isEmpty()) {
            doc.append("_id", new ObjectId(usuario.getId()));
        }

        doc.append("nombre", usuario.getNombre());
        doc.append("contrasena", usuario.getContrasena());
        doc.append("telefono", usuario.getTelefono());

        if (usuario.getFechaNacimiento() != null) {
            Date date = Date.from(usuario.getFechaNacimiento()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant());
            doc.append("fechaNacimiento", date);
        }

        if (usuario.getSexo() != null) {
            doc.append("sexo", usuario.getSexo().name());
        }

        if (usuario.getDireccion() != null) {
            Document direccionDoc = new Document();
            direccionDoc.append("calle", usuario.getDireccion().getCalle());
            direccionDoc.append("colonia", usuario.getDireccion().getColonia());
            direccionDoc.append("ciudad", usuario.getDireccion().getCiudad());
            doc.append("direccion", direccionDoc);
        }

        return doc;
    }

    /**
     * Convierte un Documento de MongoDB a un objeto de dominio Usuario.
     *
     * @param doc El documento recuperado de la base de datos.
     * @return Entidad Usuario mapeada.
     */
    public Usuario toEntity(Document doc) {
        Usuario usuario = new Usuario();

        ObjectId objectId = doc.getObjectId("_id");
        if (objectId != null) {
            usuario.setId(objectId.toString());
        }

        usuario.setNombre(doc.getString("nombre"));
        usuario.setContrasena(doc.getString("contrasena"));
        usuario.setTelefono(doc.getString("telefono"));

        Date date = doc.getDate("fechaNacimiento");
        if (date != null) {
            usuario.setFechaNacimiento(date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
        }

        String sexoStr = doc.getString("sexo");
        if (sexoStr != null) {
            usuario.setSexo(Sexo.valueOf(sexoStr));
        }

        Document direccionDoc = (Document) doc.get("direccion");
        if (direccionDoc != null) {
            Direccion direccion = new Direccion();
            direccion.setCalle(direccionDoc.getString("calle"));
            direccion.setColonia(direccionDoc.getString("colonia"));
            direccion.setCiudad(direccionDoc.getString("ciudad"));
            usuario.setDireccion(direccion);
        }

        return usuario;
    }
}
