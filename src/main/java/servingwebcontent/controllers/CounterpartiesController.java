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
    public String getAll(@RequestParam(required = false) String nameFilter, @RequestParam(required = false) String bankBikFilter, @RequestParam(required = false) String accountNumberFilter, Model model) {
        try {
            model.addAttribute("nameFilter", nameFilter);
            model.addAttribute("bankBikFilter", bankBikFilter);
            model.addAttribute("accountNumberFilter", accountNumberFilter);
            boolean isBankBikFilter = bankBikFilter != null && !bankBikFilter.isEmpty();
            boolean isAccountNumberFilter = accountNumberFilter != null && !accountNumberFilter.isEmpty();

            if (!((isBankBikFilter && isAccountNumberFilter) || (!isBankBikFilter && !isAccountNumberFilter))) {
                model.addAttribute("errorMessage", "БИК и номер счета парные");
            } else {
                List<DictionaryCounterparty> filteringList;
                boolean hasNameParam = nameFilter != null && !nameFilter.isEmpty();
                if (hasNameParam) {
                    nameFilter = "%" + nameFilter + "%";
                }

                if (hasNameParam && isBankBikFilter) {
                    filteringList = dictionaryCounterpartyRepo.findByNameOrAccountNumberAndBankBik(nameFilter, accountNumberFilter, bankBikFilter);
                } else if (hasNameParam) {
                    filteringList = dictionaryCounterpartyRepo.findByNameLike(nameFilter);
                } else if (isBankBikFilter) {
                    filteringList = dictionaryCounterpartyRepo.findByAccountNumberAndBankBik(accountNumberFilter, bankBikFilter);
                } else {
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
            String expMessage = e.getMessage();
            Throwable cause1 = e.getCause();
            if (cause1 != null) {
                expMessage += cause1.getMessage();

                Throwable cause2 = cause1.getCause();
                if (cause2 != null) {
                    expMessage += cause2.getMessage();
                }
            }

            return ResponseEntity.badRequest().body("Произошла ошибка " + expMessage);
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
