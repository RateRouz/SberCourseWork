package servingwebcontent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import servingwebcontent.entity.DictionaryCounterparty;
import servingwebcontent.repository.DictionaryCounterpartyRepository;
import servingwebcontent.service.CounterpartiesService;

import java.util.List;

@Controller
public class CounterpartiesController {

    @Autowired
    private DictionaryCounterpartyRepository dictionaryCounterpartyRepo;


    @GetMapping
    public ResponseEntity getAll() {
        try {
            return ResponseEntity.ok(dictionaryCounterpartyRepo.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка " + e);
        }
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody DictionaryCounterparty dictionaryCounterparty) {
        try {
            dictionaryCounterpartyRepo.save(dictionaryCounterparty);
            return ResponseEntity.ok("Пользователь успешно сохранен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка " + e);
        }
    }

    @PostMapping(value = "/filter")
    public ResponseEntity filter(@RequestBody String text) {
        try {
            CounterpartiesService counterpartiesService = new CounterpartiesService();
            int inn = -1;
            if (counterpartiesService.tryParseInt(text)) {
                inn = Integer.parseInt(text);
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
            return ResponseEntity.ok("Удалился");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Нет такого");
        }
    }
}
