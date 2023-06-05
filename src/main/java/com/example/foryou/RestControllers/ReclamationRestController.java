package com.example.foryou.RestControllers;

//import com.example.foryou.DAO.Entities.Etat;
import com.example.foryou.DAO.Entities.*;
import com.example.foryou.DAO.Repositories.ForBiddenWordRepository;
//import com.example.foryou.DAO.Repositories.ReclamationRepository;
import com.example.foryou.Services.Interfaces.IReclamationService;
/*import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;*/
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/Reclamation")
public class ReclamationRestController {
    private IReclamationService iReclamationService;
    private ForBiddenWordRepository forBiddenWordRepository;
    private JavaMailSender mailSender;
    @PostMapping("/ajouterReclamation")
    public ResponseEntity<String> addReclamation(@RequestBody Reclamation reclamation/*, @RequestParam int UserId*/) {
        //User client;
        //client = i
        checkDescription(reclamation.getDetails());
        iReclamationService.add(reclamation);
        return ResponseEntity.ok("Added successfully.");
    }

    @PostMapping("ajouterAllReclamations")
    public ResponseEntity<String> addAllReclamation(@RequestBody List<Reclamation> reclamationList) {
        iReclamationService.addAll(reclamationList);
        return ResponseEntity.ok("Added successfully.");
    }

    @PutMapping("/ModifierReclamation")
    public ResponseEntity<String> editReclamation(@RequestBody Reclamation reclamation) {
        iReclamationService.edit(reclamation);
        return ResponseEntity.ok("Edited successfully.");
    }

    @DeleteMapping("SupprimerReclamation")
    public ResponseEntity<String> delete(@RequestBody Reclamation reclamation) {
        iReclamationService.delete(reclamation);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerReclamationById")
    public ResponseEntity<String> supprimerReclamationById(@RequestParam int ReclamationId) {
        iReclamationService.deleteById(ReclamationId);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAllReclamations")
    public ResponseEntity<String> supprimerAllReclamations(@RequestBody List<Reclamation> ReclamationList) {
        iReclamationService.deleteAll(ReclamationList);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAll")
    public ResponseEntity<String> SupprimerAll() {
        iReclamationService.deleteAll();
        return ResponseEntity.ok("Deleted successfully.");
    }

    @PostMapping("/{reclamation_id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable("reclamation_id") int id, @RequestBody String status) {
        Reclamation reclamation = iReclamationService.selectById(id);
        if (reclamation != null) {
            Reclamation updatedReclamation = reclamation;
            if (updatedReclamation.getStatus() == Status.EN_ATTENTE && status.equals("en_cours")) {
                updatedReclamation.setStatus(Status.EN_COURS);
                iReclamationService.edit(updatedReclamation);
                return new ResponseEntity<>("Reclamation status updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid status update", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Reclamation not found", HttpStatus.NOT_FOUND);
        }
    }

    private void checkDescription(String details) {
        // Récupérer la liste des mots interdits dans la base de données
        List<String> forBiddenWords = forBiddenWordRepository.findAll().stream()
                .map(ForbiddenWord::getWord)
                .collect(Collectors.toList());
        // Vérifier si la description contient des mots interdits
        for (String word : forBiddenWords) {
            if (details.toLowerCase().contains(word.toLowerCase())) {
                throw new BadRequestException("La description contient des mots interdits.");
            }
        }
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    // @Scheduled(cron = "0 0 0 * * *") // tous les jours à minuit

    @GetMapping("/Mail")
    //@Scheduled(cron = "0 */2 * * * *") // tous les 2 minutes
    public void sendReminderEmails() {
        List<Reclamation> reclamations = iReclamationService.findByStatus(Status.EN_ATTENTE); // récupérer les réclamations en attente
        LocalDate todayMinusThreeDays = LocalDate.now().minusDays(3);
        for (Reclamation reclamation : reclamations) {
            LocalDate creationDate = reclamation.getCreationDate();

            if (creationDate.isBefore(todayMinusThreeDays))  { // vérifier si la réclamation est en attente depuis plus de 3 jours
                // Récupérer l'adresse mail de l'admin
                String to = "youssef.benyahia@esprit.tn";
                // Construire le message
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("noreply@example.com");
                message.setTo(to);
                message.setSubject("Rappel : Réclamations en attente");
                message.setText("Bonjour,\n\nVous avez des réclamations en attente depuis plus de 7 jours. Veuillez les traiter dès que possible.\n\nCordialement,\nL'équipe de support.");
                // Envoyer le message
                mailSender.send(message);
                System.out.println("rappel envoyé");
            }
            else {
                System.out.println("il n y a pas de réclamations en attente depuis plus de 3 jours");
            }
        }
    }
    @PutMapping("/{reclamation_id}/status/en_cours")
    public ResponseEntity<String> updateStatusToEnCours(@PathVariable("reclamation_id") int id) {
        Reclamation reclamation = iReclamationService.selectById(id);
        if (reclamation != null) {
            Reclamation updatedReclamation = reclamation;
            if (updatedReclamation.getStatus() == Status.EN_ATTENTE) {
                updatedReclamation.setStatus(Status.EN_COURS);
                iReclamationService.edit(updatedReclamation);
                return new ResponseEntity<>("Reclamation status updated to EN_COURS", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid status update", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Reclamation not found", HttpStatus.NOT_FOUND);
        }
    }

    public static Etat getEmotionalState(String details) {
        // Define a dictionary of words and their corresponding sentiment scores
        Map<String, Integer> sentimentDictionary = new HashMap<>();
        sentimentDictionary.put("happy", 2);
        sentimentDictionary.put("good", 2);
        sentimentDictionary.put("awesome", 3);
        sentimentDictionary.put("joy", 3);
        sentimentDictionary.put("sad", -2);
        sentimentDictionary.put("bad", -2);
        sentimentDictionary.put("terrible", -3);
        sentimentDictionary.put("grief", -3);
        sentimentDictionary.put("neutral", 0);

        // Tokenize the input string and calculate its overall sentiment score
        int sentimentScore = 0;
        String[] tokens = details.toLowerCase().split("\\s+");
        for (String token : tokens) {
            if (sentimentDictionary.containsKey(token)) {
                sentimentScore += sentimentDictionary.get(token);
            }
        }

        // Map the overall sentiment score to an Etat enum value
        if (sentimentScore > 2) {
            return Etat.VERY_HAPPY;
        } else if (sentimentScore > 0) {
            return Etat.HAPPY;
        } else if (sentimentScore < -2) {
            return Etat.VERY_ANGRY;
        } else if (sentimentScore < 0) {
            return Etat.ANGRY;
        } else {
            return Etat.NEUTRAL;
        }
    }

    @PutMapping("/{reclamation_id}/etat")
    public ResponseEntity<Reclamation> updateEmotionalState(@PathVariable int id) {
        System.out.println("11111");
        Reclamation reclamation = iReclamationService.selectById(id);
        System.out.println("222222");
        if (reclamation == null) {
            System.out.println("Réclamation introuvable");
            return ResponseEntity.notFound().build();

        }
        System.out.println("3333333");
        String details = reclamation.getDetails();
        System.out.println("444444");
        Etat etat = getEmotionalState(details);
        System.out.println("555555");
        reclamation.setEtat(etat);
        System.out.println("666666");
        iReclamationService.edit(reclamation);
        System.out.println("777777");
        return ResponseEntity.ok(reclamation);
    }
}