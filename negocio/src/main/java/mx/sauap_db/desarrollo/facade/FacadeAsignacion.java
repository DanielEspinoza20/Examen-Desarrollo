package mx.sauap_db.desarrollo.facade;

import mx.sauap_db.desarrollo.delegate.DelegateAsignacion;
import mx.sauap_db.entity.Asignacion;
import mx.sauap_db.entity.Profesor;
import mx.sauap_db.entity.UnidadAprendizaje;

import java.time.LocalTime;
import java.util.List;

public class FacadeAsignacion {

    private final DelegateAsignacion delegateAsignacion;

    public FacadeAsignacion() {
        this.delegateAsignacion = new DelegateAsignacion();
    }

    public void registrar(Asignacion asignacion) {
        delegateAsignacion.registrar(asignacion);
    }

    public void actualizar(Asignacion asignacion) {
        delegateAsignacion.actualizar(asignacion);
    }

    public void eliminar(Integer id) {
        delegateAsignacion.eliminar(id);
    }

    public Asignacion buscarAsignacion(Integer id) {
        return delegateAsignacion.buscarAsignacion(id);
    }

    public List<Asignacion> buscarPorProfesor(Profesor profesor) {
        return delegateAsignacion.buscarPorProfesor(profesor);
    }

    public List<Asignacion> buscarPorProfesorYUnidad(Profesor profesor, Integer idUnidad) {
        return delegateAsignacion.buscarPorProfesorYUnidad(profesor, idUnidad);
    }

    public boolean existeTraslape(Profesor profesor, String diaSemana,
                                  LocalTime horaInicio, LocalTime horaFin) {
        return delegateAsignacion.existeTraslape(profesor, diaSemana, horaInicio, horaFin);
    }

    public boolean existeTraslapeExcluyendo(Profesor profesor, String diaSemana,
                                            LocalTime horaInicio, LocalTime horaFin,
                                            Integer idExcluir) {
        return delegateAsignacion.existeTraslapeExcluyendo(profesor, diaSemana, horaInicio, horaFin, idExcluir);
    }

    public List<Profesor> obtenerProfesores() {
        return delegateAsignacion.obtenerProfesores();
    }

    public List<UnidadAprendizaje> obtenerUnidades() {
        return delegateAsignacion.obtenerUnidades();
    }

    public Profesor buscarProfesor(Integer id) {
        return delegateAsignacion.buscarProfesor(id);
    }

    public UnidadAprendizaje buscarUnidad(Integer id) {
        return delegateAsignacion.buscarUnidad(id);
    }

    public boolean tieneAsignacionesProfesor(Profesor profesor) {
        return delegateAsignacion.tieneAsignacionesProfesor(profesor);
    }

    public boolean tieneAsignacionesUnidad(Integer idUnidad) {
        return delegateAsignacion.tieneAsignacionesUnidad(idUnidad);
    }
}