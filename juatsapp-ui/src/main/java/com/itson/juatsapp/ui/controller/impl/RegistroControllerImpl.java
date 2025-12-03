package com.itson.juatsapp.ui.controller.impl;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.business.service.UsuarioService;
import com.itson.juatsapp.business.service.impl.UsuarioServiceImpl;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.ui.controller.RegistroController;
import com.itson.juatsapp.ui.frame.IniciarSesionFrame;
import com.itson.juatsapp.ui.frame.RegistroFrame;
import javax.swing.JOptionPane;

public class RegistroControllerImpl implements RegistroController {

    private final RegistroFrame frame;
    private final UsuarioService usuarioService;

    public RegistroControllerImpl(RegistroFrame frame) {
        this.frame = frame;
        this.usuarioService = new UsuarioServiceImpl();
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        if (usuario.getNombre().isEmpty() || usuario.getTelefono().isEmpty() || usuario.getContrasena().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor complete los campos obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            usuarioService.crearUsuario(usuario);
            JOptionPane.showMessageDialog(frame, "Cuenta creada exitosamente.\nPor favor inicie sesión.");
            frame.dispose();
            new IniciarSesionFrame().setVisible(true);

        } catch (ServicioException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "No se pudo registrar", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void cancelar() {
        frame.dispose();
        new IniciarSesionFrame().setVisible(true);
    }
}
