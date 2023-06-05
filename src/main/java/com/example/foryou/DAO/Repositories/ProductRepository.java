package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
