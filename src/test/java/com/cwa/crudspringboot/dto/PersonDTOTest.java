package com.cwa.crudspringboot.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

class PersonDTOTest {

    /**
     * 🔹 Vérifie que les getters et setters fonctionnent correctement
     */
    @Test
    void testPersonDTOGettersAndSetters() {
        PersonDTO person = new PersonDTO();
        person.setName("Alice");
        person.setCity("Paris");
        person.setPhoneNumber("0123456789");
        person.setEmail("alice@mail.com");
        person.setAge(30L);

        assertThat(person.getName()).isEqualTo("Alice");
        assertThat(person.getCity()).isEqualTo("Paris");
        assertThat(person.getPhoneNumber()).isEqualTo("0123456789");
        assertThat(person.getEmail()).isEqualTo("alice@mail.com");
        assertThat(person.getAge()).isEqualTo(30L);
    }

    /**
     * 🔹 Vérifie que `equals()` et `hashCode()` fonctionnent correctement
     */
    @Test
    void testPersonDTOEqualsAndHashCode() {
        PersonDTO person1 = new PersonDTO();
        person1.setName("Alice");
        person1.setCity("Paris");

        PersonDTO person2 = new PersonDTO();
        person2.setName("Alice");
        person2.setCity("Paris");

        PersonDTO person3 = new PersonDTO();
        person3.setName("Bob");
        person3.setCity("Lyon");

        assertThat(person1).isEqualTo(person2);
        assertThat(person1).isNotEqualTo(person3);
        assertThat(person1.hashCode()).isEqualTo(person2.hashCode());
        assertThat(person1.hashCode()).isNotEqualTo(person3.hashCode());
    }

    /**
     * 🔹 Vérifie la conversion JSON ↔ DTO
     */
    @Test
    void testPersonDTOSerialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        PersonDTO person = new PersonDTO();
        person.setName("Alice");
        person.setCity("Paris");
        person.setPhoneNumber("0123456789");
        person.setEmail("alice@mail.com");
        person.setAge(30L);

        String json = objectMapper.writeValueAsString(person);

        assertThat(json).contains("\"name\":\"Alice\"");
        assertThat(json).contains("\"city\":\"Paris\"");
    }

    @Test
    void testPersonDTODeserialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{\"name\":\"Alice\",\"city\":\"Paris\",\"phoneNumber\":\"0123456789\",\"email\":\"alice@mail.com\",\"age\":30}";

        PersonDTO person = objectMapper.readValue(json, PersonDTO.class);

        assertThat(person.getName()).isEqualTo("Alice");
        assertThat(person.getCity()).isEqualTo("Paris");
    }

    /**
     * 🔹 Vérifie que `PersonRequestDTO` fonctionne correctement
     */
    @Test
    void testPersonRequestDTO() {
        PersonDTO person1 = new PersonDTO();
        person1.setName("Alice");
        person1.setCity("Paris");

        PersonDTO person2 = new PersonDTO();
        person2.setName("Bob");
        person2.setCity("Lyon");

        PersonRequestDTO requestDTO = new PersonRequestDTO();
        requestDTO.setPersons(List.of(person1, person2));

        assertThat(requestDTO.getPersons()).hasSize(2);
        assertThat(requestDTO.getPersons().get(0).getName()).isEqualTo("Alice");
        assertThat(requestDTO.getPersons().get(1).getName()).isEqualTo("Bob");
    }
}
