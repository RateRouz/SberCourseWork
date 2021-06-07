package servingwebcontent.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "counterparties")
@Getter
@Setter
public class DictionaryCounterparty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "inn")
    private Integer inn;

    @Column(name = "kpp")
    private Integer kpp;

    @Column(name = "account_number")
    private Integer account_number;

    @Column(name = "bank_bik")
    private Integer bank_bik;

    public DictionaryCounterparty() {
    }

    public DictionaryCounterparty(/*Long id,*/ String name, Integer inn, Integer kpp, Integer account_number, Integer bank_bik) {
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
        this.account_number = account_number;
        this.bank_bik = bank_bik;
    }
}
