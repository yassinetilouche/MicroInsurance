package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.Product;
import com.example.foryou.DAO.Entities.Subproduct;
import com.example.foryou.Services.Interfaces.IsubproductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Subproducts")
public class SubproductRestController {

    private IsubproductService iSubproductService;

    @GetMapping("/afficherSubroductById")
    public Subproduct afficherSubproductById(@PathVariable int subproductId ) {

        return iSubproductService.selectById(subproductId);
    }
    @GetMapping("/afficherAllSubproducts")
    public List<Subproduct> afficherAll(){
        return iSubproductService.selectAll();
    }
    @PostMapping("/ajouterSubproduct")
    public ResponseEntity<String> addSubproduct(@RequestBody Subproduct subproduct){
        iSubproductService.add(subproduct);
        return ResponseEntity.ok("Added successfully.");
    }
    @PostMapping("ajouterAllSubproducts")
    public ResponseEntity<String> addAllSubproduct(@RequestBody List<Subproduct> subproductList){
        iSubproductService.addAll(subproductList);
        return ResponseEntity.ok("Added successfully.");
    }
    @PutMapping("/ModifierSubproduct")
    public ResponseEntity<String> editSubproduct(@RequestBody Subproduct subproduct){
        iSubproductService.edit(subproduct);
        return ResponseEntity.ok("Edited successfully.");
    }
    @DeleteMapping("SupprimerSubproduct")
    public ResponseEntity<String> deleteSubproduct(@RequestBody Subproduct subproduct){
        iSubproductService.delete(subproduct);
        return ResponseEntity.ok("Deleted successfully.");
    }
    @DeleteMapping("/SupprimerSubproductById")
    public ResponseEntity<String> supprimerSubproductsById(@RequestParam int subproductId){
        iSubproductService.deleteById(subproductId);
        return ResponseEntity.ok("Deleted successfully.");
    }
    @DeleteMapping("/SupprimerAllSubproducts")
    public ResponseEntity<String> supprimerAllSubproducts(@RequestBody List<Subproduct> subproductList){
        iSubproductService.deleteAll(subproductList);
        return ResponseEntity.ok("Deleted successfully.");
    }
    @DeleteMapping("/SupprimerAll")
    public ResponseEntity<String> SupprimerAll() {
        iSubproductService.deleteAll();
        return ResponseEntity.ok("Deleted successfully.");
    }
}
