package ui;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mx.sauap_db.desarrollo.facade.FacadeAsignacion;
import mx.sauap_db.desarrollo.facade.FacadeProfesor;
import mx.sauap_db.entity.Asignacion;
import mx.sauap_db.entity.Profesor;
import mx.sauap_db.entity.Usuario;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Named("profesorSesionBean")
@RequestScoped
public class ProfesorSesionBean implements Serializable {

    @Inject
    private LoginBean loginBean;

    private final FacadeProfesor   facadeProfesor   = new FacadeProfesor();
    private final FacadeAsignacion facadeAsignacion = new FacadeAsignacion();

    private Profesor         profesor;
    private List<Asignacion> asignaciones;

    // ── Horas y días de la tabla ──────────────────────────────────
    private static final List<String> HORAS = Arrays.asList(
            "7:00am","8:00am","9:00am","10:00am","11:00am",
            "12:00pm","1:00pm","2:00pm","3:00pm","4:00pm",
            "5:00pm","6:00pm","7:00pm","8:00pm","9:00pm","10:00pm"
    );

    private static final List<String> DIAS = Arrays.asList(
            "Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"
    );

    private static LocalTime horaALocalTime(String horaLabel) {
        switch (horaLabel) {
            case "7:00am":  return LocalTime.of(7,  0);
            case "8:00am":  return LocalTime.of(8,  0);
            case "9:00am":  return LocalTime.of(9,  0);
            case "10:00am": return LocalTime.of(10, 0);
            case "11:00am": return LocalTime.of(11, 0);
            case "12:00pm": return LocalTime.of(12, 0);
            case "1:00pm":  return LocalTime.of(13, 0);
            case "2:00pm":  return LocalTime.of(14, 0);
            case "3:00pm":  return LocalTime.of(15, 0);
            case "4:00pm":  return LocalTime.of(16, 0);
            case "5:00pm":  return LocalTime.of(17, 0);
            case "6:00pm":  return LocalTime.of(18, 0);
            case "7:00pm":  return LocalTime.of(19, 0);
            case "8:00pm":  return LocalTime.of(20, 0);
            case "9:00pm":  return LocalTime.of(21, 0);
            case "10:00pm": return LocalTime.of(22, 0);
            default:        return LocalTime.MIDNIGHT;
        }
    }

    @PostConstruct
    public void init() {
        Usuario usuarioActivo = loginBean.getUsuario();

        if (usuarioActivo == null || usuarioActivo.getRfc() == null
                || usuarioActivo.getRfc().trim().isEmpty()) {
            profesor     = null;
            asignaciones = Collections.emptyList();
            return;
        }

        profesor = facadeProfesor.buscarPorRfc(usuarioActivo.getRfc().trim());

        if (profesor != null) {
            asignaciones = facadeAsignacion.buscarPorProfesor(profesor);
        } else {
            asignaciones = Collections.emptyList();
        }
    }

    /**
     * Devuelve true si la asignación corresponde al día y hora de la celda.
     * La celda se pinta si: horaInicio <= celdaHora < horaFin
     */
    public boolean coincide(Asignacion a, String dia, String horaLabel) {
        if (a == null || a.getDiaSemana() == null) return false;
        if (!a.getDiaSemana().equalsIgnoreCase(dia)) return false;

        LocalTime celdaHora  = horaALocalTime(horaLabel);
        LocalTime inicioAsig = a.getHoraInicio();
        LocalTime finAsig    = a.getHoraFin();

        return !celdaHora.isBefore(inicioAsig) && celdaHora.isBefore(finAsig);
    }

    // ── Getters ──────────────────────────────────────────────────
    public Profesor getProfesor() { return profesor; }

    public List<Asignacion> getAsignaciones() { return asignaciones; }

    public boolean isTieneAsignaciones() {
        return asignaciones != null && !asignaciones.isEmpty();
    }

    public String getNombreCompleto() {
        if (profesor == null) return loginBean.getUsuario() != null
                ? loginBean.getUsuario().getUsername() : "Profesor";
        return profesor.getNombre() + " "
                + profesor.getApellidoPaterno() + " "
                + profesor.getApellidoMaterno();
    }

    public List<String> getHorasDelDia() { return HORAS; }
    public List<String> getDiasSemana()  { return DIAS;  }
}