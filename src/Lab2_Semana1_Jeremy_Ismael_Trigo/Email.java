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

    Calendar FH = Calendar.getInstance();

    public Email(String emisor, String asunto, String contenido, boolean leido) {
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

    public String getContenido() {
        return contenido;
    }

    public boolean isLeido() {
        return leido;
    }

       public void leer(boolean leer) {
        if (leer == false) {
            leido = true;
        } else if (leer == true) {
            leido = false;
        }
    }
    
    public void mostrar(){
         JOptionPane.showMessageDialog(null,"EMISOR: "+emisor+"\nASUNTO: "+asunto+"\nCONTENIDO:"+contenido+"\nFECHA: "+FH.getTime());
    }


}
