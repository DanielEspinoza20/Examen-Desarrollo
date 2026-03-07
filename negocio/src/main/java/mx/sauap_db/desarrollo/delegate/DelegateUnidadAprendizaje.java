package mx.sauap_db.desarrollo.delegate;

import mx.sauap_db.desarrollo.dao.UnidadAprendizajeDAO;
import mx.sauap_db.entity.UnidadAprendizaje;

import java.util.List;

public class DelegateUnidadAprendizaje {

    private final UnidadAprendizajeDAO unidadDAO = new UnidadAprendizajeDAO();

    public void registrar(UnidadAprendizaje u) {
        unidadDAO.registrar(u);
    }

    public void modificar(UnidadAprendizaje u) {
        unidadDAO.modificar(u);
    }

    public void eliminar(int id) {
        unidadDAO.eliminar(id);
    }

    public List<UnidadAprendizaje> listarTodas() {
        return unidadDAO.listarTodas();
    }

    public UnidadAprendizaje buscarPorId(int id) {
        return unidadDAO.buscarPorId(id);
    }

    public UnidadAprendizaje buscarPorNombre(String nombre) {
        return unidadDAO.findByOneParameterUnique(nombre, "nombreUnidad");
    }

    public boolean existeNombre(String nombre) {
        return buscarPorNombre(nombre) != null;
    }
}