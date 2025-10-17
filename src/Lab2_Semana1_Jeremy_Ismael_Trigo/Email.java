/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2_Semana1_Jeremy_Ismael_Trigo;

import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author jerem
 */
public class Email {

    String emisor;
    String asunto;
    String contenido;
    boolean leido;

    public Email(String emisor, String asunto, String contenido) {
        this.emisor = emisor;
        this.asunto = asunto;
        this.contenido = contenido;
        this.leido = false;
    }

    public String getEmisor() {
        return emisor;
    }

    public String getAsunto() {
        return asunto;
    }

    public boolean isLeido() {
        return leido;
    }

    public void marcarComoLeido() {
        if (!this.leido) {
            this.leido = true;
        }
    }

    public void mostrar() {
        Calendar CD = Calendar.getInstance();

        JOptionPane.showMessageDialog(null, "EMISOR: " + emisor + "\nASUNTO: " + asunto + "\nCONTENIDO: " + contenido + "\nFECHA: " + CD.getTime(), "Detalles del Correo",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
