package net.jackietran.springboottestingdemo.integrationtest.taxcalculator;

import net.jackietran.springboottestingdemo.integrationtest.DbAwareIT;
import net.jackietran.springboottestingdemo.taxcalculator.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TaxCalculatorServiceIT extends DbAwareIT {

    @Autowired
    private TaxCalculatorService sut;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaxInfoRepository taxInfoRepository;

    private final double INCOME = 3000;
    private final double EXPECTED_TAX = 300;

    @Test
    public void taxCalculatorService_userIsExist_calculatedTaxIsSaved() {
        User user = userRepository.save(createUser());
        TaxInfo taxInfo = taxInfoRepository.save(createTaxInfo(user));

        sut.calculateAndSaveTaxForUser(user.getId());

        Optional<TaxInfo> updatedTaxInfo = taxInfoRepository.findById(taxInfo.getId());
        assertThat(updatedTaxInfo).isPresent();
        assertThat(updatedTaxInfo.get().getTax()).isEqualTo(EXPECTED_TAX);
        assertThat(updatedTaxInfo.get().getIncome()).isEqualTo(INCOME);
    }

    private User createUser() {
        User user = new User();
        user.setUsername("phuongtm");
        user.setPassword("n0p4ssw0rd");
        return user;
    }

    private TaxInfo createTaxInfo(User user) {
        TaxInfo taxInfo = new TaxInfo();
        taxInfo.setIncome(INCOME);
        taxInfo.setUser(user);
        return taxInfo;
    }

}
