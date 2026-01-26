package com.ordermanagement.OrderManagementAndAuthServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements  EmaiService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${app.mail.from}")
    private String fromEmail;
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("Email Send...");
        System.out.println("To:"+to);
        System.out.println("Subject:"+subject);
        System.out.println("Body:"+body);
    }

    @Async("emailExecutor")
    @Override
    public void sendPasswordChangeAlert(String toEmail, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("🔐 Password Changed Successfully");
        message.setText(
                "Dear" + userName + ",\n\n" +
                        "Your account password was changed successfully.\n\n" +
                        "If this was not you, please contact support immediately.\n\n" +
                        "Regards,\nOrder Management Team"
        );

        javaMailSender.send(message);
    }
}
