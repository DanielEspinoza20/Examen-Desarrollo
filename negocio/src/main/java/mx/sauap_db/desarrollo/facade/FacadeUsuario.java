package mx.sauap_db.desarrollo.facade;

import mx.sauap_db.desarrollo.delegate.DelegateUsuario;
import mx.sauap_db.entity.Usuario;

public class FacadeUsuario {

    private final DelegateUsuario delegateUsuario;

    public FacadeUsuario() {
        this.delegateUsuario = new DelegateUsuario();
    }

    public Usuario login(String username, String password) {

        return delegateUsuario.login(username, password);
    }
}