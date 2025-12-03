package com.itson.juatsapp.business.service;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.domain.Sexo;
import com.itson.juatsapp.domain.Usuario;
import java.util.List;

public interface UsuarioService {

    List<Usuario> findAllUsuarios() throws ServicioException;

    List<Usuario> findAllUsuariosBySexo(Sexo sexo) throws ServicioException;

    Usuario findUsuarioById(String usuarioId) throws ServicioException;

    Usuario findUsuarioByNombre(String nombre) throws ServicioException;

    Usuario findUsuarioByTelefono(String telefono) throws ServicioException;

    Usuario login(String nombre, String contrasena) throws ServicioException;

    void crearUsuario(Usuario usuario) throws ServicioException;

    void actualizarUsuario(String usuarioId, Usuario usuario) throws ServicioException;

    void eliminarUsuario(String usuarioId) throws ServicioException;

}
