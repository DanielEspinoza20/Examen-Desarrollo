package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
import mx.sauap_db.desarrollo.persistence.AbstractDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.UnidadAprendizaje;

public class UnidadAprendizajeDAO extends AbstractDAO<UnidadAprendizaje> {

    public UnidadAprendizajeDAO() {
        super(UnidadAprendizaje.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }
}