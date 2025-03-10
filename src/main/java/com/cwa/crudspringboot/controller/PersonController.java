package com.cwa.crudspringboot.controller;

import com.cwa.crudspringboot.dto.PersonDTO;
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

    /**
     * 🔹 Ajouter plusieurs personnes
     */
    @PostMapping("/save")
    public ResponseEntity<List<PersonDTO>> savePersonss(@RequestBody PersonRequestDTO personRequestDTO) {
        List<PersonDTO> savedPersons = personService.savePersonss(personRequestDTO);
        return ResponseEntity.ok(savedPersons);
    }

    /**
     * 🔹 Récupérer toutes les personnes
     */
    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> persons = personService.getAllPersons();
        return ResponseEntity.ok(persons);
    }


    /**
     * 🔹 Récupérer une personne par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        PersonDTO personDTO = personService.getPersonById(id);
        return ResponseEntity.ok(personDTO);
    }

    /**
     * 🔹 Mettre à jour une personne
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPerson = personService.updatePerson(id, personDTO);
        return ResponseEntity.ok(updatedPerson);
    }

    /**
     * 🔹 Supprimer une personne par son ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/batch")
    public ResponseEntity<List<Person>> savePersons(@RequestBody PersonRequestDTO personRequestDTO) {
        List<Person> savedPersons = personService.savePersons(personRequestDTO);
        return new ResponseEntity<>(savedPersons, HttpStatus.CREATED);
    }
}
