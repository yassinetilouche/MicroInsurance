package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.Credit;
import com.example.foryou.Services.Interfaces.IScoreServie;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Score")
public class ScoreRestController {
    private IScoreServie iScoreServie;
    @GetMapping("/Scoring/{clientId}")
    public double scoring(@PathVariable Long clientId){
        return iScoreServie.calculerScore(clientId);
    }
    @GetMapping("/UpdateAmount/{contratId}")
    double modifierMontantContrat(@PathVariable int contratId) {
        return iScoreServie.modifierMontantContrat(contratId);
    }



}
