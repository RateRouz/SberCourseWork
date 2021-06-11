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
import servingwebcontent.service.CounterpartiesService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CounterpartiesController {

    @Autowired
    private DictionaryCounterpartyRepository dictionaryCounterpartyRepo;
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("entities", dictionaryCounterpartyRepo.findAll());
        return "/main";
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody @Valid CounterpartiesDto counterparties) {
        try {
            DictionaryCounterparty counterpartiesDto = modelMapper.map(counterparties, DictionaryCounterparty.class);
            dictionaryCounterpartyRepo.save(counterpartiesDto);
            return ResponseEntity.ok("Пользователь успешно сохранен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка " + e.getMessage());
        }
    }

    @PostMapping(value = "/filter")
    public ResponseEntity filter(@RequestBody String text) {
        try {
            CounterpartiesService counterpartiesService = new CounterpartiesService();
            long inn = -1L;
            if (counterpartiesService.tryParseLong(text)) {
                inn = Long.parseLong(text);
            }
            List<DictionaryCounterparty> sdp = dictionaryCounterpartyRepo.findByNameOrInn(text, inn);
            return ResponseEntity.ok(sdp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не найдено");
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
