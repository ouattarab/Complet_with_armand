package com.cwa.crudspringboot.controller;

import com.cwa.crudspringboot.dto.PersonDTO;
import com.cwa.crudspringboot.dto.PersonRequestDTO;
import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    private PersonDTO personDTO;
    private Person person;
    private PersonRequestDTO personRequestDTO;

    @BeforeEach
    void setUp() {
        personDTO = new PersonDTO("Alice", "Paris", "0123456789", "alice@mail.com", 30L);
        person = new Person(1L, "Alice", "Paris", "0123456789", "alice@mail.com", 30L, 100L);

        personRequestDTO = new PersonRequestDTO();
        personRequestDTO.setPersons(List.of(personDTO));
    }

    /**
     * 🔹 Test : Ajouter plusieurs personnes (`POST /api/persons/save`)
     */
    @Test
    void shouldSavePersons() throws Exception {
        when(personService.savePersonss(any(PersonRequestDTO.class)))
                .thenReturn(List.of(personDTO));

        mockMvc.perform(post("/api/persons/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    /**
     * 🔹 Test : Récupérer toutes les personnes (`GET /api/persons`)
     */
    @Test
    void shouldGetAllPersons() throws Exception {
        when(personService.getAllPersons()).thenReturn(List.of(personDTO));

        mockMvc.perform(get("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    /**
     * 🔹 Test : Récupérer une personne par son ID (`GET /api/persons/{id}`)
     */
    @Test
    void shouldGetPersonById() throws Exception {
        when(personService.getPersonById(anyLong())).thenReturn(personDTO);

        mockMvc.perform(get("/api/persons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    /**
     * 🔹 Test : Mettre à jour une personne (`PUT /api/persons/{id}`)
     */
    @Test
    void shouldUpdatePerson() throws Exception {
        when(personService.updatePerson(anyLong(), any(PersonDTO.class)))
                .thenReturn(personDTO);

        mockMvc.perform(put("/api/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    /**
     * 🔹 Test : Supprimer une personne (`DELETE /api/persons/{id}`)
     */
    @Test
    void shouldDeletePerson() throws Exception {
        doNothing().when(personService).deletePerson(anyLong());

        mockMvc.perform(delete("/api/persons/1"))
                .andExpect(status().isNoContent());
    }

    /**
     * 🔹 Test : Ajouter plusieurs personnes en batch (`POST /api/persons/batch`)
     */
    @Test
    void shouldSavePersonsBatch() throws Exception {
        when(personService.savePersons(any(PersonRequestDTO.class)))
                .thenReturn(List.of(person));

        mockMvc.perform(post("/api/persons/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }
}
