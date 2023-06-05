package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.Contracts;
import com.example.foryou.DAO.Entities.Type;
import com.example.foryou.DAO.Entities.User;
import com.example.foryou.DAO.Repositories.ContractRepository;
import com.example.foryou.DAO.Repositories.UserRepository;
import com.example.foryou.Services.Interfaces.IContractService;
import com.example.foryou.Services.Interfaces.ICreditService;
import com.example.foryou.Services.Interfaces.IScoreServie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScoreService implements IScoreServie {
    ContractRepository contractRepository;
    IContractService iContractService;
    UserRepository userRepository;

    public double calculerScore(Long clientId) {
        User client = userRepository.findById(clientId).get();
        String typeContrat = client.getInsurancetype().toString();
        double score = 0;
        if (typeContrat.equals("CAR_INSURANCE")) {
            int age = client.getAge();
            int anneesPermis = client.getAnneesPermis();
            boolean aEuAccident = client.isAEuAccident();
            int nbSinistres = client.getNbSinistres();
            score += (age * 0.1)+(anneesPermis * 0.05)+( aEuAccident ? 10 : 0)+(nbSinistres * 2);
        }
        else if (typeContrat.equals("AGRICULTURE_INSURANACE")) {
            int surfaceExploitation = client.getSurfaceExploitation();
            int nbAnimaux = client.getNbAnimaux();
            int nbInstallations = client.getNbInstallations();
            int valeurCultures = client.getValeurCultures();
            score += surfaceExploitation * 0.05;
            score += nbAnimaux * 0.1;
            score += nbInstallations * 0.15;
            score += valeurCultures * 0.01;
        }
        else if (typeContrat.equals("CREDIT_INSURANCE")){
            int nbCredit = client.getCreditList().size();
            int dureeCredit = client.getDureeCredit();
            double montantCredit = client.getMontantCredit();
            score += nbCredit * 0.2;
            score += dureeCredit * 0.1;
            score += montantCredit * 0.3;
        }

        return score;
    }
    public double modifierMontantContrat(int contratId) {
        Contracts contrat = contractRepository.findById(contratId).get();
        User client = contrat.getUser();
        Long clientId = client.getUserId();
        double score = calculerScore(clientId);
        double montantContrat = contrat.getCeilingAmount();
        if (score < 10 ) {
            montantContrat *= 1.3;
        } else if (score < 15) {
            montantContrat *= 1.2;
        } else if (score < 20 ) {
            montantContrat *= 1.1;
        } // Pas de modification si score >= 20
        System.out.println(" le montant avant scoring "+ contrat.getCeilingAmount());
        contrat.setCeilingAmount(montantContrat);
        System.out.println(" le montant après scoring "+ contrat.getCeilingAmount());
        return contrat.getCeilingAmount();
    }

    }