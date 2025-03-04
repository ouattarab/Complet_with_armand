package com.cwa.crudspringboot.daoImpl;

import com.cwa.crudspringboot.dao.PersonDao;
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

        // Enregistre le premier paramètre OUT (p_result)
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);

        // Exécute la procédure
        query.execute();

        // Récupère le résultat retourné
        String result = (String) query.getOutputParameterValue(1);

        log.info("Résultat de MERGE_EMPLOYEES : {}", result);
    }

    @Override
    @Transactional
    public void executerMergeEmployeesCopyProcedure() {
        log.info("Appel de la procédure MERGE_EMPLOYEEScopy");

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("MERGE_EMPLOYEEScopy");

        // Enregistre le premier paramètre OUT (p_result)
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);

        // Exécute la procédure
        query.execute();

        // Récupère le résultat retourné
        String result = (String) query.getOutputParameterValue(1);

        log.info("Résultat de MERGE_EMPLOYEEScopy : {}", result);
    }

    @Override
    public void executerProcedure() {
        // Cette méthode est vide pour le moment, tu peux la supprimer si elle ne sert à rien
    }
}
