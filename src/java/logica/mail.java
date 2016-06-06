/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author luis
 */
public class mail {
    
    public void avisarSolicitante(String nombreprestador, String libro, String numeroprestador, String micorreo, String correoprestador){
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "soporte.prestamo.libros.riesgo@gmail.com");
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(micorreo));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(micorreo));
            message.setSubject("[Préstamo de Libros]Solicitud de prestamo aceptada");
            message.setText("La solicitud por el libro: "+ libro +" ha sido aceptada. \n Ponte en contacto con "+nombreprestador+" para"
                    + " arreglar los detalles\n"+"Numero: "+numeroprestador+"\nCorreo: "+correoprestador+"\nGracias por tu preferencia\nAtentamente"
                    + " Soporte de Préstamo de Libros");

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            //soporte.prestamo.libros.riesgo@gmail.com
            //riesgo12345
            t.connect("soporte.prestamo.libros.riesgo@gmail.com", "riesgo12345");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void avisarPrestador(String nombresolicitante, String libro, String numerosolicitante, String micorreo, String correosolicitante){
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "soporte.prestamo.libros.riesgo@gmail.com");
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(micorreo));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(micorreo));
            message.setSubject("[Préstamo de Libros]Solicitud de prestamo aceptada");
            message.setText("Has aceptado la solicitud por el libro: "+ libro +". \n Ponte en contacto con "+nombresolicitante+" para"
                    + " arreglar los detalles\n"+"Numero: "+numerosolicitante+"\nCorreo: "+correosolicitante+"\nGracias por tu preferencia\nAtentamente"
                    + " Soporte de Préstamo de Libros");
            Transport t = session.getTransport("smtp");
            //soporte.prestamo.libros.riesgo@gmail.com
            //riesgo12345
            t.connect("soporte.prestamo.libros.riesgo@gmail.com", "riesgo12345");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void correo(String correo  , String contra) {

        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "soporte.prestamo.libros.riesgo@gmail.com");
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correo));
            message.setSubject("[Préstamo de Libros]Recuperación de contraseña");
            message.setText("Tu contraseña es:\n "+ contra +"\n Atentamente Soporte de Préstamo de Libros");

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            //soporte.prestamo.libros.riesgo@gmail.com
            //riesgo12345
            t.connect("soporte.prestamo.libros.riesgo@gmail.com", "riesgo12345");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
