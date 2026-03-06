package mx.sauap_db.desarrollo.integration;

import mx.sauap_db.desarrollo.facade.FacadeAsignacion;
import mx.sauap_db.desarrollo.facade.FacadeProfesor;
import mx.sauap_db.desarrollo.facade.FacadeUnidadAprendizaje;
import mx.sauap_db.desarrollo.facade.FacadeUsuario;

import mx.sauap_db.desarrollo.facade.FacadeUsuario;

public class ServiceFacadeLocator {

    private static FacadeUsuario facadeUsuario;
    private static FacadeAsignacion facadeAsignacion;
    private static FacadeProfesor facadeProfesor;
    private static FacadeUnidadAprendizaje facadeUnidadAprendizaje;


    public static FacadeUnidadAprendizaje getInstanceFacadeAlumno() {
        if (facadeUnidadAprendizaje == null) {
            facadeUnidadAprendizaje = new FacadeUnidadAprendizaje();
            return facadeUnidadAprendizaje;
        } else {
            return facadeUnidadAprendizaje;
        }
    }

    public static FacadeProfesor getInstanceFacadeProfesor() {
        if (facadeProfesor == null) {
            facadeProfesor = new FacadeProfesor();
            return facadeProfesor;
        } else {
            return facadeProfesor;
        }
    }

    public static FacadeAsignacion getInstanceFacadeAsignacion() {
        if (facadeProfesor == null) {
            facadeAsignacion = new FacadeAsignacion();
            return facadeAsignacion;
        } else {
            return facadeAsignacion;
        }
    }

    public static FacadeUsuario getInstanceFacadeUsuario() {
        if (facadeUsuario == null) {
            facadeUsuario = new FacadeUsuario();
            return facadeUsuario;
        } else {
            return facadeUsuario;
        }
    }
}
