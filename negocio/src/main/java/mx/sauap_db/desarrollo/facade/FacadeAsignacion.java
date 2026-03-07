package mx.sauap_db.desarrollo.facade;

import mx.sauap_db.desarrollo.delegate.DelegateAsignacion;
import java.util.List;

public class FacadeAsignacion {

    private final DelegateAsignacion delegate = new DelegateAsignacion();

    public List<Object[]> getHorarioProfesor(int idProfesor) {
        return delegate.getHorarioProfesor(idProfesor);
    }
}
