package com.cwa.crudspringboot.repository;

import com.cwa.crudspringboot.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByPhoneNumberAndSequence(String phoneNumber, Long sequence);

    @Procedure(name = "nom_de_ta_procedure") // Si ta PS a un nom enregistré en base
    void executerProcedure();

    // Ou avec des paramètres si besoin
    /*@Procedure(value = "nom_de_ta_procedure")
    void executerProcedureAvecParams(@Param("param1") String param1, @Param("param2") Long param2); */

    @Query(value = "SELECT PERSON_SEQ.NEXTVAL FROM dual", nativeQuery = true)
    Long getNextSequenceValue();
}
