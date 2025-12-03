package com.itson.juatsapp.ui.frame;

import com.github.lgooddatepicker.components.DatePicker;
import com.itson.juatsapp.domain.Direccion;
import com.itson.juatsapp.domain.Sexo;
import com.itson.juatsapp.domain.Usuario;
import com.itson.juatsapp.ui.controller.InformacionContactoController;
import com.itson.juatsapp.ui.controller.impl.InformacionContactoControllerImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class InformacionContactoFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final InformacionContactoController controlador;

    private Usuario usuarioActual;

    private JLabel lblNombreVal;
    private JLabel lblTelefonoVal;
    private JLabel lblSexoVal;
    private JLabel lblFechaNacimientoVal;

    public InformacionContactoFrame(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        this.controlador = new InformacionContactoControllerImpl(this);

        cargarFuentesPoppins();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Juatsapp - Perfil de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        JPanel main = crearPanelFondoDegradado();
        add(main);

        JPanel header = crearHeaderPanel();
        main.add(header, BorderLayout.NORTH);

        JPanel content = crearContentPanel();
        main.add(content, BorderLayout.CENTER);

        JPanel botonesPanel = crearPanelBotones();
        main.add(botonesPanel, BorderLayout.SOUTH);
    }

    private JPanel crearHeaderPanel() {
        JPanel header = crearPanelRedondeadoTransparente();
        header.setPreferredSize(new Dimension(getWidth(), 80));
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblTitulo = new JLabel("Perfil");
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(16f));
        lblTitulo.setForeground(Color.BLACK);

        header.add(lblTitulo, BorderLayout.WEST);
        return header;
    }

    private JPanel crearContentPanel() {
        JPanel content = crearPanelRedondeadoTransparente();
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 15));
        infoPanel.setOpaque(false);

        lblNombreVal = crearLabelValue(usuarioActual.getNombre());
        lblTelefonoVal = crearLabelValue(usuarioActual.getTelefono());
        lblSexoVal = crearLabelValue(String.valueOf(usuarioActual.getSexo()));
        lblFechaNacimientoVal = crearLabelValue(String.valueOf(usuarioActual.getFechaNacimiento()));

        agregarFilaInfo(infoPanel, "Nombre:", lblNombreVal);
        agregarFilaInfo(infoPanel, "Teléfono:", lblTelefonoVal);
        agregarFilaInfo(infoPanel, "Sexo:", lblSexoVal);
        agregarFilaInfo(infoPanel, "Fecha de nacimiento:", lblFechaNacimientoVal);

        JScrollPane scroll = new JScrollPane(infoPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        content.add(scroll, BorderLayout.CENTER);
        return content;
    }

    private void agregarFilaInfo(JPanel panel, String titulo, JLabel valor) {
        panel.add(crearLabelKey(titulo));
        panel.add(valor);
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setOpaque(false);

        JButton btnEditar = crearBotonEstilizado("Editar");
        btnEditar.addActionListener(e -> abrirDialogoEdicion());

        panel.add(btnEditar);

        return panel;
    }

    private void abrirDialogoEdicion() {
        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 10));

        JTextField txtNombre = new JTextField(usuarioActual.getNombre());
        JTextField txtTelefono = new JTextField(usuarioActual.getTelefono());
        JPasswordField txtContrasena = new JPasswordField(usuarioActual.getContrasena());

        DatePicker dpFecha = new DatePicker();
        dpFecha.setDate(usuarioActual.getFechaNacimiento());
        dpFecha.getComponentDateTextField().setFont(fuentePoppinsRegular.deriveFont(13f));

        JComboBox<Sexo> cbSexo = new JComboBox<>(Sexo.values());
        cbSexo.setSelectedItem(usuarioActual.getSexo());
        cbSexo.setFont(fuentePoppinsRegular.deriveFont(13f));

        JTextField txtCalle = new JTextField();
        JTextField txtColonia = new JTextField();
        JTextField txtCiudad = new JTextField();

        if (usuarioActual.getDireccion() != null) {
            Direccion d = usuarioActual.getDireccion();
            txtCalle.setText(d.getCalle());
            txtColonia.setText(d.getColonia());
            txtCiudad.setText(d.getCiudad());
        }

        aplicarFuenteInputs(txtNombre, txtTelefono, txtContrasena, txtCalle, txtColonia, txtCiudad);

        agregarCampoEdicion(formulario, "Nombre", txtNombre);
        agregarCampoEdicion(formulario, "Teléfono", txtTelefono);
        agregarCampoEdicion(formulario, "Contraseña", txtContrasena);
        agregarCampoEdicion(formulario, "Sexo", cbSexo);
        agregarCampoEdicion(formulario, "Fecha Nacimiento", dpFecha);
        agregarCampoEdicion(formulario, "Calle", txtCalle);
        agregarCampoEdicion(formulario, "Colonia", txtColonia);
        agregarCampoEdicion(formulario, "Ciudad", txtCiudad);

        int opt = JOptionPane.showConfirmDialog(
                this,
                formulario,
                "Editar Perfil",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            procesarEdicion(
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    String.valueOf(txtContrasena.getPassword()),
                    dpFecha.getDate(),
                    (Sexo) cbSexo.getSelectedItem(),
                    txtCalle.getText(),
                    txtColonia.getText(),
                    txtCiudad.getText()
            );
        }
    }

    private void agregarCampoEdicion(JPanel panel, String label, java.awt.Component comp) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(fuentePoppinsBold.deriveFont(13f));
        panel.add(lbl);
        panel.add(comp);
    }

    private void aplicarFuenteInputs(JTextField... fields) {
        for (JTextField f : fields) {
            f.setFont(fuentePoppinsRegular.deriveFont(13f));
        }
    }

    private void procesarEdicion(
            String nombre,
            String tel,
            String pass,
            LocalDate fecha,
            Sexo sexo,
            String calle,
            String col,
            String ciudad
    ) {
        Usuario datosNuevos = new Usuario();
        datosNuevos.setNombre(nombre);
        datosNuevos.setTelefono(tel);
        datosNuevos.setContrasena(pass);
        datosNuevos.setFechaNacimiento(fecha);
        datosNuevos.setSexo(sexo);

        Direccion d = new Direccion();
        d.setCalle(calle);
        d.setColonia(col);
        d.setCiudad(ciudad);

        datosNuevos.setDireccion(d);

        controlador.guardarCambios(usuarioActual, datosNuevos);
    }

    public void actualizarDatosVista(Usuario usuarioActualizado) {
        this.usuarioActual = usuarioActualizado;

        lblNombreVal.setText(usuarioActual.getNombre());
        lblTelefonoVal.setText(usuarioActual.getTelefono());
        lblSexoVal.setText(String.valueOf(usuarioActual.getSexo()));
        lblFechaNacimientoVal.setText(String.valueOf(usuarioActual.getFechaNacimiento()));
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
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int r = 20;

                g2d.setColor(new Color(255, 255, 255, 180));
                g2d.fillRoundRect(0, 0, w, h, r, r);

                g2d.setColor(new Color(120, 120, 120));
                g2d.drawRoundRect(0, 0, w - 1, h - 1, r, r);

                g2d.dispose();
            }
        };

        panel.setOpaque(false);
        return panel;
    }

    private JLabel crearLabelKey(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuentePoppinsBold.deriveFont(14f));
        lbl.setForeground(new Color(80, 80, 80));

        return lbl;
    }

    private JLabel crearLabelValue(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuentePoppinsRegular.deriveFont(14f));
        lbl.setForeground(Color.BLACK);

        return lbl;
    }

    private JButton crearBotonEstilizado(String texto) {
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

                Color c1 = new Color(37, 211, 102);
                Color c2 = new Color(51, 255, 153);

                g2d.setPaint(new GradientPaint(0, 0, c1, 0, h, c2));
                g2d.fillRoundRect(0, 0, w - 6, h - 6, r, r);

                g2d.setColor(new Color(120, 120, 120));
                g2d.drawRoundRect(0, 0, w - 6, h - 6, r, r);

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(fuentePoppinsRegular.deriveFont(14f));
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
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
            fuentePoppinsBold = new JLabel().getFont().deriveFont(Font.BOLD);
        }
    }
}
