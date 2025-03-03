package com.cwa.crudspringboot.service;

import com.cwa.crudspringboot.dto.PersonRequestDTO;
import com.cwa.crudspringboot.entity.Person;
import java.util.List;

public interface PersonService {
    List<Person> savePersons(PersonRequestDTO personRequestDTO);

    List<Person> getAllPersons();
}
