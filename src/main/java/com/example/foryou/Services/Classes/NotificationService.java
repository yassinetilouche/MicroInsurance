package com.example.foryou.Services.Classes;
import com.example.foryou.DAO.Entities.Notification;
import com.example.foryou.DAO.Repositories.NotificationRepository;
import com.example.foryou.Services.Interfaces.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@AllArgsConstructor
@SpringBootApplication
public class NotificationService implements INotificationService {
        NotificationRepository notificationRepository;
        private JavaMailSender javaMailSender;

    @Override
    public List<Notification> addAllNotif(List<Notification> notifications)   {
        return notificationRepository.saveAll(notifications);
    }
    @Override
    public List<Notification> selectAllNotification() {
        return notificationRepository.findAll();
    }
    @Override
    public void deleteAllNotif(List<Notification> notifications) {
        notificationRepository.deleteAll(notifications);
    }

    // Envoyer des notifications par courrier électronique:
    public void sendEmail(String to, String subject, String text) throws MessagingException {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(message);
        }





    }


