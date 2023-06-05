package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.Offer;
import com.example.foryou.Services.Interfaces.IOfferService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Offer")
public class OfferRestController
{
    private IOfferService iofferService;
    @PostMapping("/ajouterOffer")
    public ResponseEntity<String>  add(@RequestBody Offer offer){

        iofferService.add(offer);
        return ResponseEntity.ok("Added successfully !");

    }
    @PostMapping("ajouterAllOffer")
    public ResponseEntity<String> addAll(@RequestBody List<Offer> list){

        iofferService.addAll(list);
        return ResponseEntity.ok("AddedAll successfully !");

    }
    @PutMapping("/ModifierOffer")
    public ResponseEntity<String> edit(@RequestBody Offer offer){

        iofferService.edit(offer);
        return ResponseEntity.ok("Updated successfully !");

    }
    @DeleteMapping("SupprimerOffer")
    public ResponseEntity<String> delete(@RequestBody Offer offer){
        iofferService.delete(offer);
        return ResponseEntity.ok("Deleted successfully !");


    }
    @GetMapping("/afficherOffer")
    public List<Offer> afficher(){return iofferService.selectAll();}

    @GetMapping("/afficherOfferAvecId/{id}")
    public Offer afficherOfferAvecId(@PathVariable int id)
    {
        return iofferService.selectById(id);
    }

    @DeleteMapping("/deleteOffreById")
    public ResponseEntity<String> deleteOfferById(@RequestBody int id)
    {
        iofferService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully !");
    }
}

