package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.Role;
import com.example.foryou.DAO.Entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository <Role,Integer> {

    List<Role> findByRoleType(RoleType type);
}
