package com.example.foryou.Services.Interfaces;
import com.example.foryou.DAO.Entities.Credit;
import com.example.foryou.DAO.Entities.User;

import java.util.List;


public interface ICreditService {
    Credit add(Credit c);
    Credit edit(Credit c);

    List<Credit> selectAll();
    Credit selectById(int creditId);
    void deleteById(int creditId);
    void delete(Credit c);
    List<Credit> addAll(List<Credit> list);
    void deleteAll(List<Credit>list);
    void deleteAll();

    /*  public static int calculateYearsBetween(Date date1, Date date2) {

          LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

          LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


          return Math.abs(localDate1.getYear() - localDate2.getYear());
      }*/


    void Calcul1(Credit c);

    void Calcul2(Credit c);

    void Rentabilité();

    List<Credit> Scoring();

    void StatusCredit();

    float Profit(String type, String region);

    String getCurrentUserName();

    User getUser(String username);
}