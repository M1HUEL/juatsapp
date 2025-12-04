package com.itson.juatsapp.ui.controller.impl;

import com.itson.juatsapp.business.exception.ServicioException;
import com.itson.juatsapp.business.service.ChatService;
import com.itson.juatsapp.business.service.MensajeService;
import com.itson.juatsapp.business.service.UsuarioService;
import com.itson.juatsapp.business.service.impl.ChatServiceImpl;
import com.itson.juatsapp.business.service.impl.MensajeServiceImpl;
import com.itson.juatsapp.business.service.impl.UsuarioServiceImpl;
import com.itson.juatsapp.domain.Chat;
import com.itson.juatsapp.domain.Mensaje;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.ui.controller.ConversacionController;
import com.itson.juatsapp.ui.frame.ConversacionFrame;
import com.itson.juatsapp.ui.frame.InformacionContactoFrame;
import com.itson.juatsapp.ui.frame.IniciarSesionFrame;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ConversacionControllerImpl implements ConversacionController {

    private final ConversacionFrame frame;
    private final Usuario usuarioActual;

    private final ChatService chatService;
    private final MensajeService mensajeService;
    private final UsuarioService usuarioService;

    private Chat chatSeleccionado;

    public ConversacionControllerImpl(ConversacionFrame frame, Usuario usuarioActual) {
        this.frame = frame;
        this.usuarioActual = usuarioActual;
        this.chatService = new ChatServiceImpl();
        this.mensajeService = new MensajeServiceImpl();
        this.usuarioService = new UsuarioServiceImpl();
    }

    @Override
    public void cargarMisChats() {
        try {
            List<Chat> todosLosChats = new ArrayList<>();

            List<Chat> chatsU1 = chatService.findAllChatsByUsuario1Id(usuarioActual.getId());
            if (chatsU1 != null) {
                todosLosChats.addAll(chatsU1);
            }

            List<Chat> chatsU2 = chatService.findAllChatsByUsuario2Id(usuarioActual.getId());
            if (chatsU2 != null) {
                todosLosChats.addAll(chatsU2);
            }

            frame.actualizarListaChats(todosLosChats);

        } catch (ServicioException se) {
            System.err.println("Advertencia al cargar chats: " + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error crítico al cargar chats: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void seleccionarChat(Chat chat) {
        this.chatSeleccionado = chat;
        cargarMensajesDelChatActual();
    }

    private void cargarMensajesDelChatActual() {
        if (chatSeleccionado == null) {
            return;
        }

        try {
            List<Mensaje> mensajes = mensajeService.findAllMensajesByChatId(chatSeleccionado.getId());
            StringBuilder sb = new StringBuilder();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");

            for (Mensaje m : mensajes) {
                String nombreAutor = "Desconocido";

                if (m.getUsuarioId() != null && m.getUsuarioId().equals(usuarioActual.getId())) {
                    nombreAutor = "Tú";
                } else {
                    try {
                        if (m.getUsuarioId() != null) {
                            Usuario u = usuarioService.findUsuarioById(m.getUsuarioId());
                            nombreAutor = u.getNombre();
                        }
                    } catch (ServicioException e) {
                        System.err.println("No se pudo resolver el nombre del autor: " + e.getMessage());
                    }
                }

                String fechaHora = "";
                if (m.getTimestamp() != null) {
                    fechaHora = " [" + m.getTimestamp().format(formatter) + "]";
                }

                sb.append(nombreAutor)
                        .append(fechaHora) // Agregamos la fecha aquí
                        .append(": ")
                        .append(m.getContenido())
                        .append("\n\n");
            }

            frame.mostrarMensajes(sb.toString());

        } catch (ServicioException se) {
            frame.mostrarMensajes("No se pudo cargar el historial: " + se.getMessage());
        } catch (Exception e) {
            frame.mostrarMensajes("Error inesperado al cargar mensajes.");
            e.printStackTrace();
        }
    }

    @Override
    public void enviarMensaje(String contenido) {
        if (chatSeleccionado == null) {
            JOptionPane.showMessageDialog(frame, "Selecciona un chat primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (contenido == null || contenido.trim().isEmpty()) {
            return;
        }

        try {
            Mensaje nuevo = new Mensaje();
            nuevo.setChatId(chatSeleccionado.getId());
            nuevo.setUsuarioId(usuarioActual.getId());
            nuevo.setContenido(contenido);
            nuevo.setTimestamp(LocalDateTime.now());

            mensajeService.crearMensaje(nuevo);

            frame.limpiarInputMensaje();
            cargarMensajesDelChatActual();

        } catch (ServicioException se) {
            JOptionPane.showMessageDialog(frame, se.getMessage(), "No se pudo enviar", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error técnico al enviar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void clicBotonCrearChat() {
        try {
            List<Usuario> usuarios = usuarioService.findAllUsuarios();

            if (usuarios != null) {
                usuarios.removeIf(u -> u.getId() != null && u.getId().equals(usuarioActual.getId()));
            }

            if (usuarios == null || usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No hay otros usuarios registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            frame.mostrarDialogoCrearChat(usuarios);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    @Override
    public void crearChatConfirmado(String nombre, Usuario u1, Usuario u2) {
        try {
            Chat chatNuevo = new Chat();

            if (nombre != null && !nombre.trim().isEmpty()) {
                chatNuevo.setNombre(nombre);
            }

            String idRemitente = (u1 != null) ? u1.getId() : usuarioActual.getId();

            chatNuevo.setUsuario1id(idRemitente);
            chatNuevo.setUsuario2id(u2.getId());

            chatService.crearChat(chatNuevo);

            cargarMisChats();
            JOptionPane.showMessageDialog(frame, "Chat creado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al crear chat: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void verPerfil() {
        if (usuarioActual != null) {
            new InformacionContactoFrame(usuarioActual).setVisible(true);
        }
    }

    @Override
    public void cerrarSesion() {
        frame.dispose();
        new IniciarSesionFrame().setVisible(true);
    }

    @Override
    public String obtenerNombreChat(Chat chat) {
        if (chat.getNombre() != null && !chat.getNombre().trim().isEmpty()) {
            return chat.getNombre();
        }

        String otroId = chat.getUsuario1id().equals(usuarioActual.getId())
                ? chat.getUsuario2id()
                : chat.getUsuario1id();

        try {
            Usuario otro = usuarioService.findUsuarioById(otroId);
            return (otro != null) ? otro.getNombre() : "Usuario Desconocido";

        } catch (ServicioException se) {
            return "Chat (Usuario no disponible)";
        } catch (Exception e) {
            return "Error al cargar nombre";
        }
    }
}
