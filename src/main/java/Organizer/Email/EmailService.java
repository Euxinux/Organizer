package Organizer.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailSender{

    @Autowired
    JavaMailSender mailSender;

    @Async
    @Override
    public void sendEmail(String to, String email){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject("Confirm Mail - Organizer");
            helper.setText(email,true);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            System.out.println(e);
        }
    }
}