package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long id);
    List<User> findByAdress(String adress);

    User findByUsername(String username);


    @Query("SELECT c FROM User c WHERE c.email = ?1")
     User findByEmail(String email);

//     User findByResetPasswordToken(String token);
    @Query(value="SELECT c.* FROM User c WHERE c.reset_password_token = ?1",nativeQuery = true)
    User getResetPasswordToken(String token);
}
