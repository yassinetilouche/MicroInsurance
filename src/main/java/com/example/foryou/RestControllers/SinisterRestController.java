package com.example.foryou.RestControllers;
import com.example.foryou.DAO.Entities.Sinister;
import com.example.foryou.Services.Interfaces.ISinisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Sinister")
public class SinisterRestController {
    private ISinisterService isinisterService;
    @PostMapping("/ajouterSinister")
    public ResponseEntity<String> addSinister(@RequestBody Sinister sinister){

        isinisterService.add(sinister);
        return ResponseEntity.ok("Added successfully !");

    }
    @PostMapping("ajouterAllSinister")
    public ResponseEntity<String> addAllSinister(@RequestBody List<Sinister> list){
        isinisterService.addAll(list);
        return ResponseEntity.ok("AddedAll successfully !");

    }
    @PutMapping("/ModifierSinister")
    public ResponseEntity<String> editSiniter(@RequestBody Sinister sinister){
        isinisterService.edit(sinister);
        return ResponseEntity.ok("Updated successfully !");

    }
    @DeleteMapping("SupprimerSinister")
    public ResponseEntity<String> deleteSinister(@RequestBody Sinister sinister){

        isinisterService.delete(sinister);
        return ResponseEntity.ok("Deleted successfully !");
    }
    @GetMapping("/afficherSinistre")
    public List<Sinister> afficher(){return isinisterService.selectAll();}

    @GetMapping("/afficherSinistreAvecId/{id}")
    public Sinister afficherOfferAvecId(@PathVariable int id)
    {
        return isinisterService.selectById(id);
    }

    @DeleteMapping("/deleteSinistreById")
    public ResponseEntity<String> deleteOffreById(@RequestBody int id)
    {
        isinisterService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully !");
    }
}
