package com.example.foryou.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import com.example.foryou.RestControllers.ReclamationRestController;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int reclamationId;
     LocalDate creationDate;
     String title;
     String category;
     String details;

   @Enumerated(EnumType.STRING)
    Status status = Status.EN_ATTENTE;
    @Enumerated(EnumType.STRING)
    Etat etat = null;
    //Etat etat = null;
    @ManyToOne
    @JsonIgnore
    User client;

     @OneToOne(mappedBy = "reclamation")
     @JsonIgnore
     Response response;





}
