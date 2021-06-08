package servingwebcontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servingwebcontent.entity.DictionaryCounterparty;

import java.util.List;

public interface DictionaryCounterpartyRepository extends JpaRepository<DictionaryCounterparty, Long> {
    List<DictionaryCounterparty> findByNameOrInn(String name, Long inn);
}
