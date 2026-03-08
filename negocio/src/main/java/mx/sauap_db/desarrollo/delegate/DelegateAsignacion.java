package mx.sauap_db.desarrollo.delegate;

import mx.sauap_db.desarrollo.dao.AsignacionDAO;
import mx.sauap_db.entity.Asignacion;
import mx.sauap_db.entity.Profesor;
import mx.sauap_db.entity.UnidadAprendizaje;

import java.time.LocalTime;
import java.util.List;

public class DelegateAsignacion {

    private final AsignacionDAO asignacionDAO = new AsignacionDAO();

    public void registrar(Asignacion asignacion) {
        asignacionDAO.guardar(asignacion);
    }

    public void actualizar(Asignacion asignacion) {
        asignacionDAO.actualizar(asignacion);
    }

    public void eliminar(Integer id) {
        asignacionDAO.eliminar(id);
    }

    public Asignacion buscarAsignacion(Integer id) {
        return asignacionDAO.buscarAsignacion(id);
    }

    public List<Asignacion> buscarPorProfesor(Profesor profesor) {
        return asignacionDAO.buscarPorProfesor(profesor);
    }

    public List<Asignacion> buscarPorProfesorYUnidad(Profesor profesor, Integer idUnidad) {
        return asignacionDAO.buscarPorProfesorYUnidad(profesor, idUnidad);
    }

    public boolean existeTraslape(Profesor profesor, String diaSemana,
                                  LocalTime horaInicio, LocalTime horaFin) {
        for (Asignacion a : asignacionDAO.buscarPorProfesor(profesor)) {
            if (a.getDiaSemana().equals(diaSemana)) {
                if (horaInicio.isBefore(a.getHoraFin()) && a.getHoraInicio().isBefore(horaFin)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existeTraslapeExcluyendo(Profesor profesor, String diaSemana,
                                            LocalTime horaInicio, LocalTime horaFin,
                                            Integer idExcluir) {
        return asignacionDAO.existeTraslapeExcluyendo(profesor, diaSemana, horaInicio, horaFin, idExcluir);
    }

    public List<Profesor> obtenerProfesores() {
        return asignacionDAO.obtenerProfesores();
    }

    public List<UnidadAprendizaje> obtenerUnidades() {
        return asignacionDAO.obtenerUnidades();
    }

    public Profesor buscarProfesor(Integer id) {
        return asignacionDAO.buscarProfesor(id);
    }

    public UnidadAprendizaje buscarUnidad(Integer id) {
        return asignacionDAO.buscarUnidad(id);
    }

    public boolean tieneAsignacionesProfesor(Profesor profesor) {
        return asignacionDAO.tieneAsignacionesProfesor(profesor);
    }

    public boolean tieneAsignacionesUnidad(Integer idUnidad) {
        return asignacionDAO.tieneAsignacionesUnidad(idUnidad);
    }
}