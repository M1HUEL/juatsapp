package com.itson.juatsapp.persistence;

import com.itson.juatsapp.domain.Chat;
import com.itson.juatsapp.domain.Direccion;
import com.itson.juatsapp.domain.Mensaje;
import com.itson.juatsapp.domain.Sexo;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.persistence.dao.ChatDAO;
import com.itson.juatsapp.persistence.dao.MensajeDAO;
import com.itson.juatsapp.persistence.dao.UsuarioDAO;
import com.itson.juatsapp.persistence.dao.impl.ChatDAOImpl;
import com.itson.juatsapp.persistence.dao.impl.MensajeDAOImpl;
import com.itson.juatsapp.persistence.dao.impl.UsuarioDAOImpl;
import com.itson.juatsapp.persistence.exception.PersistenciaException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JuatsappPersistence {

    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        ChatDAO chatDAO = new ChatDAOImpl();
        MensajeDAO mensajeDAO = new MensajeDAOImpl();

        try {
            System.out.println("=== INICIANDO CREACIÓN DE DATOS ===");

            Usuario juan = new Usuario();
            juan.setNombre("Juan Pérez");
            juan.setTelefono("555-1010");
            juan.setContrasena("secret123");
            juan.setFechaNacimiento(LocalDate.of(1995, 5, 20));
            juan.setSexo(Sexo.MASCULINO);
            juan.setDireccion(new Direccion("Calle Falsa 123", "Centro", "Obregón"));

            usuarioDAO.crear(juan);
            System.out.println("Usuario creado: " + juan.getNombre() + " (ID: " + juan.getId() + ")");

            Usuario maria = new Usuario();
            maria.setNombre("María López");
            maria.setTelefono("555-2020");
            maria.setContrasena("password456");
            maria.setFechaNacimiento(LocalDate.of(1998, 8, 15));
            maria.setSexo(Sexo.FEMENINO);
            maria.setDireccion(new Direccion("Av. Siempre Viva 742", "Norte", "Obregón"));

            usuarioDAO.crear(maria);
            System.out.println("Usuario creado: " + maria.getNombre() + " (ID: " + maria.getId() + ")");

            Chat chat = new Chat();
            chat.setUsuario1id(juan.getId());
            chat.setUsuario2id(maria.getId());
            chat.setNombre("Chat Juan-María");

            chatDAO.crear(chat);
            System.out.println("Chat creado con ID: " + chat.getId());

            Mensaje m1 = new Mensaje();
            m1.setChatId(chat.getId());
            m1.setUsuarioId(juan.getId());
            m1.setContenido("Hola María, ¿cómo estás?");
            m1.setTimestamp(LocalDateTime.now().minusMinutes(10));

            mensajeDAO.crear(m1);
            System.out.println("Mensaje 1 enviado.");

            Mensaje m2 = new Mensaje();
            m2.setChatId(chat.getId());
            m2.setUsuarioId(maria.getId());
            m2.setContenido("¡Hola Juan! Todo bien, ¿y tú?");
            m2.setTimestamp(LocalDateTime.now().minusMinutes(8));

            mensajeDAO.crear(m2);
            System.out.println("Mensaje 2 enviado.");

            Mensaje m3 = new Mensaje();
            m3.setChatId(chat.getId());
            m3.setUsuarioId(juan.getId());
            m3.setContenido("Bien también, programando en Java Swing.");
            m3.setTimestamp(LocalDateTime.now().minusMinutes(5));

            mensajeDAO.crear(m3);
            System.out.println("Mensaje 3 enviado.");

            System.out.println("=== DATOS CREADOS EXITOSAMENTE ===");

            System.out.println("\n--- Leyendo historial del chat ---");
            var historial = mensajeDAO.findAllByChatId(chat.getId());

            for (Mensaje m : historial) {
                String nombreAutor = m.getUsuarioId().equals(juan.getId()) ? "Juan" : "María";
                System.out.println("[" + m.getTimestamp() + "] " + nombreAutor + ": " + m.getContenido());
            }

        } catch (PersistenciaException e) {
            System.err.println("Ocurrió un error en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
