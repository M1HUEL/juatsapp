package com.itson.juatsapp.persistence.dao;

import com.itson.juatsapp.domain.Sexo;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import java.util.List;

public interface UsuarioDAO {

    List<Usuario> findAll() throws PersistenciaException;

    List<Usuario> findAllBySexo(Sexo sexo) throws PersistenciaException;

    Usuario findById(String id) throws PersistenciaException;

    Usuario findByNombre(String nombre) throws PersistenciaException;

    Usuario findByTelefono(String telefono) throws PersistenciaException;

    void crear(Usuario usuario) throws PersistenciaException;

    void actualizar(String id, Usuario usuario) throws PersistenciaException;

    void eliminar(String id) throws PersistenciaException;

}
