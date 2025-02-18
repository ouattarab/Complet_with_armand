package com.cwa.crudspringboot.repository;

import com.cwa.crudspringboot.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByPhoneNumberAndSequence(String phoneNumber, Long sequence);
}
