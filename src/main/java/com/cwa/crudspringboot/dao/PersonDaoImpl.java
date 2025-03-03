package com.cwa.crudspringboot.daoImpl;

import com.cwa.crudspringboot.dao.PersonDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void executerMergeEmployeesProcedure() {
        executeProcedure("MERGE_EMPLOYEES");
    }

    @Override
    public void executerMergeEmployeesCopyProcedure() {
        executeProcedure("MERGE_EMPLOYEEScopy");
    }

    private void executeProcedure(String procedureName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(procedureName);
        query.registerStoredProcedureParameter("p_result", void.class); // Paramètre OUT

        query.execute();

        Object result = query.getSingleResult();
        System.out.println("Résultat de la procédure " + procedureName + " : " + result);
    }
}
