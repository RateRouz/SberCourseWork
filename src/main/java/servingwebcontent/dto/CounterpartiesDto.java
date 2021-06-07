package servingwebcontent.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CounterpartiesDto {
    private String name;
    private Integer inn;
    private Integer kpp;
    private String account_number;
    private Integer bank_bik;

}
