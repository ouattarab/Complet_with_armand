package com.cwa.crudspringboot.service.impl;

import com.cwa.crudspringboot.dto.PersonDTO;
import com.cwa.crudspringboot.dto.PersonRequestDTO;
import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePersons() {
        PersonDTO dto1 = new PersonDTO();
        dto1.setName("Alice");
        dto1.setCity("Paris");
        dto1.setPhoneNumber("123456");

        PersonDTO dto2 = new PersonDTO();
        dto2.setName("Bob");
        dto2.setCity("Lyon");
        dto2.setPhoneNumber("654321");

        PersonRequestDTO requestDTO = new PersonRequestDTO();
        requestDTO.setPersons(Arrays.asList(dto1, dto2));

        when(personRepository.findByPhoneNumberAndSequence(any(), any())).thenReturn(Optional.empty());
        when(personRepository.saveAll(any())).thenReturn(Arrays.asList());

        List<Person> savedPersons = personService.savePersons(requestDTO);

        assertEquals(2, savedPersons.size());
        verify(personRepository, times(1)).saveAll(any());
    }
}
