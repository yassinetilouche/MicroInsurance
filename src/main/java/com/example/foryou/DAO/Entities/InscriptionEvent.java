package com.example.foryou.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class InscriptionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int inscription_event_id;
    @ManyToOne

    User Participant;
    @ManyToOne

    Event event;
    Integer Mark;
}
