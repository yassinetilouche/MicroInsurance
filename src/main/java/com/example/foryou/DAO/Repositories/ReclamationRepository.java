package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.Reclamation;
import com.example.foryou.DAO.Entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation,Integer> {


    List<Reclamation> findByStatus(Status status);
}