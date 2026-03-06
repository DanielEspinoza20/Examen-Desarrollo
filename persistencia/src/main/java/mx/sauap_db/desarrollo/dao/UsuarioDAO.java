package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
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
}
