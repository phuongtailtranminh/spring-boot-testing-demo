package net.jackietran.springboottestingdemo.integrationtest.taxcalculator;

import lombok.extern.slf4j.Slf4j;
import net.jackietran.springboottestingdemo.integrationtest.RestAwareIT;
import net.jackietran.springboottestingdemo.taxcalculator.TaxCalculatorService;
import net.jackietran.springboottestingdemo.taxcalculator.TaxInfo;
import net.jackietran.springboottestingdemo.taxcalculator.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
public class TaxCalculatorRestControllerIT extends RestAwareIT {

    @MockBean
    private TaxCalculatorService taxCalculatorService;

    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String RESOURCE = "/taxcalculators/calculateTax";
    private static final String RESOURCE_BY_USER_ID = RESOURCE + "/" + USER_ID;

    @Test
    void taxCalculatorRestController_userNotExist_returnBadRequest() {
        when(taxCalculatorService.calculateAndSaveTaxForUser(anyString()))
                .thenThrow(new IllegalArgumentException(String.format("User ID %s doesn't exist", USER_ID)));

        ResponseEntity<Void> response = restTemplate.postForEntity(RESOURCE_BY_USER_ID, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(null);
    }

    @Test
    void taxCalculatorRestController_userExist_return200() {
        TaxInfo taxInfo = createTaxInfo();
        when(taxCalculatorService.calculateAndSaveTaxForUser(anyString())).thenReturn(taxInfo);

        ResponseEntity<TaxInfo> response = restTemplate.postForEntity(RESOURCE_BY_USER_ID, null, TaxInfo.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(taxInfo.getId());
        assertThat(response.getBody().getIncome()).isEqualTo(taxInfo.getIncome());
        assertThat(response.getBody().getTax()).isEqualTo(taxInfo.getTax());
        assertThat(response.getBody().getUser()).isEqualTo(taxInfo.getUser());
    }

    @Test
    void taxCalculatorRestController_internalError_return500() {
        when(taxCalculatorService.calculateAndSaveTaxForUser(anyString()))
                .thenThrow(new RuntimeException("Internal Error"));

        ResponseEntity<Void> response = restTemplate.postForEntity(RESOURCE_BY_USER_ID, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo(null);
    }

    private TaxInfo createTaxInfo() {
        TaxInfo taxInfo = new TaxInfo();
        taxInfo.setId(UUID.randomUUID().toString());
        taxInfo.setIncome(3000);
        taxInfo.setTax(300);
        taxInfo.setUser(new User());
        return taxInfo;
    }

}
