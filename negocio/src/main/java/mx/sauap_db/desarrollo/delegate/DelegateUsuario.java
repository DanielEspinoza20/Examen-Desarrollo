package mx.sauap_db.desarrollo.delegate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mx.sauap_db.entity.Usuario;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;

public class DelegateUsuario {

    public Usuario login(String username, String password) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.username = :user AND u.password = :pass",
                    Usuario.class);

            query.setParameter("user", username);
            query.setParameter("pass", password);

            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
}