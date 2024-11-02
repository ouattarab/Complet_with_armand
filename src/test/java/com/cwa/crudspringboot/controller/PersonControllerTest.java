package com.cwa.crudspringboot.controller;


import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
   // private MockMvc mockMvcvc;

    @MockBean
    private PersonService personService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllPersons() throws Exception {
        Person p1 = new Person(1L,"John Doe","New York","123-456-7890");
        Person p2 = new Person(2L,"Jane smith","Miami","123-456-7890");
    when(personService.findAll()).thenReturn(List.of(p1,p2));

    mockMvc.perform(get("/api/persons"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void shouldReturnPersonById() throws Exception {
        Person p1 = new Person(1L,"John Doe","New York","123-456-7890");
        when(personService.findById(1L)).thenReturn(p1);

        mockMvc.perform(get("/api/persons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.city").value("New York"))
                .andExpect(jsonPath("$.phoneNumber").value("123-456-7890"));
    }

    @Test
    void shouldReturnCreatePersons() throws Exception {
        // Arrange : Préparation des données et configuration des comportements attendus
        String json = """
        {   "id":1,
            "name":"John Doe",
            "city":"New York",
            "phoneNumber":"123-456-7890"
        }
        """;
        Person p = new Person(1L,"John Doe","New York","123-456-7890");
        when(personService.saveOrUpdate(p)).thenReturn(p);

        // Act : Effectuer l’action de création via une requête POST
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                // Assert : Vérifier que la réponse a le statut attendu
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));

    }

    @Test
    void shouldReturnUpdatePersons() throws Exception {
        // Arrange : Préparation des données et configuration des comportements attendus
        String json = """
        {   "id":1,
            "name":"John Doe",
            "city":"Los Angeles",
            "phoneNumber":"123-456-7890"
        }
        """;
        Person existingPerson = new Person(1L,"John Doe","New York","123-456-7890");
        Person updatedPerson = new Person(1L,"John Doe","Los Angeles","123-456-7890");
        when(personService.findById(1L)).thenReturn(existingPerson);
        when(personService.saveOrUpdate(updatedPerson)).thenReturn(updatedPerson);
        // Act : Effectuer l’action de création via une requête PUT
        mockMvc.perform(put("/api/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                // Assert : Vérifier que la réponse a le statut attendu
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Los Angeles"));
    }

    @Test
    void shouldDeletePersonById() throws Exception {
        // Arrange : Préparation des données et configuration des comportements attendus
        Person p = new Person(1L,"John Doe","New York","123-456-7890");
        when(personService.findById(1L)).thenReturn(p);
        // Act : Exécution de la requête DELETE sur le point de terminaison /api/persons/1
        mockMvc.perform(delete("/api/persons/1"))
                // Assert : Vérification que le statut de la réponse est bien 200 OK
                .andExpect(status().isOk());
    }

}