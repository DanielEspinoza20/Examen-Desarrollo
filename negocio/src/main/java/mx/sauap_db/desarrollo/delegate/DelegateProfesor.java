package mx.sauap_db.desarrollo.delegate;

import mx.sauap_db.desarrollo.integration.ServiceLocator;
import mx.sauap_db.entity.Profesor;

import java.util.List;

public class DelegateProfesor {
    public List<Profesor> listarProfesores() {
        return ServiceLocator.getInstanceProfesorDAO().obtenerListaProfesores();
    }
}
