/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebajavafx.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author PabloVE
 */
public class MailManager {
    private String emailFrom;
    private String host;
    private Properties properties;
    private Session session;

    public MailManager() {
        emailFrom = "correo@correo.com";
        
        host = "smtp.gmail.com";
        
        properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("correo@correo.com", "*******");
            }
        });
    }
    
    public boolean SendMailOnlyText(String emailReceptor, String asunto, String texto){
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor));
            message.setSubject(asunto);
            message.setText(texto);
            Transport.send(message);
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }
    
    public boolean SendMailTextWithHTML(String emailReceptor, String asunto, String nombre, String password){
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor));
            message.setSubject(asunto);
            message.setText("");
            message.setContent(
                "<h1>Bienvenido a Pali</h1><br/>"+
                "<h2>Bienvenido "+nombre+" a nuestra empresa</h2>"+
                "<p>Su usuario es: <strong>"+emailReceptor+"</strong></p>"+
                "<p>Su contraseña es: <strong>"+password+"</strong></p>"+
                "<img src=\"https://aceroschetumal.com/wp-content/uploads/2019/04/bienvenido_aceros_chetumal-870x480.jpg\" alt=\"imagen bienvenida\" />",
                "text/html"
            );
            Transport.send(message);
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }
    
    
}
