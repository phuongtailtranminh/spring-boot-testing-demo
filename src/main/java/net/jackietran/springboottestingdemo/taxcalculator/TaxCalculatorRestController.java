package net.jackietran.springboottestingdemo.taxcalculator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/taxcalculators")
public class TaxCalculatorRestController {

    private final TaxCalculatorService taxCalculatorService;

    @PostMapping("/calculateTax/{userId}")
    public ResponseEntity calculateTax(@PathVariable("userId") String userId) {
        try {
            log.info("Start Calculating tax for user id {}", userId);
            TaxInfo taxInfo = taxCalculatorService.calculateAndSaveTaxForUser(userId);
            log.info("End Calculating tax for user id {}", userId);
            return ResponseEntity.ok(taxInfo);
        } catch (IllegalArgumentException e) {
            log.error("Processing error", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Processing error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
