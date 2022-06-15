package es.cesar.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender javaMailSender;



    public void enviarEmail(String toEmail, String subject, String body) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setFrom("catsandogshelter@gmail.com");
        helper.setTo(toEmail);
        helper.setText(body, true);
        helper.setSubject(subject);
        javaMailSender.send(mimeMessage);
        System.out.println("Email enviado");
    }
}
