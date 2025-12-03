package com.itson.juatsapp.ui.frame;

import com.itson.juatsapp.ui.controller.IniciarSesionController;
import com.itson.juatsapp.ui.controller.impl.IniciarSesionControllerImpl;
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
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class IniciarSesionFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final IniciarSesionController controller;

    private JTextField txtTelefono;
    private JPasswordField txtContrasena;

    public IniciarSesionFrame() {
        this.controller = new IniciarSesionControllerImpl(this);

        cargarFuentesPoppins();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Juatsapp - Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(480, 720));
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        JPanel main = crearPanelFondoDegradado();
        add(main);

        JPanel formulario = new JPanel();
        formulario.setLayout(new GridLayout(0, 1, 5, 5));
        formulario.setOpaque(false);

        txtTelefono = crearTextField();
        txtContrasena = crearPasswordField();

        JButton btnIniciarSesion = crearBotonPrincipal("Iniciar Sesión");
        btnIniciarSesion.addActionListener(e -> accionIniciarSesion());

        JButton btnRegistrarse = crearBotonSecundario("Registrarse");
        btnRegistrarse.addActionListener(e -> controller.navegarARegistro());

        formulario.add(crearLabel("Teléfono"));
        formulario.add(txtTelefono);

        formulario.add(crearLabel("Contraseña"));
        formulario.add(txtContrasena);

        formulario.add(btnIniciarSesion);
        formulario.add(btnRegistrarse);

        main.add(formulario, BorderLayout.NORTH);
    }

    private void accionIniciarSesion() {
        String tel = txtTelefono.getText();
        String pass = String.valueOf(txtContrasena.getPassword());
        controller.iniciarSesion(tel, pass);
    }

    private JPanel crearPanelFondoDegradado() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                Color colorInicio = new Color(37, 211, 102);
                Color colorMedio = new Color(102, 255, 153);
                Color colorFin = new Color(173, 255, 207);

                GradientPaint gradient = new GradientPaint(0, 0, colorInicio, 0, height / 2, colorMedio);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height / 2);

                gradient = new GradientPaint(0, height / 2, colorMedio, 0, height, colorFin);
                g2d.setPaint(gradient);
                g2d.fillRect(0, height / 2, width, height / 2);

                g2d.dispose();
            }
        };

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        return panel;
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuentePoppinsRegular.deriveFont(13f));
        return lbl;
    }

    private JTextField crearTextField() {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                dibujarInputRedondeado(g, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        configurarEstiloInput(field);
        return field;
    }

    private JPasswordField crearPasswordField() {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                dibujarInputRedondeado(g, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        configurarEstiloInput(field);
        return field;
    }

    private void configurarEstiloInput(JTextField field) {
        field.setFont(fuentePoppinsRegular.deriveFont(13f));
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                field.repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.repaint();
            }
        });
    }

    private void dibujarInputRedondeado(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 20;

        g2d.setColor(new Color(245, 245, 245));
        g2d.fillRoundRect(0, 0, width, height, arc, arc);

        g2d.setColor(new Color(120, 120, 120));
        g2d.drawRoundRect(0, 0, width - 1, height - 1, arc, arc);

        g2d.dispose();
    }

    private JButton crearBotonPrincipal(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                dibujarBoton(g, getWidth(), getHeight(), new Color(37, 211, 102), new Color(51, 255, 153));
                super.paintComponent(g);
            }
        };

        configurarEstiloBoton(btn, Color.BLACK);
        return btn;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                dibujarBoton(g, getWidth(), getHeight(), new Color(100, 100, 100), new Color(150, 150, 150));
                super.paintComponent(g);
            }
        };

        configurarEstiloBoton(btn, Color.WHITE);
        return btn;
    }

    private void dibujarBoton(Graphics g, int width, int height, Color start, Color end) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 30;

        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillRoundRect(3, 3, width - 6, height - 6, arc, arc);

        g2d.setPaint(new GradientPaint(0, 0, start, 0, height, end));
        g2d.fillRoundRect(0, 0, width - 6, height - 6, arc, arc);

        g2d.setColor(new Color(120, 120, 120));
        g2d.drawRoundRect(0, 0, width - 6, height - 6, arc, arc);

        g2d.dispose();
    }

    private void configurarEstiloBoton(JButton btn, Color foreground) {
        btn.setFont(fuentePoppinsRegular.deriveFont(15f));
        btn.setForeground(foreground);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setLocation(btn.getX(), btn.getY() - 2);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setLocation(btn.getX(), btn.getY() + 2);
            }
        });
    }

    private void cargarFuentesPoppins() {
        try (
                InputStream isRegular = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf"); InputStream isBold = getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf")) {
            if (isRegular != null) {
                fuentePoppinsRegular = Font.createFont(Font.TRUETYPE_FONT, isRegular);
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
