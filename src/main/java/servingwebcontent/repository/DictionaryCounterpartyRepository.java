package servingwebcontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import servingwebcontent.entity.DictionaryCounterparty;

import java.util.List;

public interface DictionaryCounterpartyRepository extends JpaRepository<DictionaryCounterparty, Long> {
    List<DictionaryCounterparty> findByNameOrAccountNumberAndBankBik(@Param("name") String name,@Param("accountNumber") String accountNumber,@Param("bankBik") String String);

    List<DictionaryCounterparty> findByNameLike(@Param("name") String name);

    List<DictionaryCounterparty> findByAccountNumberAndBankBik(@Param("accountNumber") String accountNumber,@Param("bankBik") String String);
}
