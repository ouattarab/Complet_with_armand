package com.cwa.crudspringboot.repository;

import com.cwa.crudspringboot.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test") // Utiliser la configuration de test avec Oracle
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private Person person1, person2;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll(); // Nettoyage avant chaque test

        person1 = new Person(null, "Alice", "Paris", "0123456789", "alice@mail.com", 30L, 100L);
        person2 = new Person(null, "Bob", "Lyon", "0987654321", "bob@mail.com", 40L, 101L);
    }

    /**
     * 🔹 Test : Vérifier `findAll()`
     */
    @Test
    void shouldGetAllPersons() {
        personRepository.saveAll(List.of(person1, person2)); // Ajouter les personnes

        List<Person> persons = personRepository.findAll();

        assertThat(persons).hasSize(2);
        assertThat(persons.get(0).getName()).isEqualTo("Alice");
        assertThat(persons.get(1).getName()).isEqualTo("Bob");
    }

    /**
     * 🔹 Test : Vérifier `findById()`
     */
    @Test
    void shouldGetPersonById() {
        Person savedPerson = personRepository.save(person1);
        Optional<Person> person = personRepository.findById(savedPerson.getId());

        assertThat(person).isPresent();
        assertThat(person.get().getName()).isEqualTo("Alice");
    }

    /**
     * 🔹 Test : Vérifier `findByPhoneNumberAndSequence()`
     */
    @Test
    void shouldFindPersonByPhoneNumberAndSequence() {
        personRepository.save(person1);

        Optional<Person> person = personRepository.findByPhoneNumberAndSequence("0123456789", 100L);

        assertThat(person).isPresent();
        assertThat(person.get().getName()).isEqualTo("Alice");
    }

    /**
     * 🔹 Test : Vérifier `save()`
     */
    @Test
    void shouldSavePerson() {
        Person savedPerson = personRepository.save(person1);

        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo("Alice");
    }

    /**
     * 🔹 Test : Vérifier `saveAll()`
     */
    @Test
    void shouldSaveAllPersons() {
        List<Person> savedPersons = personRepository.saveAll(List.of(person1, person2));

        assertThat(savedPersons).hasSize(2);
        assertThat(savedPersons.get(0).getId()).isNotNull();
        assertThat(savedPersons.get(1).getId()).isNotNull();
    }

    /**
     * 🔹 Test : Vérifier `deleteById()`
     */
    @Test
    void shouldDeletePerson() {
        Person savedPerson = personRepository.save(person1);
        personRepository.deleteById(savedPerson.getId());

        Optional<Person> deletedPerson = personRepository.findById(savedPerson.getId());

        assertThat(deletedPerson).isEmpty();
    }

    /**
     * 🔹 Test : Vérifier `getNextSequenceValue()` (avec séquence en base)
     */
    @Test
    @Sql(statements = "CREATE SEQUENCE PERSON_SEQ START WITH 1 INCREMENT BY 1", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldGetNextSequenceValue() {
        Long sequenceValue = personRepository.getNextSequenceValue();

        assertThat(sequenceValue).isNotNull();
        assertThat(sequenceValue).isGreaterThan(0);
    }
}
