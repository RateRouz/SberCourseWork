package servingwebcontent.dto;

import lombok.*;
import servingwebcontent.annotation.CounterpartyValidation;

import javax.validation.constraints.NotBlank;

@Data
@CounterpartyValidation(message = "12345678")
public class CounterpartiesDto {
    @NotBlank(message = "Имя не задано")
    private String name;

    private Long inn;

    private Integer kpp;

    @NotBlank(message = "Номер счёта не задан")
    private String accountNumber;

    private Integer bankBik;
}
