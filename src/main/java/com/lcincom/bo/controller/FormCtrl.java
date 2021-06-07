package com.lcincom.bo.controller;

import com.lcincom.bo.model.Form;
import com.lcincom.bo.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/form")
public class FormCtrl {

    @Autowired
    FormRepository formRepository;

    @GetMapping
    Flux<Form> getForms() {
        return formRepository.findByAllAndIsActive();
    }

    @PostMapping
    Mono<Form> save(@RequestBody Form form) {
        return formRepository.save(form);
    }

    @PostMapping("/save-list")
    Flux<Form> saveAll(@RequestBody List<Form> forms) {
        return formRepository.saveAll(forms);
    }


}
