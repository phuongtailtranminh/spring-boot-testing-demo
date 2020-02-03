package net.jackietran.springboottestingdemo.unittest.taxcalculator;

import net.jackietran.springboottestingdemo.taxcalculator.TaxCalculatorService;
import net.jackietran.springboottestingdemo.unittest.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class TaxCalculatorServiceTest extends BaseUnitTest {

    private TaxCalculatorService service;

    @BeforeEach
    void setUp() {
        service = new TaxCalculatorService();
    }

    @ParameterizedTest
    @CsvSource(value = {"900:45", "789:39.45", "0:0", "1100:110", "1234:123.4"}, delimiter = ':')
    void calculateTax_withAllPossiblesIncome_returnTax(double income, double expectedTax) {
        double tax = service.calculateTax(income);

        assertThat(tax).isEqualTo(expectedTax);
    }

}
