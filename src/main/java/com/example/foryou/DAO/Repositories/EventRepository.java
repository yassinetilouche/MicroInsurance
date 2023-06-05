package com.example.foryou.DAO.Repositories;
import com.example.foryou.DAO.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer>  {
}
