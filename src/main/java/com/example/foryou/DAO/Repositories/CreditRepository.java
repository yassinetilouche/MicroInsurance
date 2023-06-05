package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit,Integer> {
    @Query(value = "select SUM(c.rentability) from credit c ",nativeQuery = true)
   float SommeRentabilite();

    @Query(value = "select c.* from credit c join user u on c.client_user_id=u.user_id  where ((c.refund_amount/c.nb_years)<=0.4*u.salaire)  ",nativeQuery = true)
    List<Credit> selectBySalary();

    @Query(value="select SUM(c.refund_amount-c.amount) from credit c join user u on c.client_user_id=u.user_id where c.type=?1 and u.region=?2 ",nativeQuery = true)
    float selectByTypeAndRegion(String type,String region);

}