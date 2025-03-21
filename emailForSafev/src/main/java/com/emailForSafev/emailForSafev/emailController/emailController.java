package com.emailForSafev.emailForSafev.emailController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Random;

@RestController
public class emailController {

    private final JavaMailSender mailSender;

    public emailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("sendmail")
    public String sendMail() {
        try {
            String customerEmail = "adityaamolthodsare@gmail.com";
            String coordinatorEmail = "thodsareaditya@gmail.com";
            String chatboxCode = String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit code

            // Sending email to customer
            sendEmail(customerEmail, "Dear Customer, your purchase request is accepted. <br>We will convey details shortly. <br><br>For further communication, use the chatbox on the website with code: <b style='color:blue;'>" + chatboxCode + "</b>");

            // Sending email to coordinator
            sendEmail(coordinatorEmail, "Order received. <br>Please connect through the chatbox using code: <b style='color:green;'>" + chatboxCode + "</b>");

            return chatboxCode + " generated successfully and emails sent";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }

    private void sendEmail(String to, String htmlMessage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("thodsareaditya@gmail.com");
        helper.setTo(to);
        helper.setSubject("Request for Purchase Accepted");
        helper.setText("<html><body style='font-family:Arial,sans-serif;padding:15px;border:1px solid #ddd;background:#f9f9f9;border-radius:10px;'>" +
                "<h2 style='color:#008080;'>SafeV Purchase Update</h2>" +
                "<p style='font-size:16px;'>" + htmlMessage + "</p>" +
                "<hr><p style='font-size:12px;color:#555;'>This is an automated email, please do not reply.</p>" +
                "</body></html>", true);

        mailSender.send(message);
    }
}
