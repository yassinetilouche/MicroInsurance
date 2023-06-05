package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.ForbiddenWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ForBiddenWordRepository extends JpaRepository<ForbiddenWord, Long> {
}

