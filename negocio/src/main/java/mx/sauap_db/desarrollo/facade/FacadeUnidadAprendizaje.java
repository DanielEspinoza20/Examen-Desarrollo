package mx.sauap_db.desarrollo.facade;

import mx.sauap_db.desarrollo.delegate.DelegateUnidadAprendizaje;
import mx.sauap_db.entity.UnidadAprendizaje;

import java.util.List;

public class FacadeUnidadAprendizaje {

    private final DelegateUnidadAprendizaje delegate = new DelegateUnidadAprendizaje();

    public void registrar(UnidadAprendizaje u) {
        delegate.registrar(u);
    }

    public void modificar(UnidadAprendizaje u) {
        delegate.modificar(u);
    }

    public void eliminar(int id) {
        delegate.eliminar(id);
    }

    public List<UnidadAprendizaje> listarTodas() {
        return delegate.listarTodas();
    }

    public UnidadAprendizaje buscarPorNombre(String nombre) {
        return delegate.buscarPorNombre(nombre);
    }

    public UnidadAprendizaje buscarPorId(int id) {
        return delegate.buscarPorId(id);
    }

    public boolean nombreDisponible(String nombre, int idExcluir) {
        UnidadAprendizaje existente = delegate.buscarPorNombre(nombre);
        if (existente == null) return true;
        return idExcluir > 0 && existente.getId() == idExcluir;
    }
}
