package com.itson.juatsapp.ui.frame;

import com.github.lgooddatepicker.components.DatePicker;
import com.itson.juatsapp.domain.Direccion;
import com.itson.juatsapp.domain.Sexo;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.ui.controller.RegistroController;
import com.itson.juatsapp.ui.controller.impl.RegistroControllerImpl;
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
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class RegistroFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final RegistroController controller;

    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JPasswordField txtContrasena;
    private JComboBox<Sexo> cbSexo;
    private DatePicker dpFecha;

    private JTextField txtCalle;
    private JTextField txtColonia;
    private JTextField txtCiudad;

    public RegistroFrame() {
        this.controller = new RegistroControllerImpl(this);

        cargarFuentesPoppins();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Juatsapp - Crear Cuenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1024, 720));
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        JPanel main = crearPanelFondoDegradado();
        add(main);

        JScrollPane scrollPane = crearPanelFormulario();
        main.add(scrollPane, BorderLayout.CENTER);

        JPanel botonesPanel = crearPanelBotones();
        main.add(botonesPanel, BorderLayout.SOUTH);
    }

    private JScrollPane crearPanelFormulario() {
        JPanel formulario = new JPanel(new GridLayout(0, 2, 20, 10));
        formulario.setOpaque(false);
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNombre = crearTextField();
        txtTelefono = crearTextField();
        txtContrasena = crearPasswordField();

        cbSexo = new JComboBox<>(Sexo.values());
        configurarCombo(cbSexo);

        dpFecha = new DatePicker();
        dpFecha.setDate(LocalDate.now().minusYears(18));
        dpFecha.getComponentDateTextField().setFont(fuentePoppinsRegular.deriveFont(13f));
        dpFecha.setBackground(new Color(0, 0, 0, 0));

        txtCalle = crearTextField();
        txtColonia = crearTextField();
        txtCiudad = crearTextField();

        formulario.add(crearLabel("Nombre"));
        formulario.add(txtNombre);

        formulario.add(crearLabel("Teléfono"));
        formulario.add(txtTelefono);

        formulario.add(crearLabel("Contraseña"));
        formulario.add(txtContrasena);

        formulario.add(crearLabel("Sexo"));
        formulario.add(cbSexo);

        formulario.add(crearLabel("Fecha Nacimiento"));
        formulario.add(dpFecha);

        formulario.add(crearLabel("Calle"));
        formulario.add(txtCalle);

        formulario.add(crearLabel("Colonia"));
        formulario.add(txtColonia);

        formulario.add(crearLabel("Ciudad"));
        formulario.add(txtCiudad);

        JScrollPane scrollPane = new JScrollPane(formulario);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        return scrollPane;
    }

    private JPanel crearPanelBotones() {
        JPanel botonesPanel = new JPanel(new GridLayout(1, 2, 20, 5));
        botonesPanel.setOpaque(false);
        botonesPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton btnRegistrar = crearBotonPrincipal("Crear Cuenta");
        btnRegistrar.addActionListener(e -> accionRegistrar());

        JButton btnAtras = crearBotonSecundario("Atrás");
        btnAtras.addActionListener(e -> controller.cancelar());

        botonesPanel.add(btnRegistrar);
        botonesPanel.add(btnAtras);

        return botonesPanel;
    }

    private void accionRegistrar() {
        Usuario u = new Usuario();
        u.setNombre(txtNombre.getText().trim());
        u.setTelefono(txtTelefono.getText().trim());
        u.setContrasena(String.valueOf(txtContrasena.getPassword()));
        u.setSexo((Sexo) cbSexo.getSelectedItem());
        u.setFechaNacimiento(dpFecha.getDate());

        Direccion d = new Direccion();
        d.setCalle(txtCalle.getText().trim());
        d.setColonia(txtColonia.getText().trim());
        d.setCiudad(txtCiudad.getText().trim());

        u.setDireccion(d);

        controller.registrarUsuario(u);
    }

    private JPanel crearPanelFondoDegradado() {
        return new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                Color c1 = new Color(37, 211, 102);
                Color c2 = new Color(102, 255, 153);
                Color c3 = new Color(173, 255, 207);

                g2d.setPaint(new GradientPaint(0, 0, c1, 0, height / 2, c2));
                g2d.fillRect(0, 0, width, height / 2);

                g2d.setPaint(new GradientPaint(0, height / 2, c2, 0, height, c3));
                g2d.fillRect(0, height / 2, width, height / 2);

                g2d.dispose();
            }
        };
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

    private void configurarCombo(JComboBox<?> combo) {
        combo.setFont(fuentePoppinsRegular.deriveFont(13f));
        combo.setOpaque(false);
        combo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void configurarEstiloInput(JTextField field) {
        field.setFont(fuentePoppinsRegular.deriveFont(13f));
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
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
        configurarBoton(btn, Color.BLACK);
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
        configurarBoton(btn, Color.WHITE);
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

    private void configurarBoton(JButton btn, Color foreground) {
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
        try (InputStream isRegular = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf"); InputStream isBold = getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf")) {

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
