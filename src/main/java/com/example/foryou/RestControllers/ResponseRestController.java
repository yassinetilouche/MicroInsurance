package com.example.foryou.RestControllers;
import com.example.foryou.DAO.Entities.Reclamation;
import com.example.foryou.DAO.Entities.Response;
import com.example.foryou.Services.Interfaces.IReclamationService;
import com.example.foryou.Services.Interfaces.IResponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Response")
public class ResponseRestController {
    private IResponseService iResponseService;
    private IReclamationService iReclamationService;
    @PostMapping("/ajouterResponse")
    public ResponseEntity<String> addResponse(@RequestBody Response response, @RequestParam int reclamationId) {
        Reclamation reclamation;
        reclamation = iReclamationService.selectById(reclamationId);
        response.setReclamation(reclamation);
        iResponseService.addResp(response);
        return ResponseEntity.ok("Added successfully.");
    }

    @PostMapping("ajouterAllResponses")
    public ResponseEntity<String> addAllResponse(@RequestBody List<Response> ResponseList) {
        iResponseService.addAll(ResponseList);
        return ResponseEntity.ok("Added successfully.");
    }

    @PutMapping("/ModifierResponse")
    public ResponseEntity<String> editResponse(@RequestBody Response response) {
        iResponseService.editResp(response);
        return ResponseEntity.ok("Edited successfully.");
    }

    @DeleteMapping("SupprimerResponse")
    public ResponseEntity<String> delete(@RequestBody Response response) {
        iResponseService.deleteResp(response);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerResponseById")
    public ResponseEntity<String> supprimerResponseById(@RequestParam int ResponseId) {
        iResponseService.deleteRespById(ResponseId);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAllResponses")
    public ResponseEntity<String> supprimerAllResponses(@RequestBody List<Response> ResponseList) {
        iResponseService.deleteRespAll(ResponseList);
        return ResponseEntity.ok("Deleted successfully.");
    }

    @DeleteMapping("/SupprimerAll")
    public ResponseEntity<String> SupprimerAll() {
        iResponseService.deleteRespAll();
        return ResponseEntity.ok("Deleted successfully.");
    }
}

