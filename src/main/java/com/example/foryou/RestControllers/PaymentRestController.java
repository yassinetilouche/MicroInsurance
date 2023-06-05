package com.example.foryou.RestControllers;

import com.example.foryou.DAO.Entities.Contracts;
import com.example.foryou.DAO.Repositories.ContractRepository;
import com.example.foryou.Services.Classes.StripeClient;
import com.example.foryou.Services.Interfaces.IContractService;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentRestController {
    private StripeClient stripeClient;
    @Autowired
    PaymentRestController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }
    @PostMapping("/charge")
    public Charge chargeCard(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        Double amount = Double.parseDouble(request.getHeader("amount"));
        return this.stripeClient.chargeCreditCard(token, amount);
    }

}
