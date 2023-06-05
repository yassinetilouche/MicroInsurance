package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.Role;
import com.example.foryou.DAO.Entities.RoleType;

import java.util.List;

public interface IroleService {
    Role add(Role r);

    Role edit(Role r);

    List<Role> selectAll();

    Role selectById(int roleId);

    void deleteById(int roleId);

    void delete(Role r);

    List<Role> addAll(List<Role> list);

    void deleteAll(List<Role> list);

    List<Role> selectByRoleType(RoleType type);
}
