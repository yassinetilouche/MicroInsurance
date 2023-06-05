package com.example.foryou.DAO.Entities;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeRequest {
    private String token;
    private int amount;
}

