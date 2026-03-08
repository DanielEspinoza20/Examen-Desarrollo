package mx.sauap_db.desarrollo.facade;

import mx.sauap_db.desarrollo.delegate.DelegateProfesor;
import mx.sauap_db.entity.Profesor;

public class FacadeProfesor {

    private final DelegateProfesor delegate = new DelegateProfesor();

    public void registrar(Profesor p) {
        delegate.registrar(p);
    }

    public void modificar(Profesor p) {
        delegate.modificar(p);
    }
    public void eliminar(Profesor p) {
        delegate.eliminar(p);
    }

    public Profesor buscarPorRfc(String rfc) {
        return delegate.buscarPorRfc(rfc);
    }

    public boolean rfcDisponible(String rfc, int idExcluir) {
        Profesor existente = delegate.buscarPorRfc(rfc);
        if (existente == null) return true;
        return idExcluir > 0 && existente.getId() == idExcluir;
    }
    public Profesor buscarPorId(int id) {
        return delegate.buscarPorId(id);
    }

}

