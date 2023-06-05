package com.example.foryou.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.example.foryou.DAO.Entities.Product;
import com.example.foryou.Services.Interfaces.IproductService;
import java.lang.String;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Products")
public class ProductRestController {


    private IproductService iProductService;
    @GetMapping("/afficherProductById/{Id}")
    public Product afficherCreditById(@PathVariable int Id) {

        return iProductService.selectById(Id);
    }

    @GetMapping("/afficherAllProducts")
    public List<Product> afficherAll() {
        return iProductService.selectAll();
    }

    @PostMapping("/ajouterProduct")
    public Product addProduct(@RequestBody Product product) {
        return iProductService.add(product);

    }

    @PostMapping("ajouterAllProducts")
    public ResponseEntity<String> addAllProduct(@RequestBody List<Product> productList) {
        iProductService.addAll(productList);
        return ResponseEntity.ok("Added successfully.");
    }

    @PutMapping("/ModifierProduct")
    public ResponseEntity<String> editProduct(@RequestBody Product product) {
        iProductService.edit(product);
        return ResponseEntity.ok("Edited successfully.");
    }

    @DeleteMapping("SupprimerProduct")
    public ResponseEntity<String> deleteProduct(@RequestBody Product product) {
        iProductService.delete(product);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerProductById")
    public ResponseEntity<String> supprimerProductsById(@RequestParam int productId) {
        iProductService.deleteById(productId);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAllProducts")
    public ResponseEntity<String> supprimerAllProducts(@RequestBody List<Product> productList) {
        iProductService.deleteAll(productList);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAll")
    public ResponseEntity<String> SupprimerAll() {
        iProductService.deleteAll();
        return ResponseEntity.ok("Deleted successfully.");
    }
    @GetMapping("/calculer")
    public String calculer(@RequestParam String expression) {


        // Get JavaScript engine
        ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");

        try {
            // Evaluate the expression
            Object result = engine.eval(expression);

            System.out.println(expression + " = " + result);
            return expression + " = " + result;
        }
        catch (ScriptException e) {
            // Something went wrong
            e.printStackTrace();
            return "error";
        }

    }
}

