package net.jackietran.springboottestingdemo.taxcalculator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TaxCalculatorService {

    private TaxInfoRepository taxInfoRepository;
    private UserRepository userRepository;

    private final double LOW_INCOME_TAX = 0.05;
    private final double HIGH_INCOME_TAX = 0.1;

    public void calculateAndSaveTaxForUser(String userId) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
            TaxInfo taxInfo = user.getTaxInfo();
            if (taxInfo == null) {
                throw new IllegalArgumentException(String.format("Tax Info of user id %s doesn't exist", userId));
            }

            double tax = calculateTax(taxInfo.getIncome());
            taxInfo.setTax(tax);

            taxInfoRepository.save(taxInfo);
        }, () -> {
            throw new IllegalArgumentException(String.format("User ID %s doesn't exist", userId));
        });
    }

    private double calculateTax(double income) {
        if (income > 1000)
            return income * HIGH_INCOME_TAX;
        return income * LOW_INCOME_TAX;
    }
}
