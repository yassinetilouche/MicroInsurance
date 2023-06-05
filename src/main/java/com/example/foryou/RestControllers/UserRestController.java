package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.User;
import com.example.foryou.Services.Interfaces.IuserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin()
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("User")
public class UserRestController {

    IuserService iuserService;

    @GetMapping("/afficherUser")
    public List<User> afficherUser() {
        return iuserService.selectAll();
    }

    @PostMapping("/ajouterUser")
    public ResponseEntity<String> ajouterUser(@RequestBody User user) {
        iuserService.add(user);
        return ResponseEntity.ok("Added successfully !");

    }

    @GetMapping("/afficherUserAvecId/{id}")
    public User afficherUserAvecId(@PathVariable Long id) {
        return iuserService.selectById(id);
    }

    @PostMapping("/ajouterAllUser")
    public ResponseEntity<String> addAllUser(@RequestBody List<User> list) {
        iuserService.addAll(list);
        return ResponseEntity.ok("Added successfully !");

    }

    @PutMapping("/modifierUser")
    public ResponseEntity<String> editUser(@RequestBody User user) {
        iuserService.edit(user);
        return ResponseEntity.ok("Edited successfully !");
    }

    @DeleteMapping("/deleteUserById")
    public ResponseEntity<String> deleteUserById(@RequestBody Long id) {
        iuserService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully !");
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUserByObject(@RequestBody User user) {
        iuserService.delete(user);
        return ResponseEntity.ok("Deleted successfully !");
    }

    @GetMapping("/AfficherUserAvecAdress")
    public List<User> afficherUserAvecAdress(@RequestParam String adress) {
        return iuserService.selectByAdress(adress);
    }

    @GetMapping("/AfficherUserAvecUsername")
    public User afficherUserAvecUsername(@RequestParam String username) {
        return iuserService.selectByUsername(username);
    }

    @GetMapping("/AfficherUserAvecEmail")
    public User afficherUserAvecEmail(@RequestParam String email) {
        return iuserService.selectByEmail(email);
    }


    @GetMapping("/greeting")
    public String getUser() {
        return "Welcome!";
    }

    @GetMapping("/{id}/solvency-ratio")
    public ResponseEntity<?> getSolvencyRatio(@PathVariable Long id) {
        return ResponseEntity.ok(iuserService.getSolvencyRatio(id));
    }

    @GetMapping("/{userId}/liquidity-ratio")
    public ResponseEntity<Float> calculateLiquidityRatio(@PathVariable Long userId) {
        User user = iuserService.selectById(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        float liquidityRatio = user.calculateLiquidityRatio();

        return ResponseEntity.ok(liquidityRatio);
    }

    @GetMapping("/{userId}/income-ratio")
    public ResponseEntity<Float> calculateIncomeRatio(@PathVariable Long userId) {
        User user = iuserService.selectById(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        float liquidityRatio = user.calculateNetIncomeRatio();

        return ResponseEntity.ok(liquidityRatio);
    }
}
