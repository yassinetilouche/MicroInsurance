package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.Subproduct;

import java.util.List;

public interface IsubproductService {
    Subproduct add(Subproduct a);
    Subproduct edit(Subproduct a);
    List<Subproduct> selectAll();
    Subproduct selectById(int id);
    void deleteById(int id);
    void delete(Subproduct a);
    List<Subproduct> addAll( List<Subproduct> list);
    void deleteAll(List<Subproduct> list);
    void deleteAll();
}
