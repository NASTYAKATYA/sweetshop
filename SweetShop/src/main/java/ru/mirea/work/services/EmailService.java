package ru.mirea.work.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {

    @Async
    public void sendmail(String mail, String message, boolean isManager) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("katyabi2010@gmail.com", "wbfatlobyccnsdqv");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("katyabi2010@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        if (isManager)
            msg.setSubject("SWEETSHOP/был оформлен новый заказ");
        else
            msg.setSubject("SWEETSHOP/был оформлен новый заказ");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html; charset=UTF-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
