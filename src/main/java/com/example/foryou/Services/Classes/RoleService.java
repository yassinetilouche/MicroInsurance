package com.example.foryou.Services.Classes;

import com.example.foryou.Services.Interfaces.IroleService;
import com.example.foryou.DAO.Entities.Role;
import com.example.foryou.DAO.Entities.RoleType;
import com.example.foryou.DAO.Repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService implements IroleService {


    private RoleRepository roleRepository;

    @Override
    public Role add(Role r) {
        return roleRepository.save(r);
    }

    @Override
    public Role edit(Role r) {
        return roleRepository.save(r);
    }

    @Override
    public List<Role> selectAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role selectById(int roleId) {
        return roleRepository.findById(roleId).get();
    }

    @Override
    public void deleteById(int roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public void delete(Role r) {
        roleRepository.delete(r);
    }

    @Override
    public List<Role> addAll(List<Role> list) {
        return roleRepository.saveAll(list);
    }

    @Override
    public void deleteAll(List<Role> list) {
        roleRepository.deleteAll(list);
    }

    @Override
    public List<Role> selectByRoleType(RoleType type) {
        //return abonnementRepository.getByTypeAbo(type);
        return roleRepository.findByRoleType(type);
    }
}
