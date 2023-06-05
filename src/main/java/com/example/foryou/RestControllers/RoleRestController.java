package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.Role;
import com.example.foryou.DAO.Entities.RoleType;
import com.example.foryou.Services.Interfaces.IroleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Role")
public class RoleRestController {

    private IroleService iroleService;

    @GetMapping("/afficherRole")
    public List<Role> afficherRole() {
        return iroleService.selectAll();
    }

    @PostMapping("/ajouterRole")
    public ResponseEntity<String> ajouterRole(@RequestBody Role role) {
        iroleService.add(role);
        return ResponseEntity.ok("Added successfully !");
    }

    @GetMapping("/afficherRoleAvecId/{id}")
    public Role afficherRoleAvecId(@PathVariable int id) {
        return iroleService.selectById(id);
    }

    @PostMapping("/ajouterAllRole")
    public ResponseEntity<String> addAllRole(@RequestBody List<Role> list) {
        iroleService.addAll(list);
        return ResponseEntity.ok("Added successfully !");
    }

    @PutMapping("/modifierRole")
    public ResponseEntity<String> editRole(@RequestBody Role role) {
        iroleService.edit(role);
        return ResponseEntity.ok("Edited successfully !");

    }

    @DeleteMapping("/deleteRoleById")
    public ResponseEntity<String> deleteRoleById(@RequestBody int id) {
        iroleService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully !");

    }

    @DeleteMapping("/deleteRole")
    public ResponseEntity<String> deleteRoleByObject(@RequestBody Role role) {
        iroleService.delete(role);
        return ResponseEntity.ok("Deleted successfully !");
    }

    @GetMapping("/AfficherRoleAvecRoleType")
    public List<Role> afficherRole2(@RequestParam RoleType type) {
        return iroleService.selectByRoleType(type);
    }
}
