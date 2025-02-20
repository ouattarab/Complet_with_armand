package com.cwa.crudspringboot.controller;

import com.cwa.crudspringboot.dto.PersonRequestDTO;
import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Autorise Angular à appeler cette API
public class PersonController {

    private final PersonService personService;

    @PostMapping("/batch")
    public ResponseEntity<List<Person>> savePersons(@RequestBody PersonRequestDTO personRequestDTO) {
        List<Person> savedPersons = personService.savePersons(personRequestDTO);
        return new ResponseEntity<>(savedPersons, HttpStatus.CREATED);
    }
}
