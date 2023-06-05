package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.Contracts;
import com.example.foryou.DAO.Entities.Notification;

import javax.mail.MessagingException;
import java.util.List;

public interface INotificationService {
     public List<Notification> addAllNotif(List<Notification> notifications);
     List<Notification> selectAllNotification();
     void deleteAllNotif (List<Notification> notifications);
     void sendEmail(String to, String subject, String text) throws MessagingException;
}
