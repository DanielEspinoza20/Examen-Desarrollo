package mx.sauap_db.desarrollo.delegate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mx.sauap_db.desarrollo.dao.UsuarioDAO;
import mx.sauap_db.desarrollo.integration.ServiceLocator;
import mx.sauap_db.entity.Usuario;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;

public class DelegateUsuario {
    public Usuario login(String username, String password) {
        return ServiceLocator.getInstanceUsuarioDAO().login(username, password);
    }
}