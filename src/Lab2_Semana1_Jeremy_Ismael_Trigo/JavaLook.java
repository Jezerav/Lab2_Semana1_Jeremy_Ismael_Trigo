/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2_Semana1_Jeremy_Ismael_Trigo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class JavaLook extends JFrame {
    
    private static EmailAccount[] cuentas = new EmailAccount[10];
    private static EmailAccount cuentaActual = null;
    private static int numCuentas = 0; 

    private JLabel etiquetaUsuario;
    private JLabel etiquetaTiempo;

    private JPanel panelMenuInicial;
    private JPanel panelMenuAcciones;
    
    private final Color COLOR_FONDO = new Color(220, 255, 220); 
    private final Color COLOR_BOTON = new Color(50, 150, 50); 
    private final Color COLOR_TEXTO_BOTON = Color.WHITE;
    
    private final int ANCHO_VENTANA = 550;
    private final int ALTO_VENTANA = 450;
    

    public JavaLook() {
        setTitle("Sistema de Correo Electronico");
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO); 
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 
        
        cargarDatosPrueba(); 

        etiquetaUsuario = new JLabel("SESION NO INICIADA");
        etiquetaUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetaUsuario.setForeground(Color.RED);
        
        etiquetaTiempo = new JLabel();
        etiquetaTiempo.setHorizontalAlignment(SwingConstants.CENTER);
        
        etiquetaUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiquetaTiempo.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(20));
        add(etiquetaUsuario);
        add(etiquetaTiempo);
        add(Box.createVerticalStrut(20)); 
        
        confMenuInicial(); 
        confMenuAcciones();
        
        add(panelMenuInicial); 
        add(panelMenuAcciones);
        
        actVista();
        
        new Timer(1000, e -> actTiempo()).start();

        setVisible(true);
    }
    
    private void confMenuInicial() {
        panelMenuInicial = new JPanel();
        panelMenuInicial.setBackground(COLOR_FONDO);
        panelMenuInicial.setLayout(new GridLayout(4, 1, 15, 15)); 
        panelMenuInicial.setBorder(BorderFactory.createEmptyBorder(40, 120, 40, 120));
        
        JLabel titulo = new JLabel("MENU INICIAL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        
        JButton botonLogin = new JButton("INICIAR SESION");
        JButton botonCrearCuenta = new JButton("CREAR CUENTA");
        JButton botonSalir = new JButton("SALIR");

        darEstiloBoton(botonLogin);
        darEstiloBoton(botonCrearCuenta);
        darEstiloBoton(botonSalir);

        botonLogin.addActionListener(e -> iniciarSesion());
        botonCrearCuenta.addActionListener(e -> crearCuentaNueva());
        botonSalir.addActionListener(e -> System.exit(0)); 

        panelMenuInicial.add(titulo);
        panelMenuInicial.add(botonLogin);
        panelMenuInicial.add(botonCrearCuenta);
        panelMenuInicial.add(botonSalir);
    }
    
    private void confMenuAcciones() {
        panelMenuAcciones = new JPanel();
        panelMenuAcciones.setBackground(COLOR_FONDO);
        panelMenuAcciones.setLayout(new GridLayout(4, 2, 10, 10)); 
        panelMenuAcciones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton botonVerInbox = new JButton("1. VER BANDEJA");
        JButton botonMandarCorreo = new JButton("2. MANDAR CORREO");
        JButton botonLeerCorreo = new JButton("3. LEER CORREO");
        JButton botonLimpiarInbox = new JButton("4. LIMPIAR (Rec)");
        
        JButton botonBuscarEmisor = new JButton("5. BUSCAR EMISOR (Rec)");
        JButton botonMostrarTotales = new JButton("6. MOSTRAR TOTALES (Rec)");
        JButton botonCerrarSesion = new JButton("7. CERRAR SESION");
        
        darEstiloBoton(botonVerInbox);
        darEstiloBoton(botonMandarCorreo);
        darEstiloBoton(botonLeerCorreo);
        darEstiloBoton(botonLimpiarInbox);
        darEstiloBoton(botonBuscarEmisor);
        darEstiloBoton(botonMostrarTotales);
        darEstiloBoton(botonCerrarSesion);
        
        botonVerInbox.addActionListener(e -> { if (cuentaActual != null) cuentaActual.printInbox(); });
        botonMandarCorreo.addActionListener(e -> enviarCorreo());
        botonLeerCorreo.addActionListener(e -> leerCorreo());
        botonLimpiarInbox.addActionListener(e -> limpiarCorreosLeidos());
        
        botonBuscarEmisor.addActionListener(e -> buscarPorEmisor());
        
        botonMostrarTotales.addActionListener(e -> mostrarTotales());
        botonCerrarSesion.addActionListener(e -> cerrarSesion());
        
        panelMenuAcciones.add(botonVerInbox);
        panelMenuAcciones.add(botonMandarCorreo);
        panelMenuAcciones.add(botonLeerCorreo);
        panelMenuAcciones.add(botonLimpiarInbox);
        panelMenuAcciones.add(botonBuscarEmisor);
        panelMenuAcciones.add(botonMostrarTotales);
        panelMenuAcciones.add(new JLabel()); 
        panelMenuAcciones.add(botonCerrarSesion);
    }
    
    private void darEstiloBoton(JButton boton) {
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_TEXTO_BOTON);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void actVista() {
        if (cuentaActual == null) {
            etiquetaUsuario.setText("<html><center><font color='red'>SESION NO INICIADA</font></center></html>");
            etiquetaUsuario.setForeground(Color.RED);
            panelMenuInicial.setVisible(true);   
            panelMenuAcciones.setVisible(false); 
        } else {
            etiquetaUsuario.setText("<html><center>Bienvenido, <b>" + cuentaActual.getNombreUsuario() + "</b></center></html>");
            etiquetaUsuario.setForeground(Color.BLACK); 
            panelMenuInicial.setVisible(false);  
            panelMenuAcciones.setVisible(true);  
        }
        revalidate();
        repaint();
    }
    
    private void actTiempo() {
        Calendar ahora = Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH : mm : ss - a");
        
        String horaFechaStr = "<html><center>Fecha: " + formatoFecha.format(ahora.getTime()) + 
                              " | Hora: " + formatoHora.format(ahora.getTime()) + "</center></html>";
        etiquetaTiempo.setText(horaFechaStr);
    }
    
    private void iniciarSesion() {
        String email = JOptionPane.showInputDialog(this, "Ingrese su correo:");
        if (email == null) return;
        
        String password = JOptionPane.showInputDialog(this, "Ingrese su contrasena:");
        if (password == null) return;
        
        EmailAccount cuenta = buscarCuenta(email);

        if (cuenta != null && cuenta.getPassword().equals(password)) {
            cuentaActual = cuenta;
            JOptionPane.showMessageDialog(this, "Bienvenido, " + cuentaActual.getNombreUsuario() + "!", "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
            actVista();
        } else {
            JOptionPane.showMessageDialog(this, "Error: Los datos de acceso son incorrectos.", "Error de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearCuentaNueva() {
        if (numCuentas >= cuentas.length) {
            JOptionPane.showMessageDialog(this, "Error: Limite de cuentas alcanzado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = null;
        while (true) {
            email = JOptionPane.showInputDialog(this, "Ingrese su correo (debe ser unico):");
            if (email == null) return; 
            
            if (buscarCuenta(email) != null) {
                JOptionPane.showMessageDialog(this, "Error: Este correo ya esta registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }
        
        String nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre de usuario:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String password = null;
        while (true) {
            password = JOptionPane.showInputDialog(this, 
                "<html>Ingrese contrasena:<br><i>(Min. 5 chars, un simbolo, una mayuscula y un numero)</i></html>");
            if (password == null) return; 
            
            if (validarPassword(password)) {
                break; 
            } else {
                JOptionPane.showMessageDialog(this, "Error: La contrasena no cumple los requisitos. Intente de nuevo.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            }
        }

        EmailAccount nuevaCuenta = new EmailAccount(email, password, nombre);
        cuentas[numCuentas++] = nuevaCuenta;
        
        cuentaActual = nuevaCuenta;
        JOptionPane.showMessageDialog(this, "Cuenta creada con exito. Bienvenido, " + cuentaActual.getNombreUsuario() + "!", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        actVista();
    }
    
    private void enviarCorreo() {
        if (cuentaActual == null) return;
        String destinatario = JOptionPane.showInputDialog(this, "Destinatario:");
        String asunto = JOptionPane.showInputDialog(this, "Asunto:");
        String contenido = JOptionPane.showInputDialog(this, "Contenido:");
        if (destinatario == null || asunto == null || contenido == null) return;

        EmailAccount cuentaDestino = buscarCuenta(destinatario);
        
        if (cuentaDestino == null) {
            JOptionPane.showMessageDialog(this, "Destinatario inexistente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Email nuevoEmail = new Email(cuentaActual.getDireccionEmail(), asunto, contenido);
        
        if (cuentaDestino.recibirEmail(nuevoEmail)) {
            JOptionPane.showMessageDialog(this, "Envio exitoso.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error: Bandeja llena del destinatario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void leerCorreo() {
        if (cuentaActual == null) return;
        cuentaActual.printInbox(); 
        String posStr = JOptionPane.showInputDialog(this, "Ingrese la POSICION del correo a leer (1-10):");
        if (posStr == null) return;
        
        try {
            int pos = Integer.parseInt(posStr);
            if (pos >= 1 && pos <= cuentaActual.inbox.length) {
                cuentaActual.leerEmail(pos - 1); 
            } else {
                JOptionPane.showMessageDialog(this, "Posicion fuera del rango (1-10).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada invalida. Debe ser un numero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCorreosLeidos() {
        if (cuentaActual == null) return;
        int eliminados = elimLeidosRec(0); 
        JOptionPane.showMessageDialog(this, "Limpieza completada. Se eliminaron " + eliminados + " correos leidos.", "Limpieza Bandeja", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private int elimLeidosRec(int i) {
        if (i >= cuentaActual.inbox.length) {
            return 0;
        }
        
        int contador = elimLeidosRec(i + 1); 
        
        if (cuentaActual.inbox[i] != null && cuentaActual.inbox[i].isLeido()) {
            cuentaActual.inbox[i] = null;
            return contador + 1;
        }
        return contador;
    }

    private void buscarPorEmisor() {
        if (cuentaActual == null) return;
        String emisor = JOptionPane.showInputDialog(this, "Ingrese emisor a buscar:");
        if (emisor == null) return;
        
        String resultado = buscarEmisorRec(emisor, 0, ""); 
        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron correos del emisor " + emisor, "Busqueda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Correos encontrados de " + emisor + ":\n" + resultado, "Busqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private String buscarEmisorRec(String emisor, int i, String acumulador) {
        if (i >= cuentaActual.inbox.length) {
            return acumulador;
        }
        
        if (cuentaActual.inbox[i] != null && cuentaActual.inbox[i].getEmisor().equalsIgnoreCase(emisor)) {
            acumulador += (i + 1) + " - " + cuentaActual.inbox[i].getAsunto() + "\n";
        }
        
        return buscarEmisorRec(emisor, i + 1, acumulador);
    }
    
    private void mostrarTotales() {
        if (cuentaActual == null) return;
        int total = contarTotalesRec(0); 
        int sinLeer = contarSinLeerRec(0); 
        
        String mensaje = "Total de correos: " + total + " | Sin Leer: " + sinLeer;
                         
        JOptionPane.showMessageDialog(this, mensaje, "Conteo Recursivo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private int contarTotalesRec(int i) {
        if (i >= cuentaActual.inbox.length) {
            return 0;
        }
        
        int contador = (cuentaActual.inbox[i] != null) ? 1 : 0;
        return contador + contarTotalesRec(i + 1);
    }

    private int contarSinLeerRec(int i) {
        if (i >= cuentaActual.inbox.length) {
            return 0;
        }
        
        int contador = (cuentaActual.inbox[i] != null && !cuentaActual.inbox[i].isLeido()) ? 1 : 0;
        return contador + contarSinLeerRec(i + 1);
    }
    
    private void cerrarSesion() {
        cuentaActual = null;
        JOptionPane.showMessageDialog(this, "Sesion cerrada.", "Cerrar Sesion", JOptionPane.INFORMATION_MESSAGE);
        actVista();
    }
    
    private EmailAccount buscarCuenta(String direccion) {
        for (int i = 0; i < numCuentas; i++) {
            if (cuentas[i] != null && cuentas[i].getDireccionEmail().equalsIgnoreCase(direccion)) {
                return cuentas[i];
            }
        }
        return null;
    }

    private boolean validarPassword(String password) {
        if (password == null || password.length() < 5) return false;
        
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{5,}$";
        return Pattern.matches(regex, password);
    }

    private void cargarDatosPrueba() {
        EmailAccount c1 = new EmailAccount("test@mail.com", "Pass$1", "Admin Demo");
        cuentas[numCuentas++] = c1;

        Email emailPrueba = new Email("bienvenida@sistema.com", "Bienvenida JavaLook", "Hola! Este es tu nuevo sistema.");
        Email emailLeido = new Email("reporte@company.com", "Reporte final", "Reporte listo.");
        emailLeido.marcarComoLeido(); 
        
        c1.recibirEmail(emailPrueba);
        c1.recibirEmail(emailLeido);
        
        EmailAccount c2 = new EmailAccount("user2@mail.com", "User$2", "Usuario Dos");
        cuentas[numCuentas++] = c2;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JavaLook());
    }
}