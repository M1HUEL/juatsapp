package com.itson.juatsapp.persistence.dao.impl;

import com.itson.juatsapp.domain.Sexo;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.persistence.dao.UsuarioDAO;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import com.itson.juatsapp.persistence.mapper.UsuarioMapper;
import com.itson.juatsapp.persistence.util.MongoConnection;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UsuarioDAOImpl implements UsuarioDAO {

    private final MongoCollection<Document> collection;
    private final UsuarioMapper mapper;

    public UsuarioDAOImpl() {
        this.collection = MongoConnection.getInstance().getDatabase().getCollection("usuarios");
        this.mapper = new UsuarioMapper();
    }

    @Override
    public List<Usuario> findAll() throws PersistenciaException {
        try {
            List<Usuario> lista = new ArrayList<>();
            for (Document doc : collection.find()) {
                lista.add(mapper.toEntity(doc));
            }
            return lista;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al consultar todos los usuarios: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> findAllBySexo(Sexo sexo) throws PersistenciaException {
        try {
            List<Usuario> lista = new ArrayList<>();
            for (Document doc : collection.find(Filters.eq("sexo", sexo.name()))) {
                lista.add(mapper.toEntity(doc));
            }
            return lista;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al consultar usuarios por sexo: " + e.getMessage());
        }
    }

    @Override
    public Usuario findById(String id) throws PersistenciaException {
        try {
            Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return mapper.toEntity(doc);
            }
            return null;
        } catch (IllegalArgumentException e) {
            throw new PersistenciaException("El ID proporcionado no es válido.");
        } catch (MongoException e) {
            throw new PersistenciaException("Error al buscar usuario por ID: " + e.getMessage());
        }
    }

    @Override
    public Usuario findByNombre(String nombre) throws PersistenciaException {
        try {
            Document doc = collection.find(Filters.eq("nombre", nombre)).first();
            if (doc != null) {
                return mapper.toEntity(doc);
            }
            return null;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al buscar usuario por nombre: " + e.getMessage());
        }
    }

    @Override
    public Usuario findByTelefono(String telefono) throws PersistenciaException {
        try {
            Document doc = collection.find(Filters.eq("telefono", telefono)).first();
            if (doc != null) {
                return mapper.toEntity(doc);
            }
            return null;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al buscar usuario por teléfono: " + e.getMessage());
        }
    }

    @Override
    public void crear(Usuario usuario) throws PersistenciaException {
        try {
            Document doc = mapper.toDocument(usuario);
            collection.insertOne(doc);
            usuario.setId(doc.getObjectId("_id").toString());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al crear el usuario: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(String id, Usuario usuario) throws PersistenciaException {
        try {
            Document doc = mapper.toDocument(usuario);
            collection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
        } catch (MongoException e) {
            throw new PersistenciaException("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
