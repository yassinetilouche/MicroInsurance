package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.InscriptionEvent;
import com.example.foryou.DAO.Entities.User;

import java.util.List;

public interface IinscriptionEventService {
    InscriptionEvent add(InscriptionEvent a);

    InscriptionEvent edit(InscriptionEvent a);

    List<InscriptionEvent> selectAll();

    InscriptionEvent selectById(int id);

    void deleteById(int id);

    void delete(InscriptionEvent a);

    List<InscriptionEvent> addAll(List<InscriptionEvent> list);

    void deleteAll(List<InscriptionEvent> list);

    void deleteAll();


    InscriptionEvent assignParticipantandEventToInscription(int idEvent, InscriptionEvent i);

    void assignMarkEvent(int mark, int inscriptionid);

    String getCurrentUserName();

    User getUser(String username);
}