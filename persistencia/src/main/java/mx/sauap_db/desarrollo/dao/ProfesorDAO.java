package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
import mx.sauap_db.desarrollo.persistence.AbstractDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.Profesor;

public class ProfesorDAO extends AbstractDAO<Profesor> {

    public ProfesorDAO() {
        super(Profesor.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }
}