package com.itson.juatsapp.ui.frame;

import com.itson.juatsapp.domain.Chat;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.ui.controller.ConversacionController;
import com.itson.juatsapp.ui.controller.impl.ConversacionControllerImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;

public class ConversacionFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final ConversacionController controller;
    private final Usuario usuarioActual;

    private DefaultListModel<Chat> listaChatsModel;

    private JList<Chat> listaChats;
    private JTextArea txtAreaMensajes;
    private JTextField txtInputMensaje;

    public ConversacionFrame(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        this.controller = new ConversacionControllerImpl(this, usuarioActual);

        configurarVentana();
        cargarFuentesPoppins();
        inicializarComponentes();

        controller.cargarMisChats();
    }

    private void configurarVentana() {
        setTitle("Juatsapp - Chat (" + usuarioActual.getNombre() + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        JPanel main = crearPanelFondoDegradado();
        add(main);

        JPanel header = crearHeader();
        main.add(header, BorderLayout.NORTH);

        JPanel sidebar = crearSidebar();
        main.add(sidebar, BorderLayout.WEST);

        JPanel mensajesPanel = crearAreaMensajes();
        main.add(mensajesPanel, BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = crearPanelRedondeadoTransparente();
        header.setPreferredSize(new Dimension(getWidth(), 80));
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblUsuario = new JLabel(usuarioActual.getNombre());
        lblUsuario.setFont(fuentePoppinsRegular.deriveFont(14f));
        lblUsuario.setForeground(Color.BLACK);

        JButton btnPerfil = crearBotonEstilizado(
                "Mirar Perfil",
                new Color(37, 211, 102),
                new Color(51, 255, 153)
        );
        btnPerfil.addActionListener(e -> controller.verPerfil());

        JButton btnCerrarSesion = crearBotonEstilizado(
                "Cerrar Sesión",
                new Color(255, 80, 80),
                new Color(255, 120, 120)
        );
        btnCerrarSesion.addActionListener(e -> controller.cerrarSesion());

        JPanel panelBotones = new JPanel(new java.awt.GridBagLayout());
        panelBotones.setOpaque(false);

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(0, 5, 0, 5);

        panelBotones.add(btnPerfil, gbc);
        panelBotones.add(btnCerrarSesion, gbc);

        header.add(lblUsuario, BorderLayout.WEST);
        header.add(panelBotones, BorderLayout.EAST);

        return header;
    }

    private JPanel crearSidebar() {
        JPanel sidebar = crearPanelRedondeadoTransparente(new Color(245, 245, 245, 180));
        sidebar.setPreferredSize(new Dimension(260, getHeight()));

        listaChatsModel = new DefaultListModel<>();
        listaChats = new JList<>(listaChatsModel);

        listaChats.setOpaque(false);
        listaChats.setBackground(new Color(0, 0, 0, 0));

        listaChats.setCellRenderer(
                (list, chat, index, isSelected, cellHasFocus)
                -> crearTarjetaChat(chat, isSelected)
        );

        listaChats.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                controller.seleccionarChat(listaChats.getSelectedValue());
            }
        });

        JScrollPane scrollChats = new JScrollPane(listaChats);
        scrollChats.setOpaque(false);
        scrollChats.getViewport().setOpaque(false);
        scrollChats.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        sidebar.add(scrollChats, BorderLayout.CENTER);

        JButton btnCrearChat = crearBotonEstilizado(
                "Crear Chat",
                new Color(37, 211, 102),
                new Color(51, 255, 153)
        );
        btnCrearChat.addActionListener(e -> controller.clicBotonCrearChat());

        JPanel panelBtn = new JPanel(new BorderLayout());
        panelBtn.setOpaque(false);
        panelBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelBtn.add(btnCrearChat, BorderLayout.CENTER);

        sidebar.add(panelBtn, BorderLayout.SOUTH);

        return sidebar;
    }

    private JPanel crearAreaMensajes() {
        JPanel panel = crearPanelRedondeadoTransparente(new Color(255, 255, 255, 200));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        txtAreaMensajes = new JTextArea();
        txtAreaMensajes.setOpaque(false);
        txtAreaMensajes.setFont(fuentePoppinsRegular.deriveFont(14f));
        txtAreaMensajes.setEditable(false);
        txtAreaMensajes.setLineWrap(true);
        txtAreaMensajes.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtAreaMensajes);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(scroll, BorderLayout.CENTER);

        JPanel enviarPanel = new JPanel(new BorderLayout(5, 5));
        enviarPanel.setOpaque(false);
        enviarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        txtInputMensaje = crearInputMensaje();

        JButton btnEnviar = crearBotonEstilizado(
                "Enviar",
                new Color(37, 211, 102),
                new Color(51, 255, 153)
        );
        btnEnviar.addActionListener(e -> controller.enviarMensaje(txtInputMensaje.getText()));

        enviarPanel.add(txtInputMensaje, BorderLayout.CENTER);
        enviarPanel.add(btnEnviar, BorderLayout.EAST);

        panel.add(enviarPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void actualizarListaChats(List<Chat> chats) {
        listaChatsModel.clear();
        chats.forEach(listaChatsModel::addElement);
    }

    public void mostrarMensajes(String texto) {
        txtAreaMensajes.setText(texto);
        txtAreaMensajes.setCaretPosition(txtAreaMensajes.getDocument().getLength());
    }

    public void limpiarInputMensaje() {
        txtInputMensaje.setText("");
        txtInputMensaje.requestFocus();
    }

    public void mostrarDialogoCrearChat(List<Usuario> usuariosDisponibles) {

        JComboBox<Usuario> cbUsuarios
                = new JComboBox<>(usuariosDisponibles.toArray(Usuario[]::new));

        cbUsuarios.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Usuario u) {
                    setText(u.getNombre());
                }

                return this;
            }
        });

        cbUsuarios.setFont(fuentePoppinsRegular.deriveFont(12f));

        JTextField txtNombre = new JTextField();
        txtNombre.setFont(fuentePoppinsRegular.deriveFont(12f));

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Nombre del Chat (Opcional):"));
        panel.add(txtNombre);
        panel.add(new JLabel("Seleccionar Contacto:"));
        panel.add(cbUsuarios);

        int opt = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Nuevo Chat",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opt == JOptionPane.OK_OPTION) {
            Usuario otro = (Usuario) cbUsuarios.getSelectedItem();

            if (otro != null) {
                controller.crearChatConfirmado(txtNombre.getText(), null, otro);
            }
        }
    }

    private JPanel crearPanelFondoDegradado() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                Color c1 = new Color(37, 211, 102);
                Color c2 = new Color(102, 255, 153);
                Color c3 = new Color(173, 255, 207);

                g2d.setPaint(new GradientPaint(0, 0, c1, 0, h / 2, c2));
                g2d.fillRect(0, 0, w, h / 2);

                g2d.setPaint(new GradientPaint(0, h / 2, c2, 0, h, c3));
                g2d.fillRect(0, h / 2, w, h / 2);

                g2d.dispose();
            }
        };

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JPanel crearPanelRedondeadoTransparente() {
        return crearPanelRedondeadoTransparente(new Color(255, 255, 255, 180));
    }

    private JPanel crearPanelRedondeadoTransparente(Color backgroundColor) {
        JPanel panel = new JPanel(new BorderLayout()) {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int radio = 20;

                g2d.setColor(backgroundColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);

                g2d.setColor(new Color(120, 120, 120));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);

                g2d.dispose();
            }
        };

        panel.setOpaque(false);
        return panel;
    }

    private JPanel crearTarjetaChat(Chat chat, boolean isSelected) {
        String nombreChat = controller.obtenerNombreChat(chat);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(new EmptyBorder(5, 0, 5, 0));

        JPanel card = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int r = 14;

                Color base = isSelected
                        ? new Color(200, 255, 220, 230)
                        : new Color(240, 255, 240, 180);

                g2d.setPaint(new GradientPaint(0, 0, base, 0, getHeight(), new Color(230, 250, 230, 150)));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), r, r);

                g2d.setColor(new Color(120, 120, 120, isSelected ? 180 : 120));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, r, r);

                g2d.dispose();
            }
        };

        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel lbl = new JLabel(nombreChat);
        lbl.setFont(fuentePoppinsRegular.deriveFont(14f));

        card.add(lbl, BorderLayout.CENTER);
        contenedor.add(card, BorderLayout.CENTER);

        return contenedor;
    }

    private JTextField crearInputMensaje() {
        JTextField input = new JTextField() {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int radio = 20;

                g2d.setColor(new Color(245, 245, 245, 220));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);

                g2d.setColor(new Color(120, 120, 120));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);

                super.paintComponent(g2d);
                g2d.dispose();
            }
        };

        input.setOpaque(false);
        input.setFont(fuentePoppinsRegular.deriveFont(13f));
        input.setBorder(new EmptyBorder(10, 15, 10, 15));

        return input;
    }

    private JButton crearBotonEstilizado(String texto, Color start, Color end) {
        JButton btn = new JButton(texto) {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int r = 30;

                g2d.setColor(new Color(0, 0, 0, 40));
                g2d.fillRoundRect(3, 3, w - 6, h - 6, r, r);

                g2d.setPaint(new GradientPaint(0, 0, start, 0, h, end));
                g2d.fillRoundRect(0, 0, w - 6, h - 6, r, r);

                g2d.setColor(new Color(120, 120, 120));
                g2d.drawRoundRect(0, 0, w - 6, h - 6, r, r);

                g2d.dispose();

                super.paintComponent(g);
            }
        };

        btn.setFont(fuentePoppinsRegular.deriveFont(14f));
        btn.setForeground(Color.BLACK);

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);

        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setLocation(btn.getX(), btn.getY() - 2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setLocation(btn.getX(), btn.getY() + 2);
            }
        });

        return btn;
    }

    private void cargarFuentesPoppins() {
        try (
                InputStream isReg = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf"); InputStream isBold = getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf")) {

            if (isReg != null) {
                fuentePoppinsRegular = Font.createFont(Font.TRUETYPE_FONT, isReg);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuentePoppinsRegular);
            } else {
                fuentePoppinsRegular = new JLabel().getFont();
            }

            if (isBold != null) {
                fuentePoppinsBold = Font.createFont(Font.TRUETYPE_FONT, isBold);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuentePoppinsBold);
            } else {
                fuentePoppinsBold = fuentePoppinsRegular.deriveFont(Font.BOLD);
            }

        } catch (FontFormatException | IOException e) {
            fuentePoppinsRegular = new JLabel().getFont();
            fuentePoppinsBold = fuentePoppinsRegular.deriveFont(Font.BOLD);
        }
    }
}
