/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author TGDD
 */
public class Email {

    static final String from = "hai@gmail.com";
    static final String password = "vfum cgin wlew ubwr";

    public static boolean sendEmail(String to, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "465"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Authentication
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(from, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        // Message
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(content, "text/HTML; charset=UTF-8");

            // Send Email
            Transport.send(msg);
           
            System.out.println("Email sent successfully!");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}       


