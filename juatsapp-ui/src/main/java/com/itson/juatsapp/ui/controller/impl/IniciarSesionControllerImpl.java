package com.itson.juatsapp.ui.controller.impl;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.business.service.UsuarioService;
import com.itson.juatsapp.business.service.impl.UsuarioServiceImpl;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.ui.controller.IniciarSesionController;
import com.itson.juatsapp.ui.frame.IniciarSesionFrame;
import com.itson.juatsapp.ui.frame.RegistroFrame;
import com.itson.juatsapp.ui.frame.ConversacionFrame;
import javax.swing.JOptionPane;

public class IniciarSesionControllerImpl implements IniciarSesionController {

    private final IniciarSesionFrame frame;
    private final UsuarioService usuarioService;

    public IniciarSesionControllerImpl(IniciarSesionFrame frame) {
        this.frame = frame;
        this.usuarioService = new UsuarioServiceImpl();
    }

    @Override
    public void iniciarSesion(String nombre, String contrasena) {
        try {
            if (nombre.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor ingrese todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Aquí se asume que el servicio ahora busca por nombre
            Usuario usuarioLogueado = usuarioService.login(nombre, contrasena);

            JOptionPane.showMessageDialog(frame, "Bienvenido " + usuarioLogueado.getNombre());

            frame.dispose();

            ConversacionFrame ventana = new ConversacionFrame(usuarioLogueado);
            ventana.setVisible(true);

        } catch (ServicioException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Ocurrió un error inesperado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void navegarARegistro() {
        frame.dispose();
        new RegistroFrame().setVisible(true);
    }
}
