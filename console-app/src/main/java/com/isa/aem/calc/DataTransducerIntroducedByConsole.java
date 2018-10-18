package com.isa.aem.calc;

import com.isa.aem.CurrencyRepository;
import com.isa.aem.tools.ConsolePrinter;
import com.isa.aem.tools.ConsoleReader;
import com.isa.aem.tools.DataValidator;

import java.time.LocalDate;

public class DataTransducerIntroducedByConsole {

    private ConsoleReader consoleReader = new ConsoleReader();
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private DataValidator dataService = new DataValidator();
    private CurrencyRepository currencyRepository = new CurrencyRepository();

    private static final String CORRECT_DATE_FORM = "^\\d{4}(0?[1-9]|1[012])(0?[1-9]|[12][0-9]|3[01])$";
    private static final String ONLY_EIGHT_DIGITS = "[0-9]{8}";
    private static final String ONLY_DIGITS = "[0-9 .]+";
    private String preparedDate;

    public String getCurrencySelectedByUserOfConsole(String commandForUser) {
        String commandByUser = null;
        do {
            commandByUser = consoleReader.getString(commandForUser).trim().toUpperCase();
            if (currencyRepository.containsCurrencyNameInCurrencyList(commandByUser)) {
                return commandByUser;

            } else {
                System.out.println(consolePrinter.currencyUnexist());
            }
        } while (!currencyRepository.containsCurrencyNameInCurrencyList(commandByUser));
        return null;
    }

    public double amountGivenByUserService() {
        String strValue;
        String replace;
        Double amountGivenByUser = null;
        do {
            strValue = consoleReader.getString(consolePrinter.enterAmount()).trim();
            replace = strValue.replace(',', '.');
            if (checkIfItIsANumber(replace)) {
                amountGivenByUser = Double.parseDouble(replace);
            } else {
                System.out.println(consolePrinter.numberUnexist());
            }
        } while (!checkIfItIsANumber(replace));
        return amountGivenByUser;
    }

    public LocalDate dataService() {
        String strDate;
        LocalDate date = null;
        do {
            strDate = consoleReader.getString(consolePrinter.enterDate()).trim();
            preparedDate = dataService.preparingDateRemovingPunctuationMarks(strDate);
            if (checkIfItIsCorrectDataFormatAndOnlyEightDigits(preparedDate)) {
                date = dataService.dataParse(preparedDate);
            } else {
                System.out.println(consolePrinter.wrongDate());
            }
        } while (!(checkIfItIsCorrectDataFormatAndOnlyEightDigits(preparedDate)));
        return date;
    }

    public boolean checkIfItIsANumber(String strValue) {
        return strValue.matches(ONLY_DIGITS);
    }

    public boolean checkIfCurrencyNameSelectedByUserContainsGivenDate(
            String firstNameOfCurrency, String secondNameOfCurrency, LocalDate date) {
        return currencyRepository.checkIfExistCurrencyWithGivenDate(firstNameOfCurrency, date)
                && currencyRepository.checkIfExistCurrencyWithGivenDate(secondNameOfCurrency, date);
    }

    public Boolean checkIfItIsCorrectDataFormatAndOnlyEightDigits(String preparedDate) {
        return (preparedDate.matches(CORRECT_DATE_FORM)
                && preparedDate.matches(ONLY_EIGHT_DIGITS));
    }
}
