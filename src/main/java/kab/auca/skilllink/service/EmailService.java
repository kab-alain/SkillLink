package kab.auca.skilllink.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("skillink250@gmail.com");  // Replace with your sender email
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}

