package servingwebcontent.dto;

import lombok.*;
import servingwebcontent.annotation.CounterpartyValidation;

import javax.validation.constraints.NotBlank;

@Data
@CounterpartyValidation
public class CounterpartiesDto {
    @NotBlank
    private String name;

    private Long inn;

    private Integer kpp;

    @NotBlank
    private String accountNumber;

    private Integer bankBik;
}
