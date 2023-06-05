package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.Event;
import com.example.foryou.DAO.Entities.InscriptionEvent;
import com.example.foryou.DAO.Entities.User;
import com.example.foryou.Services.Classes.UserService;
import com.example.foryou.Services.Interfaces.IeventService;
import com.example.foryou.Services.Interfaces.IinscriptionEventService;
import com.example.foryou.Services.Interfaces.IuserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Event")
public class EventRestController {
    private IeventService iEventService;
    IuserService iuserService;

    private IinscriptionEventService iinscriptionEventService;

    @GetMapping("/afficherEventById/{Id}")
    public Event afficherEventById(@PathVariable int Id) {

        return iEventService.selectById(Id);
    }

    @GetMapping("/afficherAllEvents")
    public List<Event> afficherAll() {
        return iEventService.selectAll();
    }


    @PostMapping("/ajouterEventavecuser")
    public Event addEvent(@RequestBody Event Event) {
        return iEventService.add(Event);


    }

    @PostMapping("ajouterAllEvents")
    public ResponseEntity<String> addAllEvent(@RequestBody List<Event> EventList) {
        iEventService.addAll(EventList);
        return ResponseEntity.ok("Added successfully.");
    }

    @PutMapping("/ModifierEvent")
    public Event editEvent(@RequestBody Event Event) {

        return iEventService.edit(Event);
    }

    @DeleteMapping("SupprimerEvent")
    public ResponseEntity<String> deleteEvent(@RequestBody Event Event) {
        iEventService.delete(Event);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerEventById")
    public ResponseEntity<String> supprimerEventsById(@RequestParam int EventId) {
        iEventService.deleteById(EventId);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAllEvents")
    public ResponseEntity<String> supprimerAllEvents(@RequestBody List<Event> EventList) {
        iEventService.deleteAll(EventList);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAll")
    public ResponseEntity<String> SupprimerAll() {
        iEventService.deleteAll();
        return ResponseEntity.ok("Deleted successfully.");
    }

    @PutMapping("/CalcualteRating")
    @Scheduled(cron = "0 0 0 * * *")
    public void calculateRating() {
        List<Event> list = (List<Event>) iEventService.selectAll();
        for (Event i : list) {
            List<InscriptionEvent> listinsc = (List<InscriptionEvent>) i.getInscriptionEvent();
            double totalMarks = 0;
            int count = 0;
            for (InscriptionEvent j : listinsc) {
                if (j.getMark() != null) {
                    totalMarks += j.getMark();
                    count++;
                }
            }
            if (count > 0) {
                double averageMark = totalMarks / count;
                System.out.println("Average mark for event " + i.getName_Event() + ": " + averageMark);
                i.setMark(averageMark);
                iEventService.add(i);
            } else {
                System.out.println("No marks found for event " + i.getName_Event());
            }
        }
    }

    @PutMapping("/SetRatingforOrganizers")
    @Scheduled(cron = "0 0 0 * * *")
    public void calculateUserRating() {
        List<User> list = (List<User>) iuserService.selectAll();
        for (User i : list) {
            List<Event> listevent = (List<Event>) i.getEventsCreated();
            double totalMarks = 0;
            int count = 0;
            for (Event j : listevent) {
                if (j.getMark() != null) {
                    totalMarks += j.getMark();
                    count++;
                }
            }
            if (count > 0) {
                double averageMark = totalMarks / count;
                System.out.println("Average mark for organizer " + i.getFirstName() + ": " + averageMark);
                i.setFeedbackmark(averageMark);
                iuserService.add(i);
            } else {
                System.out.println("No marks found for event " + i.getFirstName());
            }
        }
    }
    @GetMapping("/eventssortedbymarks")
    public List<Event> getEventsSortedByMarks() {
        List<Event> events = iEventService.selectAll(); // assume eventService is already defined

        // Sort events by their marks
        Collections.sort(events, Comparator.comparing(Event::getMark).reversed());

        return events;
    }
}



