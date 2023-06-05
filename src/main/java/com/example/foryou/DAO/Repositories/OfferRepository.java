package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OfferRepository extends JpaRepository <Offer,Integer> {
}
