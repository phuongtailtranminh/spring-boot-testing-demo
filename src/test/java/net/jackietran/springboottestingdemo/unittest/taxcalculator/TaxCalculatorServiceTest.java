package net.jackietran.springboottestingdemo.unittest.taxcalculator;

import net.jackietran.springboottestingdemo.taxcalculator.*;
import net.jackietran.springboottestingdemo.unittest.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TaxCalculatorServiceTest extends BaseUnitTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TaxInfoRepository taxInfoRepository;
    @Captor
    private ArgumentCaptor<TaxInfo> taxInfoArgumentCaptor;

    private TaxCalculatorService sut;

    private final double HIGH_INCOME = 3000;
    private final double HIGH_INCOME_TAX = 300;
    private final double LOW_INCOME = 500;
    private final double LOW_INCOME_TAX = 25;
    private String userId;
    private String taxInfoId;

    @BeforeEach
    void setUp() {
        sut = new TaxCalculatorService(taxInfoRepository, userRepository);
        userId = UUID.randomUUID().toString();
        taxInfoId = UUID.randomUUID().toString();
    }

    @Test
    void taxCalculatorService_userWithHighIncomeAndTaxInfoExist_taxInfoIsCalculateAndSaved() {
        Optional<User> highIncomeUser = createHighIncomeUser();
        when(userRepository.findById(userId)).thenReturn(highIncomeUser);

        sut.calculateAndSaveTaxForUser(userId);

        verify(taxInfoRepository, times(1)).save(taxInfoArgumentCaptor.capture());

        assertThat(taxInfoArgumentCaptor.getValue().getId()).isEqualTo(taxInfoId);
        assertThat(taxInfoArgumentCaptor.getValue().getIncome()).isEqualTo(HIGH_INCOME);
        assertThat(taxInfoArgumentCaptor.getValue().getTax()).isEqualTo(HIGH_INCOME_TAX);
        assertThat(taxInfoArgumentCaptor.getValue().getUser()).isEqualTo(highIncomeUser.get());
    }

    @Test
    void taxCalculatorService_userWithLowIncomeAndTaxInfoExist_taxInfoIsCalculateAndSaved() {
        Optional<User> lowIncomeUser = createLowIncomeUser();
        when(userRepository.findById(userId)).thenReturn(lowIncomeUser);

        sut.calculateAndSaveTaxForUser(userId);

        verify(taxInfoRepository, times(1)).save(taxInfoArgumentCaptor.capture());

        assertThat(taxInfoArgumentCaptor.getValue().getId()).isEqualTo(taxInfoId);
        assertThat(taxInfoArgumentCaptor.getValue().getIncome()).isEqualTo(LOW_INCOME);
        assertThat(taxInfoArgumentCaptor.getValue().getTax()).isEqualTo(LOW_INCOME_TAX);
        assertThat(taxInfoArgumentCaptor.getValue().getUser()).isEqualTo(lowIncomeUser.get());
    }

    @Test
    void taxCalculatorService_userNotExist_taxInfoIsNotCalculateAndSaved() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sut.calculateAndSaveTaxForUser(userId));

        assertThat(exception.getMessage()).isEqualTo(String.format("User ID %s doesn't exist", userId));
    }

    @Test
    void taxCalculatorService_userExistButTaxInfoIsMissing_taxInfoIsNotCalculateAndSaved() {
        Optional<User> lowIncomeUser = createLowIncomeUser();
        lowIncomeUser.get().setTaxInfo(null);
        when(userRepository.findById(userId)).thenReturn(lowIncomeUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> sut.calculateAndSaveTaxForUser(userId));

        assertThat(exception.getMessage()).isEqualTo(String.format("Tax Info of user id %s doesn't exist", userId));
    }

    private Optional<User> createHighIncomeUser() {
        User user = new User();
        user.setId(userId);
        user.setUsername("phuongtm");
        user.setPassword("n0p4ssw0rd");
        user.setTaxInfo(createTaxInfo(user, HIGH_INCOME));
        return Optional.of(user);
    }

    private Optional<User> createLowIncomeUser() {
        User user = new User();
        user.setId(userId);
        user.setUsername("phuongtm");
        user.setPassword("n0p4ssw0rd");
        user.setTaxInfo(createTaxInfo(user, LOW_INCOME));
        return Optional.of(user);
    }

    private TaxInfo createTaxInfo(User user, double income) {
        TaxInfo taxInfo = new TaxInfo();
        taxInfo.setId(taxInfoId);
        taxInfo.setIncome(income);
        taxInfo.setUser(user);
        return taxInfo;
    }

}
