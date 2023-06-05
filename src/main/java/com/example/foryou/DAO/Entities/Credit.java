package com.example.foryou.DAO.Entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "creditId")

public class Credit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int creditId;

     float amount;
     float interestRate;
    @Temporal(TemporalType.DATE)
     Date startDate;
    @Temporal(TemporalType.DATE)
     Date endtDate;
     float refundAmount;

     int nb_years;
     float rentability;
     String type;
    @Enumerated(EnumType.STRING)
     StateCredit status;
    @ManyToOne
    @JsonIgnore
    User client;

}
