package net.jackietran.springboottestingdemo.taxcalculator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class TaxCalculatorService {

    private TaxInfoRepository taxInfoRepository;
    private UserRepository userRepository;

    private final double LOW_INCOME_TAX = 0.05;
    private final double HIGH_INCOME_TAX = 0.1;

    public TaxInfo calculateAndSaveTaxForUser(String userId) {
        Objects.requireNonNull(userId, "User ID cannot be null");

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new IllegalArgumentException(String.format("User ID %s doesn't exist", userId));
        }

        TaxInfo taxInfo = user.get().getTaxInfo();

        if (taxInfo == null) {
            throw new IllegalArgumentException(String.format("Tax Info of user id %s doesn't exist", userId));
        }

        taxInfo.setTax(calculateTax(taxInfo.getIncome()));

        return taxInfoRepository.save(taxInfo);
    }

    private double calculateTax(double income) {
        if (income > 1000)
            return income * HIGH_INCOME_TAX;
        return income * LOW_INCOME_TAX;
    }
}
