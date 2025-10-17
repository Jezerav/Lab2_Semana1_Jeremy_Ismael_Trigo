/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2_Semana1_Jeremy_Ismael_Trigo;

/**
 *
 * @author trigo
 */
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class EmailAccount {

    String direccionEmail;
    String password;
    String nombreUsuario;
    Email[] inbox;

    // Constructor
    public EmailAccount(String direccionEmail, String password, String nombreUsuario) {
        this.direccionEmail = direccionEmail;
        this.password = password;
        this.nombreUsuario = nombreUsuario;
        this.inbox = new Email[10]; // 10 espacios disponibles
    }

    public String getDireccionEmail() {
        return direccionEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    // Guardar correo nuevo
    public boolean recibirEmail(Email em) {
        for (int i = 0; i < inbox.length; i++) {
            if (inbox[i] == null) {
                inbox[i] = em;
                return true;
            }
        }
        return false; // inbox lleno
    }

    // Mostrar la bandeja
    public void printInbox() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar ahora = Calendar.getInstance();

        StringBuilder sb = new StringBuilder();
        sb.append("Fecha actual: ").append(sdf.format(ahora.getTime())).append("\n\n");
        sb.append("POS - EMISOR - ASUNTO - ESTADO\n");

        int sinLeer = contarSinLeer(0);
        int total = contarTotales(0);

        for (int i = 0; i < inbox.length; i++) {
            if (inbox[i] != null) {
                sb.append((i + 1)).append(" - ")
                  .append(inbox[i].getEmisor()).append(" - ")
                  .append(inbox[i].getAsunto()).append(" - ")
                  .append(inbox[i].isLeido() ? "LEÍDO" : "SIN LEER")
                  .append("\n");
            }
        }

        sb.append("\nTotal: ").append(total).append(" | Sin leer: ").append(sinLeer);
        JOptionPane.showMessageDialog(null, sb.toString(), "BANDEJA DE ENTRADA", JOptionPane.INFORMATION_MESSAGE);
    }

    // Leer correo por posición
    public void leerEmail(int pos) {
        if (pos < 0 || pos >= inbox.length || inbox[pos] == null) {
            JOptionPane.showMessageDialog(null, "Correo no existe.");
        } else {
            inbox[pos].mostrar();  // Muestra los datos del correo
            inbox[pos].leido = true; // Marca como leído
        }
    }
    

    private int contarSinLeer(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private int contarTotales(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

   