package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.Credit;
import com.example.foryou.DAO.Entities.PdfCredit;
import com.example.foryou.Services.Classes.CreditService;
import com.example.foryou.Services.Interfaces.ICreditService;
import com.lowagie.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Credit")
public class CreditRestController {
    private ICreditService iCreditService;
    private CreditService creditService;
    public boolean hasEightDigits(String type) {
        return (type.length() >= 3 );}
    //@EventListener(ApplicationReadyEvent.class)
    @PostMapping("/ajouterCredit")
    public ResponseEntity<?> add(@RequestBody Credit credit) throws MessagingException{
        boolean testType = hasEightDigits(credit.getType());
        boolean testDate = credit.getStartDate().before(credit.getEndtDate());
        boolean testAmount = credit.getAmount()>0.0;
        boolean testNbrA = credit.getNb_years()>0;

        if(!testType)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("the credit type must contain at least 3 characters");
        }
         if (!testDate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The start date must be before the end date");
        }
        else if (!testAmount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The amount cannot be negative");

        }
        else if (!testNbrA) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le nombre d'années doit etre superieur à 0 ");

        }
        else iCreditService.add(credit);
        return ResponseEntity.ok("Added successfully.");
    }


    @PostMapping("/ajouterAllCredits")
    public ResponseEntity<String> addAll(@RequestBody List<Credit> creditList){
        iCreditService.addAll(creditList);
        return ResponseEntity.ok("Added successfully.");
    }
    @PutMapping("/ModifierCredit")
    public ResponseEntity<String> edit(@RequestBody Credit credit){
        iCreditService.edit(credit);
        return ResponseEntity.ok("Edited successfully.");
    }
    @DeleteMapping("/SupprimerCredit")
    public ResponseEntity<String> delete(@RequestBody Credit credit){
        iCreditService.delete(credit);
        return ResponseEntity.ok("Deleted successfully.");
    }
    @DeleteMapping("/SupprimerCreditById")
    public ResponseEntity<String> deleteCreditById(@RequestParam int creditId){
        iCreditService.deleteById(creditId);
        return ResponseEntity.ok("Deleted successfully.");
    }
    @DeleteMapping("/SupprimerAllCredits")
    public ResponseEntity<String> supprimerAllContracts(@RequestBody List<Credit> creditList){
        iCreditService.deleteAll(creditList);
        return ResponseEntity.ok("Deleted successfully.");
    }
    @DeleteMapping("/SupprimerCredits")
    public ResponseEntity<String> SupprimerAll() {
        iCreditService.deleteAll();
        return ResponseEntity.ok("Deleted successfully.");
    }
    @GetMapping("/afficherCreditById")
    public Credit afficherCreditById(@PathVariable int creditId ) {

        return iCreditService.selectById(creditId);
    }
    @GetMapping("/afficherAllCredits")
    public List<Credit> afficherAll(){
        return iCreditService.selectAll();
    }
    @GetMapping("/Calcul1Credit/{id}")
    public void calcul1(@PathVariable int id){

        iCreditService.Calcul1(iCreditService.selectById(id));
    }
    @GetMapping("/Calcul2Credit/{id}")
    public void calcul2(@PathVariable int id){

        iCreditService.Calcul2(iCreditService.selectById(id));
    }
    @GetMapping("/Rentability")
    public void rentabilite(){
        iCreditService.Rentabilité();
    }
    @GetMapping("/Scoring")
    public List<Credit> scoring(){
        return iCreditService.Scoring();
    }
    @GetMapping("/Pdf/{id}")
    public void generatePdfFile(HttpServletResponse response,@PathVariable int id) throws DocumentException, IOException{
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=credit" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);
        Credit credit =creditService.selectById(id) ;
        PdfCredit generator = new PdfCredit();
        generator.generate(credit, response);
    }
    @GetMapping("/Status")
    public void Status(){
        iCreditService.StatusCredit();
    }
    @GetMapping("/Profit/{type}/{region}")
    public float Profit(@PathVariable String type,@PathVariable String region){
       return iCreditService.Profit(type,region);
    }



    }


