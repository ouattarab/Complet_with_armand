package com.cwa.crudspringboot.serviceImpl;

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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private PersonRequestDTO personRequestDTO;
    private Person person1, person2;

    @BeforeEach
    void setUp() {
        // Créer des objets de test
        person1 = Person.builder()
                .name("Alice")
                .city("Paris")
                .phoneNumber("0123456789")
                .email("alice@example.com")
                .age(25L)
                .sequence(1000L)
                .build();

        person2 = Person.builder()
                .name("Bob")
                .city("Lyon")
                .phoneNumber("0987654321")
                .email("bob@example.com")
                .age(30L)
                .sequence(1000L)
                .build();

        // Créer un DTO avec une liste de personnes
        personRequestDTO = new PersonRequestDTO();
        personRequestDTO.setPersons(Arrays.asList(
                new PersonDTO("Alice", "Paris", "0123456789", "alice@example.com", 25L),
                new PersonDTO("Bob", "Lyon", "0987654321", "bob@example.com", 30L)
        ));
    }

    @Test
    void savePersons_ShouldSaveUniquePersons() {
        // Mock : Simuler que ces personnes ne sont pas encore en base
        when(personRepository.findByPhoneNumberAndSequence("0123456789", 1000L)).thenReturn(Optional.empty());
        when(personRepository.findByPhoneNumberAndSequence("0987654321", 1000L)).thenReturn(Optional.empty());

        // Mock : Simuler la sauvegarde des personnes
        when(personRepository.saveAll(anyList())).thenReturn(Arrays.asList(person1, person2));

        // Appel de la méthode à tester
        List<Person> savedPersons = personService.savePersons(personRequestDTO);

        // Vérifications
        assertNotNull(savedPersons);
        assertEquals(2, savedPersons.size());
        assertEquals("Alice", savedPersons.get(0).getName());
        assertEquals("Bob", savedPersons.get(1).getName());

        // Vérifier que `saveAll` a été appelé une seule fois
        verify(personRepository, times(1)).saveAll(anyList());
    }

    @Test
    void savePersons_ShouldNotSaveDuplicates() {
        // Simuler qu'un des utilisateurs existe déjà
        when(personRepository.findByPhoneNumberAndSequence("0123456789", 1000L))
                .thenReturn(Optional.of(person1)); // Déjà existant
        when(personRepository.findByPhoneNumberAndSequence("0987654321", 1000L))
                .thenReturn(Optional.empty()); // Pas encore en base

        // Mock : Simuler la sauvegarde de Bob uniquement
        when(personRepository.saveAll(anyList())).thenReturn(List.of(person2));

        // Appel de la méthode à tester
        List<Person> savedPersons = personService.savePersons(personRequestDTO);

        // Vérifications
        assertNotNull(savedPersons);
        assertEquals(1, savedPersons.size()); // Un seul doit être sauvegardé (Bob)
        assertEquals("Bob", savedPersons.get(0).getName());

        // Vérifier que `saveAll` a été appelé une seule fois
        verify(personRepository, times(1)).saveAll(anyList());
    }
}
