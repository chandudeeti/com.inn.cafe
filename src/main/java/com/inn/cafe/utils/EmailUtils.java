package com.inn.cafe.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Send a simple email message.
     *
     * @param to      The recipient's email address.
     * @param subject The email subject.
     * @param text    The email message text.
     * @param list    A list of email addresses to include as CC recipients.
     */
    public void sendSimpleMessage(String to, String subject, String text, List<String> list) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nanipatel112@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if (list != null && list.size() > 0) {
            message.setCc(getCcArray(list));
        }
        mailSender.send(message);
    }

    /**
     * Get an array of email addresses from a list.
     *
     * @param ccList A list of email addresses.
     * @return An array of email addresses.
     */
    private String[] getCcArray(List<String> ccList) {
        String[] cc = new String[ccList.size()];
        for (int i = 0; i < ccList.size(); i++) {
            cc[i] = ccList.get(i);
        }
        return cc;
    }

    /**
     * Send a MIME email message for password recovery.
     *
     * @param to      The recipient's email address.
     * @param subject The email subject.
     * @param password The password to include in the email.
     * @throws MessagingException if there is an issue with creating or sending the email.
     */
    public void forgotMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom("nanipatel112@gmail.com");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMsg, "text/html");
        mailSender.send(message);
    }
}
