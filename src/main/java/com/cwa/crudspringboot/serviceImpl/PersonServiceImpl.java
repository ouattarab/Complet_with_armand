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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonDao personDao; // Ajout du DAO





    @Override
    @Transactional
    public List<Person> savePersons(PersonRequestDTO personRequestDTO) {
        Long sequenceValue = personRepository.getNextSequenceValue();
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
            personDao.executerMergeEmployeesCopyProcedure();
            personDao.executerMergeEmployeesProcedure();
        } else {
            personDao.executerMergeEmployeesProcedure();
        }
        return personRepository.saveAll(persons);
    }

    @Override
    public List<PersonDTO> savePersonss(PersonRequestDTO personRequestDTO) {
        if (personRequestDTO.getPersons() == null || personRequestDTO.getPersons().isEmpty()) {
            throw new RuntimeException("PersonRequestDTO must contain at least one person.");
        }

        Long sequenceValue = personRepository.getNextSequenceValue();

        List<Person> personsToSave = personRequestDTO.getPersons().stream()
                .map(dto -> {
                    Person person = mapToEntity(dto);
                    person.setSequence(sequenceValue);

                    return person;
                })
                .collect(Collectors.toList());

        List<Person> savedPersons = personRepository.saveAll(personsToSave);

        return savedPersons.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }




    @Override
    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        return mapToDTO(person);
    }


    @Override
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));

        person.setName(personDTO.getName());
        person.setCity(personDTO.getCity());
        person.setPhoneNumber(personDTO.getPhoneNumber());
        person.setEmail(personDTO.getEmail());
        person.setAge(personDTO.getAge());

        Person updatedPerson = personRepository.save(person);
        return mapToDTO(updatedPerson);
    }

    @Override
    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException("Person not found with id: " + id);
        }
        personRepository.deleteById(id);
    }

    private PersonDTO mapToDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setName(person.getName());
        dto.setCity(person.getCity());
        dto.setPhoneNumber(person.getPhoneNumber());
        dto.setEmail(person.getEmail());
        dto.setAge(person.getAge());
        return dto;
    }

    private Person mapToEntity(PersonDTO dto) {
        Person person = new Person();
        person.setName(dto.getName());
        person.setCity(dto.getCity());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setEmail(dto.getEmail());
        person.setAge(dto.getAge());
        return person;
    }



}
