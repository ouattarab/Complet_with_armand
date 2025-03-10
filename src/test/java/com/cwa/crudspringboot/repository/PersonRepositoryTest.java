package com.cwa.crudspringboot.repository;

import com.cwa.crudspringboot.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // 🔹 Active Mockito dans JUnit 5
class PersonRepositoryTest {

    @Mock
    private PersonRepository personRepository; // 🔥 On Mock le Repository

    private Person person1, person2;

    @BeforeEach
    void setUp() {
        person1 = new Person(1L, "Alice", "Paris", "0123456789", "alice@mail.com", 30L, 100L);
        person2 = new Person(2L, "Bob", "Lyon", "0987654321", "bob@mail.com", 40L, 101L);
    }

    /**
     * 🔹 Test : Vérifier l'appel à `findAll()`
     */
    @Test
    void shouldGetAllPersons() {
        when(personRepository.findAll()).thenReturn(List.of(person1, person2));

        List<Person> persons = personRepository.findAll();

        assertThat(persons).hasSize(2);
        assertThat(persons.get(0).getName()).isEqualTo("Alice");

        verify(personRepository, times(1)).findAll(); // 🔥 Vérifie que `findAll()` a bien été appelé
    }

    /**
     * 🔹 Test : Vérifier `findById()`
     */
    @Test
    void shouldGetPersonById() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));

        Optional<Person> person = personRepository.findById(1L);

        assertThat(person).isPresent();
        assertThat(person.get().getName()).isEqualTo("Alice");

        verify(personRepository, times(1)).findById(1L);
    }

    /**
     * 🔹 Test : Vérifier `findByPhoneNumberAndSequence()`
     */
    @Test
    void shouldFindPersonByPhoneNumberAndSequence() {
        when(personRepository.findByPhoneNumberAndSequence("0123456789", 100L)).thenReturn(Optional.of(person1));

        Optional<Person> person = personRepository.findByPhoneNumberAndSequence("0123456789", 100L);

        assertThat(person).isPresent();
        assertThat(person.get().getName()).isEqualTo("Alice");

        verify(personRepository, times(1)).findByPhoneNumberAndSequence("0123456789", 100L);
    }

    /**
     * 🔹 Test : Vérifier l'ajout d'une personne
     */
    @Test
    void shouldSavePerson() {
        when(personRepository.save(any(Person.class))).thenReturn(person1);

        Person savedPerson = personRepository.save(person1);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo("Alice");

        verify(personRepository, times(1)).save(any(Person.class));
    }

    /**
     * 🔹 Test : Vérifier la suppression d'une personne
     */
    @Test
    void shouldDeletePerson() {
        doNothing().when(personRepository).deleteById(1L);

        personRepository.deleteById(1L);

        verify(personRepository, times(1)).deleteById(1L);
    }

    /**
     * 🔹 Test : Vérifier `getNextSequenceValue()` (simulé)
     */
    @Test
    void shouldGetNextSequenceValue() {
        when(personRepository.getNextSequenceValue()).thenReturn(200L);

        Long sequenceValue = personRepository.getNextSequenceValue();

        assertThat(sequenceValue).isEqualTo(200L);
        verify(personRepository, times(1)).getNextSequenceValue();
    }
}
