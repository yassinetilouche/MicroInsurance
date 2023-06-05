package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.Reclamation;
import com.example.foryou.DAO.Entities.Status;
import com.example.foryou.DAO.Entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IReclamationService {


    Reclamation add(Reclamation rec);

    Reclamation edit(Reclamation rec);

    List<Reclamation> selectAll();

    Reclamation selectById(int reclamationId);

    void deleteById(int roleId);

    void delete(Reclamation rec);

    List<Reclamation> addAll(List<Reclamation> listrec);

    void deleteAll(List<Reclamation> list);
    void deleteAll();
    List<Reclamation> findByStatus(Status status);

    String getCurrentUserName();

    User getUser(String username);
}