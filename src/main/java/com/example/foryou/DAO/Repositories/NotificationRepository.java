package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
}
