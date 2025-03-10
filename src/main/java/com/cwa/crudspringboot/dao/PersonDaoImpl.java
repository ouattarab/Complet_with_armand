package com.cwa.crudspringboot.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void executerMergeEmployeesProcedure() {
        log.info("Appel de la procédure MERGE_EMPLOYEES");

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("MERGE_EMPLOYEES");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();

        String result = (String) query.getOutputParameterValue(1);
        log.info("Résultat de MERGE_EMPLOYEES : {}", result);
    }

    @Override
    @Transactional
    public void executerMergeEmployeesCopyProcedure() {
        log.info("Appel de la procédure MERGE_EMPLOYEEScopy");

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("MERGE_EMPLOYEEScopy");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
        query.execute();

        String result = (String) query.getOutputParameterValue(1);
        log.info("Résultat de MERGE_EMPLOYEEScopy : {}", result);
    }
}
