package servingwebcontent.dto;

import lombok.Data;
import servingwebcontent.annotation.CounterpartyValidation;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@CounterpartyValidation
public class CounterpartiesDto {
    @Id
    private Long id;

    @NotBlank(message = "Имя не задано")
    private String name;

    @NotBlank(message = "Не введен ИНН")
    private String inn;

    private String kpp;

    @NotBlank(message = "Номер счёта не задан")
    private String accountNumber;

    @NotBlank(message = "Не введн БИК")
    private String bankBik;
}
