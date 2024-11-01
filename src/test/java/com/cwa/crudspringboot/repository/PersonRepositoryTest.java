package com.cwa.crudspringboot.repository;

import com.cwa.crudspringboot.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void shouldGetAllPersons() {
        //arrange dans le datasql
        //act
        List<Person> person = personRepository.findAll();
        //assert
        assertEquals(3, person.size()) ;
        assertEquals("jhon Doe", person.get(0).getName()) ;
    }

    @Test
    void shouldGetPersonById() {
        Person person = personRepository.findById(1L).get() ;

        assertEquals("jhon Doe", person.getName()) ;
        assertEquals("Paris", person.getCity());
        assertEquals("123-456-7890", person.getPhoneNumber());
    }

    @Test
    void shouldCheckName () {
        Person person = personRepository.findById(1L).get() ;
        assertEquals("jhon Doe", person.getName()) ;
    }

    @Test
    void shouldSavePerson() {
        Person person = new Person();
        person.setName("Bakary");
        person.setCity("Paris");
        person.setPhoneNumber("123-456-7890");

        Person savedPerson = personRepository.save(person);

        assertNotNull(savedPerson.getId());
        assertEquals("Bakary", savedPerson.getName());
        assertEquals("Paris", savedPerson.getCity());
        assertEquals("123-456-7890", savedPerson.getPhoneNumber());
    }

    @Test
    void shouldpdatePerson() {
        Person person = personRepository.findById(1L).get() ;
        person.setCity("Lille");

        assertEquals("Lille", person.getCity());
    }

    @Test
    void shouldDeletePerson() {
       personRepository.deleteById(1L) ;

       Optional<Person> person = personRepository.findById(1L) ;
        assertFalse(person.isPresent()) ;
    }


}