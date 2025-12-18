package com.toyroom.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class EmailCriticalHandler extends Handler {

    // Заміни ці рядки у своєму файлі:
    private static final String FROM_EMAIL = "myhallstep@gmail.com";
    private static final String PASSWORD   = "lptl lisr miwu pdfx"; // Твій пароль додатка
    private static final String ADMIN_EMAIL = "myhallstep@gmail.com";

    @Override
    public void publish(LogRecord record) {

        if (record.getLevel() != Level.SEVERE) return;

        if (FROM_EMAIL == null || PASSWORD == null || ADMIN_EMAIL == null) {
            System.err.println("Email credentials are not configured");
            return;
        }

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                    }
                }
        );

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(ADMIN_EMAIL)
            );
            message.setSubject("ToyRoom CRITICAL ERROR");

            message.setText(
                    "Time: " + new java.util.Date() + "\n" +
                            "Message: " + record.getMessage()
            );

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override public void flush() {}
    @Override public void close() throws SecurityException {}
}
