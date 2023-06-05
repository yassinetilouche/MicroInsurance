package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.Notification;
import com.example.foryou.Services.Interfaces.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Notification")
public class NotificationRestController {
        private INotificationService iNotificationService;
    @GetMapping("/afficherArchiveNotif")
    public List<Notification> afficherAll(){
        return iNotificationService.selectAllNotification();
    }

    }




