package servingwebcontent.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import servingwebcontent.dto.CounterpartiesDto;
import servingwebcontent.entity.DictionaryCounterparty;
import servingwebcontent.repository.DictionaryCounterpartyRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CounterpartiesController {

    @Autowired
    private DictionaryCounterpartyRepository dictionaryCounterpartyRepo;
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public String getAll(@RequestParam(required = false) String name_filter, @RequestParam(required = false) Integer bankBik_filter, @RequestParam(required = false) String accountNumber_filter, Model model) {
        try {
            model.addAttribute("name_filter", name_filter);
            model.addAttribute("bankBik_filter", bankBik_filter);
            model.addAttribute("accountNumber_filter", accountNumber_filter);

            if (!((bankBik_filter == null && (accountNumber_filter == null || accountNumber_filter.isEmpty())) || (bankBik_filter != null && !accountNumber_filter.isEmpty()))) {
                model.addAttribute("errorMessage", "БИК и номер счета парные");
            } else {
                List<DictionaryCounterparty> filteringList = null;
                boolean hasNameParam = name_filter != null && !name_filter.isEmpty();
                if (hasNameParam){
                    name_filter = "%" + name_filter + "%";
                }

                boolean hasBankBik = bankBik_filter != null;

                if (hasNameParam && hasBankBik) {
                    filteringList = dictionaryCounterpartyRepo.findByNameOrAccountNumberAndBankBik(name_filter, accountNumber_filter, bankBik_filter);
                } else if (hasNameParam) {
                    filteringList = dictionaryCounterpartyRepo.findByNameLike(name_filter);
                } else if (hasBankBik) {
                    filteringList = dictionaryCounterpartyRepo.findByAccountNumberAndBankBik(accountNumber_filter, bankBik_filter);
                }
                else{
                    filteringList = dictionaryCounterpartyRepo.findAll();
                }

                model.addAttribute("entities", filteringList);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "/main";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody @Valid CounterpartiesDto counterparty) {
        try {
            DictionaryCounterparty counterpartiesDto = modelMapper.map(counterparty, DictionaryCounterparty.class);
            dictionaryCounterpartyRepo.save(counterpartiesDto);
            return ResponseEntity.ok("Пользователь успешно сохранен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            dictionaryCounterpartyRepo.deleteById(id);
            return ResponseEntity.ok("Запись с id = " + id + " удалена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Запись с id = " + id + " не найдена");
        }
    }
}
