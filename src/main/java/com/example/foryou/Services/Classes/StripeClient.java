package com.example.foryou.Services.Classes;
import com.example.foryou.DAO.Entities.Contracts;
import com.example.foryou.DAO.Entities.User;
import com.example.foryou.Services.Interfaces.IContractService;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {
    IContractService iContractService;
    @Autowired
    StripeClient() {
        Stripe.apiKey = "sk_test_51MpURUImJwa6U6kHEm7AejSf8aO1S1aDPdGEdlXF9YBDgfdhWMBjsWDt2IpzDeTw4LeBhFjkLmItSUNItUSkXdKj00vPQw6te9";
    }
    public Charge chargeCreditCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
    public Customer createCustomer(String token ,String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }
    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard =     Customer.retrieve(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}

