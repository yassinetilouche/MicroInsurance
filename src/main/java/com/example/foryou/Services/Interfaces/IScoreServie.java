package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.User;

public interface IScoreServie {
     double calculerScore(Long clientId);
    double modifierMontantContrat(int contratId);
}
