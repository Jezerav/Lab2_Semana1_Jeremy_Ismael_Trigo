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

    
    public EmailAccount(String direccionEmail, String password, String nombreUsuario) {
        this.direccionEmail = direccionEmail;
        this.password = password;
        this.nombreUsuario = nombreUsuario;
        this.inbox = new Email[10]; 
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

    
    public boolean recibirEmail(Email em) {
        for (int i = 0; i < inbox.length; i++) {
            if (inbox[i] == null) {
                inbox[i] = em;
                return true;
            }
        }
        return false; 
    }

    
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

    
    public void leerEmail(int pos) {
        if (pos < 0 || pos >= inbox.length || inbox[pos] == null) {
            JOptionPane.showMessageDialog(null, "Correo no existe.");
        } else {
            inbox[pos].mostrar();  
            inbox[pos].leido = true; 
        }
    }

    public int contarSinLeerRecursivo(int Cant) {
        if (Cant >= inbox.length) {
            return 0;
        }

        int cuentaActual = 0;

        if (inbox[Cant] != null) {
            if (inbox[Cant].isLeido() == false) {
                cuentaActual = 1;
            }
        }

        int resultadoRestante = contarSinLeerRecursivo(Cant + 1);

        return cuentaActual + resultadoRestante;
    }

    public void buscarPorEmisorRecursivo(String emisorBuscado, int Cant) {
        if (Cant >= inbox.length) {
            JOptionPane.showMessageDialog(null, "Busqueda por emisor finalizada.");
            return;
        }

        if (inbox[Cant] != null) {
            if (inbox[Cant].getEmisor().equalsIgnoreCase(emisorBuscado)) {
                JOptionPane.showMessageDialog(null,
                        "¡ENCONTRADO en POSICION " + (Cant + 1) + "!"
                        + "\nAsunto: " + inbox[Cant].getAsunto());
            }
        }

        buscarPorEmisorRecursivo(emisorBuscado, Cant + 1);
    }

    public void eliminarCorreosLeidosRecursivo(int Cant) {
        if (Cant >= inbox.length) {
            JOptionPane.showMessageDialog(null, "Proceso de eliminacion terminado.");
            return;
        }

        if (inbox[Cant] != null) {
            if (inbox[Cant].isLeido() == true) {
                inbox[Cant] = null;
            }
        }

        eliminarCorreosLeidosRecursivo(Cant + 1);
    }

    public int contarTotalesRecursivo(int Cant) {
        if (Cant == inbox.length) {
            return 0;
        }

        int cuentaActual = 0;
        if (inbox[Cant] != null) {
            cuentaActual = 1;
        }

        int resultadoRestante = contarTotalesRecursivo(Cant + 1);

        return cuentaActual + resultadoRestante;
    }

    private int contarSinLeer(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private int contarTotales(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
