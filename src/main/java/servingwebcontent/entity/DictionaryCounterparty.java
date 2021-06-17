package servingwebcontent.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String name;

    @Size(min = 10, max = 12)
    @Column(name = "inn", unique = true)
    @NotNull
    private String inn;

    @Size(max = 9)
    @Column(name = "kpp")
    private String kpp;

    @Size(min = 20, max = 20)
    @Column(name = "account_number", unique = true)
    @NotNull
    private String accountNumber;

    @Size(min = 9, max = 9)
    @Column(name = "bank_bik")
    @NotNull
    private String bankBik;
}
