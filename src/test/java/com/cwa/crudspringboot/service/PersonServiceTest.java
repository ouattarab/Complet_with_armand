package com.cwa.crudspringboot.service;

import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void shouldReturnAllPersons() {
         Person p1 = new Person(1L,"John Doe","New York","123-456-7890");
         Person p2 = new Person(2L,"Jane smith","Miami","123-456-7890");

         when(personRepository.findAll()).thenReturn(List.of(p1, p2));

         List<Person> persons = personService.findAll();
         assertThat(persons).hasSize(2).containsExactly(p1,p2);
    }

    @Test
    void shouldReturnPersonById() {
        Person p = new Person(1L,"John Doe","New York","123-456-7890");
        when(personRepository.findById(1L)).thenReturn(Optional.of(p));
        Person person = personService.findById(1L);
        assertThat(person).isEqualTo(p);
    }

    @Test
    void shouldReturnPersonSaveOrUpdate() {
        Person p = new Person(1L,"John Doe","New York","123-456-7890");
        when(personRepository.save(p)).thenReturn(p);
        Person person = personService.saveOrUpdate(p);
        assertThat(person).isEqualTo(p);
    }
    @Test
    void shouldDeletePerson() {
        personService.deleteById(1L);

        verify(personRepository).deleteById(1L);
    }
}