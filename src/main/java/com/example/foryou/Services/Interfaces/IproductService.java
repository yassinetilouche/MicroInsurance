package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.Product;

import java.util.List;

public interface IproductService {
    Product add(Product a);
    Product edit(Product a);
    List<Product> selectAll();
    Product selectById(int id);
    void deleteById(int id);
    void delete(Product a);
    List<Product> addAll( List<Product> list);
    void deleteAll(List<Product> list);
    void deleteAll();
}
