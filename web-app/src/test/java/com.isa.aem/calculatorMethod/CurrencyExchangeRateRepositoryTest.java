package com.isa.aem.calculatorMethod;

import com.isa.aem.CurrencyRepository;
import com.isa.aem.FileContentReader;
import com.isa.aem.LoadCurrencyNameCountryFlags;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class CurrencyExchangeRateRepositoryTest {
    private CurrencyRepository currencyRepository = new CurrencyRepository();
    private FileContentReader fileContentReader = new FileContentReader();
    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Before
    public void init() {
        fileContentReader.readFile();
        fileContentReader.addPLNToListCurrency();
        LoadCurrencyNameCountryFlags loadCurrencyNameCountryFlags = new LoadCurrencyNameCountryFlags();
    }

    @Test
    @DisplayName("Should return date before or equal Today's date")
    public void returnsMaxDateForCorrectCurrencyName() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currency = "PLN";

        // act
        LocalDate result = currencyExchangeRateRepository.getMaxDateForSelectedCurrency(currency);

        // assert
        assertThat(result)
                .isBeforeOrEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Should return date before or equal Today's date when currency is in File even though it is provided with a lowercase letter")
    public void returnsDateBeforeTodayForCorrectCurrencyWhenIsProvidedInLowerCase() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currency = "pln";

        // act
        LocalDate result = currencyExchangeRateRepository.getMaxDateForSelectedCurrency(currency);

        // assert
        assertThat(result)
                .isBeforeOrEqualTo(LocalDate.now());
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("Should throw exception NoSuchElementException if currency is not on the list")
    public void throwsExceptionNoSuchElementExceptionWhenCurrencyNotInTheFileForMethodGetMaxDateForSelectedCurrency() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();

        // arrange
        String currency = "AA";

        // act
        currencyExchangeRateRepository.getMaxDateForSelectedCurrency(currency);

    }

    @Test
    @DisplayName("Should return date after 1900-01-01 if currency is in File")
    public void returnsDateAfter19000101ForCorrectCurrencyName() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currency = "PLN";

        // act
        LocalDate result = currencyExchangeRateRepository.getMinDateForSelectedCurrency(currency);

        // assert
        assertThat(result)
                .isAfterOrEqualTo(LocalDate.of(1900, 01, 01));
    }

    @Test
    @DisplayName("Should return date after 1900-01-01  when currency is in File even though it is provided with a lowercase letter")
    public void returnsDateAfter19000101ForCorrectCurrencyWhenIsProvidedInLowerCase() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currency = "pln";

        // act
        LocalDate result = currencyExchangeRateRepository.getMinDateForSelectedCurrency(currency);

        // assert
        assertThat(result)
                .isAfterOrEqualTo(LocalDate.of(1900, 01, 01));
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("Should throw exception NoSuchElementException if currency is not in the File")
    public void throwsExceptionNoSuchElementExceptionWhenCurrencyNotInTheFileForMethodGetMinDateForSelectedCurrency() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currency = "AA";

        // act
        currencyExchangeRateRepository.getMinDateForSelectedCurrency(currency);

    }

    @Test
    @DisplayName("Should return length>0 if currency is in file")
    public void returnsNotEmptyListWhenCurrencyIsInTheFile() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currency = "pln";

        // act
        Boolean result = currencyExchangeRateRepository.getRangeOfSelectedCurrency(currency).length() > 0;

        // assert
        assertEquals(true, result);
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("Should throw exception NoSuchElementException if currency not in file")
    public void throwsExceptionNoSuchElementExceptionWhenCurrencyIsNotInTheFileForMethodGetRangeOfSelectedCurrency() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currency = "aa";

        // act
        currencyExchangeRateRepository.getRangeOfSelectedCurrency(currency);
    }

    @Test
    @DisplayName("Should return exchange value if currencyWant and CurrencyHave is not empty and is in File")
    public void returnsExchangeValueWhenCurrencyAndDateIsCorrect() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currencyHave = "EUR";
        String currencyWant = "PLN";
        LocalDate dateMax = LocalDate.of(2018, 07, 27);

        // act
        BigDecimal result = currencyExchangeRateRepository.getExchangeValue(currencyHave, currencyWant, dateMax);

        // assert
        assertEquals(BigDecimal.valueOf(4.2945), result);
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("Should throw exception NoSuchElementException if currencyWant is not in file")
    public void throwsExceptionNoSuchElementExceptionWhenCurrencyWantIsNotInTheFile() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currencyHave = "EUR";
        String currencyWant = "aa";
        LocalDate dateMax = LocalDate.of(2018, 07, 27);

        // act
        currencyExchangeRateRepository.getExchangeValue(currencyHave, currencyWant, dateMax);
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("Should throw exception NoSuchElementException if currencyHave is not in file")
    public void throwsExceptionNoSuchElementExceptionWhenCurrencyHaveIsNotInTheFile() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currencyHave = "aa";
        String currencyWant = "PLN";
        LocalDate dateMax = LocalDate.of(2018, 07, 27);

        // act
        currencyExchangeRateRepository.getExchangeValue(currencyHave, currencyWant, dateMax);
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Should throw exception NullPointerException if LocalDate is empty")
    public void throwsExceptionNullPointerExceptionWhenLocalDateIsEmpty() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        String currencyHave = "EUR";
        String currencyWant = "PLN";
        LocalDate dateMax = null;

        // act
        currencyExchangeRateRepository.getExchangeValue(currencyHave, currencyWant, dateMax);
    }

    @Test
    @DisplayName("Should return list with a size more than zero")
    public void returnsListWithSizeMoreThanZeroWhenDateIsInThFile() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        LocalDate dateMax = LocalDate.of(2018, 07, 27);

        // act
        Boolean result = currencyExchangeRateRepository.getSingleCurrencyWithMaxDate(dateMax).size() > 0;

        // assert
        assertEquals(true, result);
    }

    @Test
    @DisplayName("Should return empty list when LocalDate doesn't exist")
    public void returnsEmptyListWhenDateIsNotInThFile() {
        currencyExchangeRateRepository = new CurrencyExchangeRateRepository();
        // arrange
        LocalDate dateMax = LocalDate.of(100, 07, 27);

        // act
        Boolean result = currencyExchangeRateRepository.getSingleCurrencyWithMaxDate(dateMax).isEmpty();

        // assert
        assertEquals(true, result);
    }
}
