package com.example.foryou.RestControllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Simulator")
public class SimulateurRestController {

    // Estimated lifespan and estimated lifetime KM for each car type
    private static final int ECONOMY_LIFESPAN = 12;
    private static final int ECONOMY_LIFETIME_KM = 250_000;
    private static final int MIDRANGE_LIFESPAN = 15;
    private static final int MIDRANGE_LIFETIME_KM = 300_000;
    private static final int LUXURY_LIFESPAN = 20;
    private static final int LUXURY_LIFETIME_KM = 400_000;

    // New car value for each car type
  /*  private static final double ECONOMY_NEW_CAR_VALUE = 20_000;
    private static final double MIDRANGE_NEW_CAR_VALUE = 30_000;
    private static final double LUXURY_NEW_CAR_VALUE = 50_000;*/

    @GetMapping("/value")
    public Double getCarValue(
            @RequestParam int age,
            @RequestParam int km,
            @RequestParam CarType carType,
            @RequestParam double newCarValue
    ) {
        double estimatedLifespan, estimatedLifetimeKm;
        switch (carType) {
            case ECONOMY:
                estimatedLifespan = ECONOMY_LIFESPAN;
                estimatedLifetimeKm = ECONOMY_LIFETIME_KM;

                break;
            case MIDRANGE:
                estimatedLifespan = MIDRANGE_LIFESPAN;
                estimatedLifetimeKm = MIDRANGE_LIFETIME_KM;

                break;
            case LUXURY:
                estimatedLifespan = LUXURY_LIFESPAN;
                estimatedLifetimeKm = LUXURY_LIFETIME_KM;

                break;
            default:
                return null;
        }

        double value = newCarValue * (1 - ((double) age / estimatedLifespan)) * (1 - ((double) km / estimatedLifetimeKm));
        return value;
    }

    public enum CarType {
        ECONOMY,
        MIDRANGE,
        LUXURY
    }
    @GetMapping("/calculatePremium")
    public double calculatePremium(
                                   @RequestParam int ageDriver,
                                   @RequestParam int km,
                                   @RequestParam CarType carType,
                                   @RequestParam double newCarValue,
                                   @RequestParam String insuranceType) {
        double premium = 0.0;
        double carValue=getCarValue(ageDriver,km,carType,newCarValue);
        double basePremium = carValue * 0.05;

            switch (insuranceType.toLowerCase()) {
                case "third-party":
                    premium = basePremium * 1.2;
                    break;
                case "third-party fire and theft":
                    premium = basePremium * 1.4;
                    break;
                case "comprehensive":
                    premium = basePremium * 1.6;
                    break;
                case "premium":
                    premium = basePremium * 2.0;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid insurance type: " + insuranceType);
            }


        return premium;
    }
    @GetMapping("/calculate-premium")
    public double calculatePremium(@RequestParam("landValue") double landValue,
                                   @RequestParam("expectedIncome") double expectedIncome,
                                   @RequestParam("insuranceType") String insuranceType) {
        double premium = 0.0;


        if (insuranceType.equals(" ")) {
            if (landValue >= 100000 && expectedIncome >= 50000) {
                premium = 0.05 * (landValue + expectedIncome);
            } else {
                premium = 0.03 * (landValue + expectedIncome);
            }
        }
        // livestock insurance:
        else if (insuranceType.equals("livestock")) {
            if (expectedIncome >= 20000) {
                premium = 0.07 * (expectedIncome);
            } else {
                premium = 0.05 * (expectedIncome);
            }
        }
        // crop insurance:
        else if (insuranceType.equals("crop")) {
            if (expectedIncome >= 50000) {
                premium = 0.06 * (expectedIncome);
            } else {
                premium = 0.04 * (expectedIncome);
            }
        }

        return premium;
    }
}
