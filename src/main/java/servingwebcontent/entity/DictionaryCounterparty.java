package servingwebcontent.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "counterparties")
@Getter
@Setter
@Validated
public class DictionaryCounterparty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "inn")
    private Long inn;

    @Min(100000000)
    @Max(999999999)
    @Column(name = "kpp")
    private Integer kpp;

    @Size(min = 20)
    @Size(max = 20)
    @Column(name = "account_number")
    private String accountNumber;

    @Min(100000000)
    @Max(999999999)
    @Column(name = "bank_bik")
    private Integer bankBik;
}
