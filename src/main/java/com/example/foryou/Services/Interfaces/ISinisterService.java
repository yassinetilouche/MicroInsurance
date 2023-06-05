package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.Sinister;

import java.util.List;

public interface ISinisterService {
    Sinister add(Sinister s);

    Sinister edit(Sinister s);

    List<Sinister> selectAll();

    Sinister selectById(int sinisterId);

    void deleteById(int sinisterId);

    void delete(Sinister s);

    List<Sinister> addAll(List<Sinister> list);

    void deleteAll(List<Sinister> list);
}
