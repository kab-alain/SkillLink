package kab.auca.skilllink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kab.auca.skilllink.service.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendTestEmail() {
        // Sending an email using the EmailService
        emailService.sendEmail("recipient@example.com", "Test Email", "This is a test email from SendGrid.");
        return "Email sent successfully!";
    }
}
