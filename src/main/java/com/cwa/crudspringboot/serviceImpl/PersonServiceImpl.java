package com.cwa.crudspringboot.serviceImpl;

import com.cwa.crudspringboot.dto.PersonDTO;
import com.cwa.crudspringboot.dto.PersonRequestDTO;
import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.repository.PersonRepository;
import com.cwa.crudspringboot.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    @Transactional
    public List<Person> savePersons(PersonRequestDTO personRequestDTO) {
        // Générer une séquence unique pour toutes les personnes de la liste
        Long sequenceValue = System.currentTimeMillis(); // Ou récupérer depuis Oracle

        // 🔹 Récupérer une nouvelle valeur de séquence depuis Oracle
        /*Long sequenceValue = getNextSequenceValue();*/

        // Transformer les DTOs en entités et vérifier l’unicité
        List<Person> persons = personRequestDTO.getPersons().stream()
                .map(dto -> Person.builder()
                        .name(dto.getName())
                        .city(dto.getCity())
                        .phoneNumber(dto.getPhoneNumber())
                        .sequence(sequenceValue)
                       // .sequence(sequenceValue)  // 🔹 Utilisation de la séquence Oracle
                        .build())
                .filter(person -> personRepository.findByPhoneNumberAndSequence(person.getPhoneNumber(), person.getSequence()).isEmpty())
                .collect(Collectors.toList());

        return personRepository.saveAll(persons);
    }
    // 🔹 Méthode pour récupérer la séquence Oracle
    /*private Long getNextSequenceValue() {
        return (Long) entityManager.createNativeQuery("SELECT SEQUENCE_PERSON.NEXTVAL FROM DUAL").getSingleResult();
    } */
}
