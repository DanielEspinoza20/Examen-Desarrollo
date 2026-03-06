package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import mx.sauap_db.desarrollo.persistence.AbstractDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.UnidadAprendizaje;
import java.util.List;

public class UnidadAprendizajeDAO extends AbstractDAO<UnidadAprendizaje> {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("desarrolloPU");

    public void registrar(UnidadAprendizaje u) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        em.close();
    }

    public void modificar(UnidadAprendizaje u) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
        em.close();
    }

    public void eliminar(int idUnidad) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        UnidadAprendizaje u = em.find(UnidadAprendizaje.class, idUnidad);
        if (u != null) em.remove(u);
        em.getTransaction().commit();
        em.close();
    }

    public UnidadAprendizaje buscarPorId(int idUnidad) {
        EntityManager em = emf.createEntityManager();
        UnidadAprendizaje u = em.find(UnidadAprendizaje.class, idUnidad);
        em.close();
        return u;
    }

    public List<UnidadAprendizaje> listarTodas() {
        EntityManager em = emf.createEntityManager();
        List<UnidadAprendizaje> lista = em
                .createQuery("SELECT u FROM UnidadAprendizaje u", UnidadAprendizaje.class)
                .getResultList();
        em.close();
        return lista;
    }

    public UnidadAprendizajeDAO() {
        super(UnidadAprendizaje.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }
}