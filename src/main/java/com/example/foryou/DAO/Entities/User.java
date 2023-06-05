package com.example.foryou.DAO.Entities;




import com.fasterxml.jackson.annotation.*;



import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User implements Serializable {
    private static final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    String username;
    @JsonIgnore
    String password;
    String firstName;
    String lastName;
    String email;
    String profession;
    @Temporal(TemporalType.DATE)
     Date birthDate;
     String adress;
     long phone;
     String expertiseDomain;
     float salaire;
     String region;

    @Enumerated(EnumType.STRING)
    Gender gender;

    float currentAssets;
    float currentLiabilities;
    float nonCurrentAssets;
    float nonCurrentLiabilities;

    float totalIncome;
    float totalExpenses;

    int age;
    //Client assurance auto
    int anneesPermis ;
    boolean aEuAccident ;
    int nbSinistres;
    // Client assurance agriculture
    int surfaceExploitation ;
    int nbAnimaux ;
    int nbInstallations ;
    int valeurCultures;
    // Client assurance credit
    int DureeCredit;
    int MontantCredit;
    @Enumerated(EnumType.STRING)
    Type Insurancetype;

    @ManyToOne

    @JsonIgnore

    @JsonIgnoreProperties("user")

    User user;
    @OneToMany
    @JsonIgnore
    List<User> userList;
    @ManyToOne
    @JsonIgnore
    Role role;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    List<Reclamation> reclamationList;
    @OneToMany(mappedBy = "assureur")
    @JsonIgnore
    List<Contracts> contractsA;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<Contracts> contractsList;
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    List<Sinister> sinisterList;


    @OneToMany(mappedBy = "transmitter")
    @JsonIgnore
    List<Notification> notificationTList;
    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    List<Notification> notificationRList;
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    List<Credit> creditList;
    String resetPasswordToken;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="Participant")
    @JsonIgnore
    List<InscriptionEvent> InscriptionEvent;
    @OneToMany(mappedBy = "organizer")
    @JsonIgnore
    List<Event> EventsCreated;
    Double Feedbackmark;

    public float calculateSolvencyRatio() {
        float totalAssets = currentAssets + nonCurrentAssets;
        float totalLiabilities = currentLiabilities + nonCurrentLiabilities;
        float solvencyRatio = 0.0f;

        if (totalLiabilities != 0.0f) {
            solvencyRatio = totalAssets / totalLiabilities;
        }

        return solvencyRatio;
    }
    public boolean isSolvent() {
        boolean solvent = false;

        if (currentAssets > currentLiabilities && nonCurrentAssets > nonCurrentLiabilities) {
            solvent = true;
        }

        return solvent;
    }

    public boolean hasLiquidityProblems() {
        boolean liquidityProblems = false;

        if (currentAssets < currentLiabilities) {
            liquidityProblems = true;
        }

        return liquidityProblems;
    }

    public float calculateLiquidityRatio() {
        float currentRatio = calculateCurrentRatio();
        float quickRatio = calculateQuickRatio();
        float liquidityRatio = 0.0f;

        if (quickRatio != 0.0f) {
            liquidityRatio = (currentRatio - quickRatio) / quickRatio;
        }

        return liquidityRatio;
    }

    public float calculateCurrentRatio() {
        float currentRatio = 0.0f;

        if (currentLiabilities != 0.0f) {
            currentRatio = currentAssets / currentLiabilities;
        }

        return currentRatio;
    }

    public float calculateQuickRatio() {
        float quickRatio = 0.0f;
        float quickAssets = currentAssets - (currentLiabilities - nonCurrentLiabilities);

        if (currentLiabilities != 0.0f) {
            quickRatio = quickAssets / currentLiabilities;
        }

        return quickRatio;
    }

    public float calculateNetIncomeRatio() {

        float netIncome = totalIncome - totalExpenses;
        float netIncomeRatio = 0.0f;

        if (totalIncome != 0.0f) {
            netIncomeRatio = netIncome / totalIncome;
        }

        return netIncomeRatio;
    }
}
