package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response,Integer> {
}