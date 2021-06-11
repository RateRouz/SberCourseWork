package servingwebcontent.validation;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import servingwebcontent.annotation.CounterpartyValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CounterpartyValidator implements ConstraintValidator<CounterpartyValidation, Object> {
    private CounterpartyValidation innValidator;
    @Override
    public void initialize(CounterpartyValidation innValidator) {
        this.innValidator=innValidator;
    }

    @Override
    public boolean isValid(Object model, ConstraintValidatorContext constraintValidatorContext) throws NullPointerException {
        boolean isValid = true;

        try {
            String errorsMessage = "";

            String innString = Objects.requireNonNull(new BeanWrapperImpl(model).getPropertyValue("inn")).toString();
            if (!isInnValid(innString)) {
                errorsMessage += "Некорректный ИНН\r\n";
                isValid = false;
            }

            String bankBikString = Objects.requireNonNull(new BeanWrapperImpl(model).getPropertyValue("bankBik")).toString();
            if (!isBankBikValid(bankBikString)) {
                errorsMessage += "Некорректный БИК\r\n";
                isValid = false;
            } else {
                String accountNumberString = Objects.requireNonNull(new BeanWrapperImpl(model).getPropertyValue("accountNumber")).toString();
                if (!isAccountNumberValid(bankBikString, accountNumberString)) {
                    errorsMessage += "Некорректный номер счёта\r\n";
                    isValid = false;
                }
            }

            if (!isValid) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(errorsMessage).addConstraintViolation();
            }
        }
        catch (NullPointerException ex){
            isValid = false;

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Произошла ошибка во время валидации полей").addConstraintViolation();
        }

        return isValid;
    }

    private boolean isAccountNumberValid(String bankBikString, String accountNumberString) {
        if (accountNumberString.length() != 20) {
            return false;
        }

        int[] coefficients = new int[]{7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1};

        int[] accountNumberToCalculate;

        boolean isRKC = bankBikString.startsWith("00", 6);
        String stringAccountNumberToCalculate = isRKC ? "0" + bankBikString.substring(4, 6) : bankBikString.substring(6, 9);
        stringAccountNumberToCalculate += accountNumberString;

        accountNumberToCalculate = convertStringToIntArray(stringAccountNumberToCalculate);

        int controlSum = calculateControlSum(coefficients, accountNumberToCalculate);

        int controlNumber = controlSum % 10;

        return controlNumber == 0;
    }

    private int[] convertStringToIntArray(String stringArray) {
        int[] array = new int[stringArray.length()];

        for (int i = 0; i < stringArray.length(); i++) {
            array[i] = Integer.parseInt(stringArray.substring(i, i + 1));
        }

        return array;
    }

    private boolean isBankBikValid(String bankBikString) {
        if (bankBikString.length() != 9) {
            return false;
        }

        int credOrgNumber = Integer.parseInt(bankBikString.substring(6, 9));
        return credOrgNumber >= 50;
    }

    private boolean isInnValid(String innString) {
        if (innString.length() != 10 && innString.length() != 12) {
            return false;
        }

        boolean isLE = innString.length() == 10;

        int[] inn = convertStringToIntArray(innString);

        if (isLE) {
            int[] coefficients = new int[]{2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
            int controlSum = calculateControlSum(coefficients, inn);

            int controlNumber = calculateControlNumber(controlSum);

            return controlNumber == inn[9];
        } else {
            int[] firstCoefficients = new int[]{7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
            int firstControlSum = calculateControlSum(firstCoefficients, inn);
            int firstControlNumber = calculateControlNumber(firstControlSum);

            int[] secondCoefficients = new int[]{3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
            int secondControlSum = calculateControlSum(secondCoefficients, inn);
            int secondControlNumber = calculateControlNumber(secondControlSum);

            return firstControlNumber == inn[10] && secondControlNumber == inn[11];
        }
    }

    private int calculateControlSum(int[] coefficients, int[] inn) {
        int controlSum = 0;

        for (int i = 0; i < coefficients.length; i++) {
            controlSum += inn[i] * coefficients[i];
        }

        return controlSum;
    }

    private int calculateControlNumber(int controlSum) {
        int controlNumber = controlSum % 11;

        if (controlNumber > 9) {
            controlNumber %= 10;
        }

        return controlNumber;
    }
}
