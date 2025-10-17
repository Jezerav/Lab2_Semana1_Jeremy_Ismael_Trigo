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
    
    // --- ATRIBUTOS (Necesarios para Login/Crear Cuenta) ---
    private static EmailAccount[] cuentas = new EmailAccount[100];
    private static EmailAccount accountActual = null; // null si no hay sesion
    private static int numCuentas = 0; 

    // Elementos de la GUI
    private JLabel userLabel;
    private JLabel timeLabel;

    // --- PANEL CENTRAL CON BOTONES ---
    private JPanel menuInicialPanel;
    
    private final int FRAME_WIDTH = 550;
    private final int FRAME_HEIGHT_DEFAULT = 350;

    public JavaLook() {
        setTitle("JavaLook - Sistema de Correo Electronico");
        setSize(FRAME_WIDTH, FRAME_HEIGHT_DEFAULT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 
        
        inicializarDatosPrueba(); 

        // 1. Etiquetas de Estado
        userLabel = new JLabel("SESION NO INICIADA");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(10)); 
        add(userLabel);
        add(timeLabel);
        add(Box.createVerticalStrut(10)); 
        
        // 2. Panel de Botones del Menu Inicial
        setupMenuInicialPanel(); 
        add(menuInicialPanel); 
        
        updateView(); // Controla la visibilidad
        
        // 3. Timer para la hora/fecha
        new Timer(1000, e -> updateTimeDisplay()).start();

        setVisible(true);
    }
    
    // ----------------------------------------------------------------------
    // --- CONFIGURACION DE GUI ---
    // ----------------------------------------------------------------------
    
    private void setupMenuInicialPanel() {
        menuInicialPanel = new JPanel();
        // Usamos GridLayout para apilar los elementos
        menuInicialPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 filas para Titulo, Login, Crear Cuenta y SALIR
        menuInicialPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));
        
        // Titulo
        JLabel titulo = new JLabel("MENU INICIAL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        
        JButton loginButton = new JButton("LOGIN");
        JButton crearCuentaButton = new JButton("CREAR CUENTA");
        JButton salirButton = new JButton("SALIR"); // Boton SALIR agregado al panel

        loginButton.addActionListener(e -> login());
        crearCuentaButton.addActionListener(e -> crearCuenta());
        salirButton.addActionListener(e -> System.exit(0)); // Accion de salir
        
        menuInicialPanel.add(titulo);
        menuInicialPanel.add(loginButton);
        menuInicialPanel.add(crearCuentaButton);
        menuInicialPanel.add(salirButton);
    }

    private void updateView() {
        if (accountActual == null) {
            userLabel.setText("<html><center><font color='red'>SESION NO INICIADA</font></center></html>");
            menuInicialPanel.setVisible(true);
        } else {
            // Cuando la sesion inicia, ocultamos el panel inicial.
            // Aqui seria donde mostrarias el Panel de ACCIONES
            userLabel.setText("<html><center>Bienvenido, <b>" + accountActual.getNombreUsuario() + "</b></center></html>");
            menuInicialPanel.setVisible(false);
        }
        revalidate();
        repaint();
    }
    
    private void updateTimeDisplay() {
        Calendar ahora = Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH : mm : ss - a");
        
        String horaFechaStr = "<html><center>Fecha: " + formatoFecha.format(ahora.getTime()) + 
                              " | Hora: " + formatoHora.format(ahora.getTime()) + "</center></html>";
        timeLabel.setText(horaFechaStr);
    }
    

    private void login() {
        String email = JOptionPane.showInputDialog(this, "Ingrese su correo:");
        if (email == null) return;
        
        String password = JOptionPane.showInputDialog(this, "Ingrese su contraseña:");
        if (password == null) return;
        
        EmailAccount cuenta = buscarCuenta(email);

        if (cuenta != null && cuenta.getPassword().equals(password)) {
            accountActual = cuenta;
            JOptionPane.showMessageDialog(this, "¡Bienvenido, " + accountActual.getNombreUsuario() + "!", "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
            updateView();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas o la cuenta no existe.", "Error de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearCuenta() {
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
                "<html>Ingrese contraseña:<br><i>(Min. 5 chars, un simbolo, una mayuscula y un numero)</i></html>");
            if (password == null) return; 
            
            if (validarPassword(password)) {
                break; 
            } else {
                JOptionPane.showMessageDialog(this, "Error: La contraseña no cumple los requisitos:\n" + 
                                                   "• Minimo 5 caracteres\n" +
                                                   "• Al menos un simbolo\n" +
                                                   "• Al menos una letra mayuscula\n" +
                                                   "• Al menos un numero", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
            }
        }

        EmailAccount nuevaCuenta = new EmailAccount(email, password, nombre);
        cuentas[numCuentas++] = nuevaCuenta;
        
        accountActual = nuevaCuenta;
        JOptionPane.showMessageDialog(this, "Cuenta creada con exito. ¡Bienvenido, " + accountActual.getNombreUsuario() + "!", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        updateView();
    }
    
    // ----------------------------------------------------------------------
    // --- UTILERIAS ---
    // ----------------------------------------------------------------------

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

    private void inicializarDatosPrueba() {
        // Cuenta de prueba para Login
        EmailAccount c1 = new EmailAccount("test@mail.com", "Pass$1", "Admin Demo");
        cuentas[numCuentas++] = c1;

        // Esto solo es para que compile, no afecta la funcionalidad del Menu Inicial
        Email emailPrueba = new Email("bienvenida@sistema.com", "Bienvenida", "Hola!", false);
        c1.recibirEmail(emailPrueba);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JavaLook());
    }
}