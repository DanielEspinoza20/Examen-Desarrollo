package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
import mx.sauap_db.desarrollo.persistence.AbstractDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.Asignacion;

import java.util.List;

public class AsignacionDAO extends AbstractDAO<Asignacion> {

    public AsignacionDAO() {
        super(Asignacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    public List<Asignacion> obtenerUnidadesConHoras() {
        return execute(em ->
                em.createQuery(
                        "SELECT a FROM Asignacion a JOIN FETCH a.idUnidad",
                        Asignacion.class
                ).getResultList()
        );
    }

}
