package com.cwa.crudspringboot.serviceImpl;

import com.cwa.crudspringboot.dao.PersonDao;
import com.cwa.crudspringboot.dto.PersonDTO;
import com.cwa.crudspringboot.dto.PersonRequestDTO;
import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.repository.PersonRepository;
import com.cwa.crudspringboot.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonDao personDao;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person1, person2;
    private PersonDTO personDTO1, personDTO2;
    private PersonRequestDTO personRequestDTO;

    @BeforeEach
    void setUp() {
        person1 = new Person(1L, "Alice", "Paris", "0123456789", "alice@mail.com", 30L, 100L);
        person2 = new Person(2L, "Bob", "Lyon", "0987654321", "bob@mail.com", 40L, 101L);

        personDTO1 = new PersonDTO("Alice", "Paris", "0123456789", "alice@mail.com", 30L);
        personDTO2 = new PersonDTO("Bob", "Lyon", "0987654321", "bob@mail.com", 40L);

        personRequestDTO = new PersonRequestDTO();
        personRequestDTO.setPersons(List.of(personDTO1, personDTO2));
    }

    /**
     * 🔹 Test : Vérifier `savePersons()`
     */
    @Test
    void shouldSavePersons() {
        when(personRepository.getNextSequenceValue()).thenReturn(200L);
        when(personRepository.saveAll(anyList())).thenReturn(List.of(person1, person2));

        List<Person> savedPersons = personService.savePersons(personRequestDTO);

        assertThat(savedPersons).hasSize(2);
        assertThat(savedPersons.get(0).getName()).isEqualTo("Alice");

        verify(personRepository, times(2)).saveAll(anyList());
        verify(personDao, times(1)).executerMergeEmployeesProcedure();
    }

    /**
     * 🔹 Test : Vérifier `savePersonss()`
     */
    @Test
    void shouldSavePersonss() {
        when(personRepository.getNextSequenceValue()).thenReturn(200L);
        when(personRepository.saveAll(anyList())).thenReturn(List.of(person1, person2));

        List<PersonDTO> savedPersons = personService.savePersonss(personRequestDTO);

        assertThat(savedPersons).hasSize(2);
        assertThat(savedPersons.get(0).getName()).isEqualTo("Alice");

        verify(personRepository, times(1)).saveAll(anyList());
    }

    /**
     * 🔹 Test : Vérifier `getAllPersons()`
     */
    @Test
    void shouldGetAllPersons() {
        when(personRepository.findAll()).thenReturn(List.of(person1, person2));

        List<PersonDTO> persons = personService.getAllPersons();

        assertThat(persons).hasSize(2);
        assertThat(persons.get(1).getName()).isEqualTo("Bob");

        verify(personRepository, times(1)).findAll();
    }

    /**
     * 🔹 Test : Vérifier `getPersonById()`
     */
    @Test
    void shouldGetPersonById() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));

        PersonDTO person = personService.getPersonById(1L);

        assertThat(person.getName()).isEqualTo("Alice");

        verify(personRepository, times(1)).findById(1L);
    }

    /**
     * 🔹 Test : Vérifier `getPersonById()` avec une personne inexistante
     */
    @Test
    void shouldThrowExceptionWhenPersonNotFound() {
        when(personRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            personService.getPersonById(99L);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found with id: 99");
    }

    /**
     * 🔹 Test : Vérifier `updatePerson()`
     */
    @Test
    void shouldUpdatePerson() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));
        when(personRepository.save(any(Person.class))).thenReturn(person1);

        PersonDTO updatedPerson = personService.updatePerson(1L, personDTO1);

        assertThat(updatedPerson.getName()).isEqualTo("Alice");
        assertThat(updatedPerson.getCity()).isEqualTo("Paris");

        verify(personRepository, times(1)).save(any(Person.class));
    }

    /**
     * 🔹 Test : Vérifier `deletePerson()`
     */
    @Test
    void shouldDeletePerson() {
        when(personRepository.existsById(1L)).thenReturn(true);
        doNothing().when(personRepository).deleteById(1L);

        personService.deletePerson(1L);

        verify(personRepository, times(1)).deleteById(1L);
    }

    /**
     * 🔹 Test : Vérifier `deletePerson()` avec une personne inexistante
     */
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingPerson() {
        when(personRepository.existsById(99L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            personService.deletePerson(99L);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found with id: 99");
    }
}
