package servingwebcontent.dto;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import servingwebcontent.annotation.CounterpartyValidation;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@CounterpartyValidation
public class CounterpartiesDto {
    @Id
    private Long id;

    //@UniqueElements
    @NotBlank(message = "Имя не задано")
    private String name;

    private Long inn;

    private Integer kpp;

    @NotBlank(message = "Номер счёта не задан")
    private String accountNumber;

    private Integer bankBik;
}
