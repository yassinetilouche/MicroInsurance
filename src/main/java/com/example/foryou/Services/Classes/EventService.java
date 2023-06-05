package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.Event;
import com.example.foryou.DAO.Entities.User;
import com.example.foryou.DAO.Repositories.EventRepository;
import com.example.foryou.DAO.Repositories.UserRepository;
import com.example.foryou.Services.Interfaces.IeventService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class EventService implements IeventService {
    private EventRepository eventRepository;
    private UserRepository userRepository;
    @Override
    public Event add(Event a) {

        String username = this.getCurrentUserName();
        User user = this.getUser(username);

        a.setOrganizer(user);
        return eventRepository.save(a);
    }

    @Override
    public Event edit(Event a) {
        return eventRepository.save(a);
    }

    @Override
    public List<Event> selectAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event selectById(int id) {
        return eventRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void delete(Event a) {
        eventRepository.delete(a);
    }

    @Override
    public List<Event> addAll(List<Event> list) {
        return eventRepository.saveAll(list);
    }

    @Override
    public void deleteAll(List<Event> list) {
        eventRepository.deleteAll(list);
    }
    @Override
    public void deleteAll() {
        eventRepository.deleteAll();
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
