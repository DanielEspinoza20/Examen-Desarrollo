package mx.sauap_db.desarrollo.delegate;

import mx.sauap_db.desarrollo.dao.AsignacionDAO;
import mx.sauap_db.entity.Asignacion;

import java.util.List;

public class DelegateAsignacion {
    private final AsignacionDAO dao;

    public DelegateAsignacion() {
        this.dao = new AsignacionDAO();
    }

    public List<Asignacion> obtenerUnidadesConHoras() {
        return dao.obtenerUnidadesConHoras();
    }
}
