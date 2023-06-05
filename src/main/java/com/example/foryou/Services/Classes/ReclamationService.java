package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.Reclamation;
import com.example.foryou.DAO.Entities.Status;
import com.example.foryou.DAO.Entities.User;
import com.example.foryou.DAO.Repositories.UserRepository;
import com.example.foryou.Services.Interfaces.IReclamationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.foryou.DAO.Repositories.ReclamationRepository;

import java.time.LocalDate;
import java.util.List;

import static com.example.foryou.RestControllers.ReclamationRestController.getEmotionalState;

@Service
@AllArgsConstructor
public class ReclamationService implements IReclamationService {

    private ReclamationRepository reclamationRepository;
    private UserRepository userRepository;

    @Override
    public Reclamation add(Reclamation rec) {
        String username = this.getCurrentUserName();
        User user = this.getUser(username);
        rec.setClient(user);
        rec.setEtat(getEmotionalState(rec.getDetails()));
        rec.setCreationDate(LocalDate.now());
        return reclamationRepository.save(rec);
    }

    @Override
    public Reclamation edit(Reclamation rec) {
        return reclamationRepository.save(rec);
    }

    @Override
    public List<Reclamation> selectAll() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation selectById(int reclamationId) {
        return reclamationRepository.findById(reclamationId).get();
    }

    @Override
    public void deleteById(int reclamationId) {
        reclamationRepository.deleteById(reclamationId);
    }

    @Override
    public void delete(Reclamation rec) {
        reclamationRepository.delete(rec);
    }

    @Override
    public List<Reclamation> addAll(List<Reclamation> listrec) {
        return reclamationRepository.saveAll(listrec);
    }

    @Override
    public void deleteAll(List<Reclamation> listrec) { reclamationRepository.deleteAll(listrec);
    }

    @Override
    public void deleteAll() {
        reclamationRepository.deleteAll();
    }

    @Override
    public List<Reclamation> findByStatus(Status status) {
        return reclamationRepository.findByStatus(status);
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
