package servingwebcontent.annotation;

import servingwebcontent.validation.CounterpartyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CounterpartyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CounterpartyValidation {
    String message() default "Не корректный ИНН";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
