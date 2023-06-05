package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.User;

import java.util.List;
import java.util.Map;

public interface IuserService {
    User add(User u);

    User edit(User u);

    List<User> selectAll();

    User selectById(Long userId);

    void deleteById(Long roleId);

    void delete(User u);

    List<User> addAll(List<User> list);

    void deleteAll(List<User> list);

    List<User> selectByAdress(String adress);

    User selectByUsername(String username);

    User selectByEmail(String email);

    public Map<String, Object> getSolvencyRatio(Long id);
}
