package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mx.sauap_db.desarrollo.persistence.AbstractDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.Usuario;

public class UsuarioDAO extends AbstractDAO<Usuario> {
    public UsuarioDAO() {
        super(Usuario.class);
    }
    protected EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }

    public Usuario login(String username, String password){
        try {
            return getEntityManager().createQuery(
                            "SELECT u FROM Usuario u WHERE u.username = :user AND u.password = :pass",
                            Usuario.class)
                    .setParameter("user", username)
                    .setParameter("pass", password)
                    .getSingleResult();
        }catch (NoResultException e){
            return  null;
            }
        }


    }
