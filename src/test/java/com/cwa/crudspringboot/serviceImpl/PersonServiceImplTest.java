package com.cwa.crudspringboot.serviceImpl;

import com.cwa.crudspringboot.dao.PersonDao;
import com.cwa.crudspringboot.dto.PersonDTO;
import com.cwa.crudspringboot.dto.PersonRequestDTO;
import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.repository.PersonRepository;
import com.cwa.crudspringboot.serviceImpl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // 🔹 Active Mockito dans JUnit 5
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonDao personDao;

    @InjectMocks
    private PersonServiceImpl personService;

    private PersonDTO personDTO1, personDTO2;
    private Person person1, person2;
    private PersonRequestDTO personRequestDTO;

    @BeforeEach
    void setUp() {
        personDTO1 = new PersonDTO();
        personDTO1.setName("Alice");
        personDTO1.setCity("Paris");
        personDTO1.setPhoneNumber("0123456789");
        personDTO1.setEmail("alice@mail.com");
        personDTO1.setAge(30L);

        personDTO2 = new PersonDTO();
        personDTO2.setName("Bob");
        personDTO2.setCity("Lyon");
        personDTO2.setPhoneNumber("0987654321");
        personDTO2.setEmail("bob@mail.com");
        personDTO2.setAge(40L);

        personRequestDTO = new PersonRequestDTO(List.of(personDTO1, personDTO2));
    }

    /**
     * 🔹 Test : Vérifier `savePersonss()` en ajoutant des personnes
     */
    @Test
    void shouldSavePersons() {
        // 🔹 Simuler la récupération de la séquence
        when(personRepository.getNextSequenceValue()).thenReturn(100L, 101L);

        // 🔹 Simuler la conversion et le stockage des personnes
        List<Person> personsToSave = personRequestDTO.getPersons().stream()
                .map(dto -> {
                    Person person = new Person();
                    person.setName(dto.getName());
                    person.setCity(dto.getCity());
                    person.setPhoneNumber(dto.getPhoneNumber());
                    person.setEmail(dto.getEmail());
                    person.setAge(dto.getAge());
                    person.setSequence(100L); // Simule une séquence
                    return person;
                })
                .collect(Collectors.toList());

        // 🔹 Simuler la sauvegarde en base
        when(personRepository.saveAll(anyList())).thenReturn(personsToSave);

        // 🔥 Appeler la méthode à tester
        List<PersonDTO> result = personService.savePersonss(personRequestDTO);

        // ✅ Vérifications
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Alice");
        assertThat(result.get(1).getName()).isEqualTo("Bob");

        verify(personRepository, times(1)).saveAll(anyList());
        verify(personRepository, times(1)).getNextSequenceValue();
    }

    /**
     * 🔹 Test : Vérifier que `savePersonss()` lève une exception si `PersonRequestDTO` est vide
     */
    @Test
    void shouldThrowExceptionWhenSavingEmptyList() {
        PersonRequestDTO emptyRequestDTO = new PersonRequestDTO(List.of());

        Exception exception = assertThrows(RuntimeException.class, () -> personService.savePersonss(emptyRequestDTO));

        assertThat(exception.getMessage()).isEqualTo("PersonRequestDTO must contain at least one person.");

        verify(personRepository, never()).saveAll(anyList());
    }
}
