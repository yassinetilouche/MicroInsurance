package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.User;
import com.example.foryou.DAO.Repositories.UserRepository;
import com.example.foryou.Services.Interfaces.IuserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements IuserService {
    UserRepository userRepository;

    @Override
    public User add(User u) {
        return userRepository.save(u);
    }

    @Override
    public User edit(User u) {
        return userRepository.save(u);
    }

    @Override
    public List<User> selectAll() {
        return userRepository.findAll();
    }

    @Override
    public User selectById(Long userId) {
        return userRepository.findByUserId(userId).get();
    }

    @Override
    public void deleteById(Long roleId) {
        userRepository.deleteById(roleId);
    }

    @Override
    public void delete(User u) {
        userRepository.delete(u);
    }

    @Override
    public List<User> addAll(List<User> list) {
        return userRepository.saveAll(list);
    }

    @Override
    public void deleteAll(List<User> list) {
        userRepository.deleteAll(list);
    }


    @Override
    public List<User> selectByAdress(String adress) {
        //return abonnementRepository.getByTypeAbo(type);
        return userRepository.findByAdress(adress);
    }

    @Override
    public User selectByUsername(String username) {
        //return abonnementRepository.getByTypeAbo(type);
        return userRepository.findByUsername(username);
    }


    @Override
    public User selectByEmail(String email) {
        //return abonnementRepository.getByTypeAbo(type);
        return userRepository.findByEmail(email);
    }

    public void updateResetPasswordToken(String token, String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new MessagingException("Could not find any user with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.getResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @SneakyThrows
    public Map<String, Object> getSolvencyRatio(Long id) {
        Optional<User> userOptional = userRepository.findByUserId(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            float solvencyRatio = user.calculateSolvencyRatio();
            boolean isSolvent = user.isSolvent();
            boolean hasLiquidityProblems = user.hasLiquidityProblems();

            Map<String, Object> response = new HashMap<String, Object>();
            response.put("solvencyRatio", solvencyRatio);
            response.put("isSolvent", isSolvent);
            response.put("hasLiquidityProblems", hasLiquidityProblems);
            return response;
        }

        throw new Exception("user not found");
    }
}
