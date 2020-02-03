package net.jackietran.springboottestingdemo.taxcalculator;

import org.springframework.stereotype.Service;

@Service
public class TaxCalculatorService {

    private final double LOW_INCOME_TAX = 0.05;
    private final double HIGH_INCOME_TAX = 0.1;

    public double calculateTax(double income) {
        if (income > 1000)
            return income * HIGH_INCOME_TAX;
        return income * LOW_INCOME_TAX;
    }

}
