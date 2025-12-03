package com.itson.juatsapp.business.service.impl;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.business.service.UsuarioService;
import com.itson.juatsapp.domain.Sexo;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.persistence.dao.UsuarioDAO;
import com.itson.juatsapp.persistence.dao.impl.UsuarioDAOImpl;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    public List<Usuario> findAllUsuarios() throws ServicioException {
        try {
            return usuarioDAO.findAll();
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al obtener la lista de usuarios: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> findAllUsuariosBySexo(Sexo sexo) throws ServicioException {
        try {
            return usuarioDAO.findAllBySexo(sexo);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al obtener usuarios por sexo: " + e.getMessage());
        }
    }

    @Override
    public Usuario findUsuarioById(String usuarioId) throws ServicioException {
        try {
            Usuario usuario = usuarioDAO.findById(usuarioId);
            if (usuario == null) {
                throw new ServicioException("No se encontró ningún usuario con el ID proporcionado.");
            }
            return usuario;
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al buscar usuario por ID: " + e.getMessage());
        }
    }

    @Override
    public Usuario findUsuarioByNombre(String nombre) throws ServicioException {
        try {
            return usuarioDAO.findByNombre(nombre);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al buscar usuario por nombre: " + e.getMessage());
        }
    }

    @Override
    public Usuario findUsuarioByTelefono(String telefono) throws ServicioException {
        try {
            return usuarioDAO.findByTelefono(telefono);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al buscar usuario por teléfono: " + e.getMessage());
        }
    }

    @Override
    public Usuario login(String nombre, String contrasena) throws ServicioException {
        try {
            Usuario usuario = usuarioDAO.findByNombre(nombre);

            if (usuario == null) {
                throw new ServicioException("El usuario no existe.");
            }

            if (!usuario.getContrasena().equals(contrasena)) {
                throw new ServicioException("Contraseña incorrecta.");
            }

            return usuario;

        } catch (PersistenciaException e) {
            throw new ServicioException("Error durante el inicio de sesión: " + e.getMessage());
        }
    }

    @Override
    public void crearUsuario(Usuario usuario) throws ServicioException {
        try {
            Usuario existente = usuarioDAO.findByTelefono(usuario.getTelefono());

            if (existente != null) {
                throw new ServicioException("Ya existe un usuario registrado con el teléfono: " + usuario.getTelefono());
            }

            usuarioDAO.crear(usuario);
        } catch (PersistenciaException e) {
            throw new ServicioException("No se pudo registrar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void actualizarUsuario(String usuarioId, Usuario usuario) throws ServicioException {
        try {
            Usuario existente = usuarioDAO.findById(usuarioId);
            if (existente == null) {
                throw new ServicioException("No se puede actualizar. El usuario no existe.");
            }

            usuarioDAO.actualizar(usuarioId, usuario);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminarUsuario(String usuarioId) throws ServicioException {
        try {
            Usuario existente = usuarioDAO.findById(usuarioId);
            if (existente == null) {
                throw new ServicioException("No se puede eliminar. El usuario no existe.");
            }

            usuarioDAO.eliminar(usuarioId);
        } catch (PersistenciaException e) {
            throw new ServicioException("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
