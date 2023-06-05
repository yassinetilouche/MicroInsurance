package com.example.foryou.DAO.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Contracts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int contract_id ;
    @Temporal(TemporalType.DATE)
    Date creationDate;
    @Temporal(TemporalType.DATE)
    Date startDate;
    @Temporal(TemporalType.DATE)
    Date exprirationDate;
    double ceilingAmount;
    int installementsnbr;
    int duration ;
    @Temporal(TemporalType.DATE)
    Date lastRenewalDate;
    @Enumerated(EnumType.STRING)
    PaymentFormality paymentFormality;
    @Temporal(TemporalType.DATE)
    Date paymentDate;
    @Enumerated(EnumType.STRING)
    PaymentType paymentType;
    boolean renewable;
    @ManyToOne
    @JsonIgnore
    User assureur;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    User user;
    @OneToMany(mappedBy = "contract")
    @JsonIgnore
    List<Sinister> sinisterList;
    @ManyToOne
    @JsonIgnore
    Subproduct subproduct;
}
