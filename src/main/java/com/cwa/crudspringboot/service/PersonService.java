package com.cwa.crudspringboot.service;

import com.cwa.crudspringboot.dto.PersonDTO;
import com.cwa.crudspringboot.dto.PersonRequestDTO;
import com.cwa.crudspringboot.entity.Person;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> savePersons(PersonRequestDTO personRequestDTO);

    List<PersonDTO> savePersonss(PersonRequestDTO personRequestDTO);

    List<PersonDTO> getAllPersons();

    PersonDTO getPersonById(Long id);

    PersonDTO updatePerson(Long id, PersonDTO personDTO);

    void deletePerson(Long id);
}
