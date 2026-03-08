package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mx.sauap_db.desarrollo.persistence.AbstractDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.Profesor;

import java.util.List;

public class ProfesorDAO extends AbstractDAO<Profesor> {

    public ProfesorDAO() {
        super(Profesor.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    public void registrar(Profesor p) {
        save(p);
    }

    public void modificar(Profesor p) {
        update(p);
    }

    public void eliminar(Profesor p) {
        delete(p);
    }

    public List<Profesor> listarTodos() {
        return findAll();
    }

    public Profesor buscarPorRfc(String rfc) {
        return execute(em -> {
            try {
                return em.createQuery(
                                "SELECT p FROM Profesor p WHERE p.rfc = :rfc", Profesor.class)
                        .setParameter("rfc", rfc)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        });
    }

    public boolean existeRfc(String rfc) {
        return buscarPorRfc(rfc) != null;
    }

    public Profesor buscarPorId(int id) {
        return find(id).orElse(null);
    }
}

