package mx.sauap_db.desarrollo.delegate;

import mx.sauap_db.desarrollo.dao.ProfesorDAO;
import mx.sauap_db.entity.Profesor;

public class DelegateProfesor {

    private final ProfesorDAO profesorDAO = new ProfesorDAO();

    public void registrar(Profesor p) {
        profesorDAO.registrar(p);
    }

    public void modificar(Profesor p) {
        profesorDAO.modificar(p);
    }
    public void eliminar(Profesor p) {
        profesorDAO.eliminar(p);
    }

    public Profesor buscarPorRfc(String rfc) {
        return profesorDAO.buscarPorRfc(rfc);
    }
    public Profesor buscarPorId(int id) {
        return profesorDAO.buscarPorId(id);
    }

    public boolean existeRfc(String rfc) {
        return profesorDAO.existeRfc(rfc);
    }

}
