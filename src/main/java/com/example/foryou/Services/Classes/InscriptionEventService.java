package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.Event;
import com.example.foryou.DAO.Entities.InscriptionEvent;
import com.example.foryou.DAO.Entities.User;
import com.example.foryou.DAO.Repositories.EventRepository;
import com.example.foryou.DAO.Repositories.InscriptionEventRepository;
import com.example.foryou.DAO.Repositories.UserRepository;
import com.example.foryou.Services.Interfaces.IinscriptionEventService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class InscriptionEventService implements IinscriptionEventService {
    private InscriptionEventRepository inscriptionEventRepository;
    private EventRepository eventRepository;
    private UserRepository userRepository;
    @Override
    public InscriptionEvent add(InscriptionEvent a) {
        return inscriptionEventRepository.save(a);
    }

    @Override
    public InscriptionEvent edit(InscriptionEvent a) {
        return inscriptionEventRepository.save(a);
    }

    @Override
    public List<InscriptionEvent> selectAll() {
        return inscriptionEventRepository.findAll();
    }

    @Override
    public InscriptionEvent selectById(int id) {
        return inscriptionEventRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
        inscriptionEventRepository.deleteById(id);
    }

    @Override
    public void delete(InscriptionEvent a) {
        inscriptionEventRepository.delete(a);
    }

    @Override
    public List<InscriptionEvent> addAll(List<InscriptionEvent> list) {
        return inscriptionEventRepository.saveAll(list);
    }

    @Override
    public void deleteAll(List<InscriptionEvent> list) {
        inscriptionEventRepository.deleteAll(list);
    }
    @Override
    public void deleteAll() {
        inscriptionEventRepository.deleteAll();
    }
    @Override

    public InscriptionEvent assignParticipantandEventToInscription(int idEvent, InscriptionEvent i) {
        String username = this.getCurrentUserName();
        User user = this.getUser(username);
        i.setParticipant(user);
        Event event= eventRepository.findById(idEvent).get();
        i.setEvent(event);

        return inscriptionEventRepository.save(i);
    }
    @Override
    public void assignMarkEvent(int mark, int inscriptionid){

        InscriptionEvent inscription= inscriptionEventRepository.findById(inscriptionid).get();
        inscription.setMark(mark);
        inscriptionEventRepository.save(inscription);
    }

    @Override
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

}
