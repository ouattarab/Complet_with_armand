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
    @Transactional
    public void executerProcedure() {
        try {
            log.info("✅ Exécution de la procédure stockée...");

            // Création de la requête pour exécuter la procédure stockée
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("nom_de_ta_procedure");

            // Exécution de la procédure
            query.execute();

            log.info("✅ Procédure stockée exécutée avec succès !");
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'exécution de la procédure stockée : ", e);
        }
    }
}
