package com.itson.juatsapp.ui.controller.impl;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.business.service.UsuarioService;
import com.itson.juatsapp.business.service.impl.UsuarioServiceImpl;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.ui.controller.InformacionContactoController;
import com.itson.juatsapp.ui.frame.InformacionContactoFrame;
import javax.swing.JOptionPane;

public class InformacionContactoControllerImpl implements InformacionContactoController {

    private final InformacionContactoFrame frame;
    private final UsuarioService usuarioService;

    public InformacionContactoControllerImpl(InformacionContactoFrame frame) {
        this.frame = frame;
        this.usuarioService = new UsuarioServiceImpl();
    }

    @Override
    public void guardarCambios(Usuario usuarioOriginal, Usuario datosNuevos) {
        try {
            if (datosNuevos.getNombre().isEmpty() || datosNuevos.getTelefono().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "El nombre y el teléfono son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            datosNuevos.setId(usuarioOriginal.getId());
            usuarioService.actualizarUsuario(usuarioOriginal.getId(), datosNuevos);

            frame.actualizarDatosVista(datosNuevos);

            JOptionPane.showMessageDialog(frame, "Perfil actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (ServicioException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "No se pudo actualizar", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
