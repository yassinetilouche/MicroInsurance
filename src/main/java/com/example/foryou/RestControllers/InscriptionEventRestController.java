package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.InscriptionEvent;
import com.example.foryou.DAO.Entities.User;
import com.example.foryou.Services.Interfaces.IinscriptionEventService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/InscriptionEvent")
@CrossOrigin("*")
public class InscriptionEventRestController {
    private IinscriptionEventService iinscriptionEventService;


    @GetMapping("/afficherInscriptionEventById/{Id}")
    public InscriptionEvent afficherCreditById(@PathVariable int Id) {

        return iinscriptionEventService.selectById(Id);
    }

    @GetMapping("/afficherAllInscriptionEvents")
    public List<InscriptionEvent> afficherAll() {
        return iinscriptionEventService.selectAll();
    }

    @PostMapping("/ajouterInscriptionEvent")
    public InscriptionEvent addInscriptionEvent(@RequestBody InscriptionEvent InscriptionEvent) {
        return iinscriptionEventService.add(InscriptionEvent);

    }

    @PostMapping("ajouterAllInscriptionEvents")
    public ResponseEntity<String> addAllInscriptionEvent(@RequestBody List<InscriptionEvent> InscriptionEventList) {
        iinscriptionEventService.addAll(InscriptionEventList);
        return ResponseEntity.ok("Added successfully.");
    }

    @PutMapping("/ModifierInscriptionEvent")
    public ResponseEntity<String> editInscriptionEvent(@RequestBody InscriptionEvent InscriptionEvent) {
        iinscriptionEventService.edit(InscriptionEvent);
        return ResponseEntity.ok("Edited successfully.");
    }

    @DeleteMapping("SupprimerInscriptionEvent")
    public ResponseEntity<String> deleteInscriptionEvent(@RequestBody InscriptionEvent InscriptionEvent) {
        iinscriptionEventService.delete(InscriptionEvent);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerInscriptionEventById")
    public ResponseEntity<String> supprimerInscriptionEventsById(@RequestParam int InscriptionEventId) {
        iinscriptionEventService.deleteById(InscriptionEventId);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAllInscriptionEvents")
    public ResponseEntity<String> supprimerAllInscriptionEvents(@RequestBody List<InscriptionEvent> InscriptionEventList) {
        iinscriptionEventService.deleteAll(InscriptionEventList);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAll")
    public ResponseEntity<String> SupprimerAll() {
        iinscriptionEventService.deleteAll();
        return ResponseEntity.ok("Deleted successfully.");
    }


    @PostMapping("/ajouterParticpantInscriptionEvent/{EventId}")
    public InscriptionEvent addInscriptionEvent(@PathVariable int EventId) {
        InscriptionEvent i = new InscriptionEvent();
        return iinscriptionEventService.assignParticipantandEventToInscription(EventId, i);


    }

    @Autowired
    private JavaMailSender javaMailSender;

    @Scheduled(cron = "0 0 0 * * *")
    @PostMapping("/EventReminder")
    public void SendEventReminder() {
        List<InscriptionEvent> list = (List<InscriptionEvent>) iinscriptionEventService.selectAll();
        for (InscriptionEvent i : list) {
            LocalDate today = LocalDate.now();

            if (0 == (i.getEvent().getDate_Event().compareTo(today.plusDays(7)))) {
                System.out.println(i.getParticipant().getEmail());
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(i.getParticipant().getEmail());
                message.setSubject("Reminder");
                message.setText(i.getEvent().getName_Event() + "event will happen in 7 days ");


                javaMailSender.send(message);
            }
        }
    }

    @PostMapping("/SendFeedback")
    //@Scheduled(cron = "0 0 0 * * *")
    public void sendfeedback() throws MessagingException {
        List<InscriptionEvent> list = (List<InscriptionEvent>) iinscriptionEventService.selectAll();
        for (InscriptionEvent i : list) {
            LocalDate today = LocalDate.now();

           // if (0 == (i.getEvent().getDate_Event().compareTo(today.minusDays(1)))) {
                String feedbackLink = "http://localhost:8081/foryou/InscriptionEvent/SetFeedback/"+i.getInscription_event_id()+"/"; //

                System.out.println(i.getParticipant().getEmail());
                //SimpleMailMessage message = new SimpleMailMessage();
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(i.getParticipant().getEmail());
            helper.setSubject("Reminder");
            String htmlMsg = "<html>" +
                    "<body style=\"background-image: url('https://img.rawpixel.com/s3fs-private/rawpixel_images/website_content/v546batch3-mynt-31-badgewatercolor_1.jpg?w=800&dpr=1&fit=default&crop=default&q=65&vib=3&con=3&usm=15&bg=F4F4F3&ixlib=js-2.2.1&s=e1ee6e84fa2c584170bab2733ea5a1b0'); background-size: cover;\">" +
                    "<div style=\"background-color: rgba(0, 0, 0, 0.5); padding: 20px;\">" +
                    "<h1 style=\"color: white;\">Feedback Request for " + i.getEvent().getName_Event() + "</h1>" +
                    "<p style=\"color: white;\">Dear Participant,</p>" +
                    "<p style=\"color: white;\">We hope you enjoyed attending our event, " + i.getEvent().getName_Event() + ". We would greatly appreciate it if you could take a few minutes to provide us with your feedback. Your input will help us improve future events and make them even better!</p>" +
                    "<p style=\"color: white;\">Please click on the following button to complete the feedback form:</p>" +
                    "<a href=\"" + feedbackLink + "\"><button style=\"background-color: #008CBA; color: white; padding: 14px 25px; border: none; cursor: pointer; border-radius: 5px;\">Click Here to Provide Feedback</button></a>" +
                    "<p style=\"color: white;\">Thank you for your time and valuable feedback.</p>" +
                    "<p style=\"color: white;\">Best regards,</p>" +
                    "<p style=\"color: white;\">The Event Team</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
            helper.setText( htmlMsg,true);



                javaMailSender.send(message);
            //}
        }
    }

    @PostMapping("/SetFeedback/{inscriptionid}/{mark}")
    public void setfeedback(@PathVariable int inscriptionid, @PathVariable int mark) {
        iinscriptionEventService.assignMarkEvent(mark, inscriptionid);
    }
}
