package com.example.foryou.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int event_id;

    LocalDate date_Event;
    String name_Event;
    String place;
    Double mark;
    @ManyToOne
    @JsonIgnore
    User organizer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="event")
    @JsonIgnore
    List<InscriptionEvent> InscriptionEvent;

}
