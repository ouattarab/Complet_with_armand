package com.cwa.crudspringboot.serviceImpl;

import com.cwa.crudspringboot.dao.PersonDao;
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
    private final PersonDao personDao; // Ajout du DAO

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    @Transactional
    public List<Person> savePersons(PersonRequestDTO personRequestDTO) {
        // Générer une séquence unique pour toutes les personnes de la liste
        Long sequenceValue = System.currentTimeMillis(); // Ou récupérer depuis Oracle

        // 🔹 Récupérer une nouvelle valeur de séquence depuis Oracle
      //  Long sequenceValue = personRepository.getNextSequenceValue();

        // 🔹 Récupérer une nouvelle valeur de séquence depuis Oracle
        /*Long sequenceValue = getNextSequenceValue();*/

        // Transformer les DTOs en entités et vérifier l’unicité
        List<Person> persons = personRequestDTO.getPersons().stream()
                .map(dto -> Person.builder()
                        .name(dto.getName())
                        .city(dto.getCity())
                        .phoneNumber(dto.getPhoneNumber())
                        .email(dto.getEmail())
                        .age(dto.getAge())
                        .sequence(sequenceValue)
                       // .sequence(sequenceValue)  // 🔹 Utilisation de la séquence Oracle
                        .build())
                .filter(person -> personRepository.findByPhoneNumberAndSequence(person.getPhoneNumber(), person.getSequence()).isEmpty())
                .collect(Collectors.toList());
        // Sauvegarde en base
        List<Person> savedPersons = personRepository.saveAll(persons);

        // ✅ Déterminer quelle(s) procédure(s) stockée(s) exécuter selon la présence de l'email
        boolean hasNullEmail = savedPersons.stream().anyMatch(person -> person.getEmail() == null);

        if (hasNullEmail) {
            // S'il y a au moins un email null, on exécute les deux PS
            personDao.executerMergeEmployeesCopyProcedure();
            personDao.executerMergeEmployeesProcedure();
        } else {
            // Sinon, on exécute seulement MERGE_EMPLOYEES
            personDao.executerMergeEmployeesProcedure();
        }

        return personRepository.saveAll(persons);
    }

}
