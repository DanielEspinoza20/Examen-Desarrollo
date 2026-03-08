package ui;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.sauap_db.desarrollo.facade.FacadeAsignacion;
import mx.sauap_db.entity.Asignacion;
import mx.sauap_db.entity.Profesor;
import mx.sauap_db.entity.UnidadAprendizaje;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Named("modificarAsignacionBean")
@SessionScoped
public class ModificarAsignacionBean implements Serializable {

    private final FacadeAsignacion facade = new FacadeAsignacion();

    private List<Profesor>          profesores;
    private List<UnidadAprendizaje> unidades;

    private Integer idProfesorSel;
    private Integer idUnidadSel;
    private boolean asignacionEncontrada = false;

    // IDs de cada asignación — ordenados por id ASC: índice 0=Clase, 1=Taller, 2=Lab
    private Integer idAsignacionClase;
    private Integer idAsignacionTaller;
    private Integer idAsignacionLab;

    private String  diaClase;    private Integer inicioClase;   private Integer finClase;
    private String  diaTaller;   private Integer inicioTaller;  private Integer finTaller;
    private String  diaLab;      private Integer inicioLab;     private Integer finLab;

    @PostConstruct
    public void init() {
        profesores = facade.obtenerProfesores();
        unidades   = facade.obtenerUnidades();
    }

    // ── Buscar ────────────────────────────────────────────────────────────────
    public void buscar() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        asignacionEncontrada = false;

        if (idProfesorSel == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Selecciona un profesor"));
            return;
        }
        if (idUnidadSel == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Selecciona una unidad de aprendizaje"));
            return;
        }

        Profesor profesor = facade.buscarProfesor(idProfesorSel);
        // Lista ordenada por id ASC → posición 0=Clase, 1=Taller, 2=Lab
        List<Asignacion> lista = facade.buscarPorProfesorYUnidad(profesor, idUnidadSel);

        if (lista == null || lista.isEmpty()) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se encontró asignación para esa combinación"));
            return;
        }

        // Limpiar
        diaClase = null; inicioClase = null; finClase = null; idAsignacionClase = null;
        diaTaller = null; inicioTaller = null; finTaller = null; idAsignacionTaller = null;
        diaLab = null; inicioLab = null; finLab = null; idAsignacionLab = null;

        // índice 0 → Clase
        if (lista.size() > 0) {
            Asignacion a = lista.get(0);
            idAsignacionClase = a.getId();
            diaClase    = a.getDiaSemana();
            inicioClase = a.getHoraInicio().getHour();
            finClase    = a.getHoraFin().getHour();
        }
        // índice 1 → Taller
        if (lista.size() > 1) {
            Asignacion a = lista.get(1);
            idAsignacionTaller = a.getId();
            diaTaller    = a.getDiaSemana();
            inicioTaller = a.getHoraInicio().getHour();
            finTaller    = a.getHoraFin().getHour();
        }
        // índice 2 → Lab
        if (lista.size() > 2) {
            Asignacion a = lista.get(2);
            idAsignacionLab = a.getId();
            diaLab    = a.getDiaSemana();
            inicioLab = a.getHoraInicio().getHour();
            finLab    = a.getHoraFin().getHour();
        }

        asignacionEncontrada = true;
        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Éxito", "Asignación cargada correctamente"));
    }

    // ── Modificar ─────────────────────────────────────────────────────────────
    public void modificarClase()  { modificar(diaClase,  inicioClase,  finClase,  "Clase",       idAsignacionClase);  }
    public void modificarTaller() { modificar(diaTaller, inicioTaller, finTaller, "Taller",      idAsignacionTaller); }
    public void modificarLab()    { modificar(diaLab,    inicioLab,    finLab,    "Laboratorio", idAsignacionLab);    }

    private void modificar(String dia, Integer inicio, Integer fin, String tipo, Integer idAsignacion) {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (!asignacionEncontrada || idAsignacion == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Primero busca una asignación"));
            return;
        }
        if (dia == null || dia.isEmpty()) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Selecciona un día para " + tipo));
            return;
        }
        if (inicio == null || inicio < 7 || inicio > 21) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Hora inicio de " + tipo + " debe ser entre 7 y 21"));
            return;
        }
        if (fin == null || fin < 8 || fin > 22) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Hora fin de " + tipo + " debe ser entre 8 y 22"));
            return;
        }
        if (fin <= inicio) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Hora fin debe ser mayor a hora inicio en " + tipo));
            return;
        }

        try {
            Profesor profesor    = facade.buscarProfesor(idProfesorSel);
            LocalTime horaInicio = LocalTime.of(inicio, 0);
            LocalTime horaFin    = LocalTime.of(fin, 0);

            // Verifica traslape ignorando el registro que se está editando
            if (facade.existeTraslapeExcluyendo(profesor, dia, horaInicio, horaFin, idAsignacion)) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", "Traslape: el profesor ya tiene asignación en "
                        + dia + " " + inicio + ":00–" + fin + ":00"));
                return;
            }

            Asignacion a = facade.buscarAsignacion(idAsignacion);
            if (a == null) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", "No se encontró la asignación a modificar"));
                return;
            }

            a.setDiaSemana(dia);
            a.setHoraInicio(horaInicio);
            a.setHoraFin(horaFin);
            facade.actualizar(a);

            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito", tipo + " modificado: " + dia + " " + inicio + ":00–" + fin + ":00"));

        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se pudo modificar: " + e.getMessage()));
        }
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────
    public List<Profesor>          getProfesores()          { return profesores; }
    public List<UnidadAprendizaje> getUnidades()            { return unidades; }
    public boolean                 isAsignacionEncontrada() { return asignacionEncontrada; }

    public Integer getIdProfesorSel()               { return idProfesorSel; }
    public void    setIdProfesorSel(Integer v)       { this.idProfesorSel = v; }
    public Integer getIdUnidadSel()                 { return idUnidadSel; }
    public void    setIdUnidadSel(Integer v)         { this.idUnidadSel = v; }

    public String  getDiaClase()                    { return diaClase; }
    public void    setDiaClase(String v)            { this.diaClase = v; }
    public Integer getInicioClase()                 { return inicioClase; }
    public void    setInicioClase(Integer v)        { this.inicioClase = v; }
    public Integer getFinClase()                    { return finClase; }
    public void    setFinClase(Integer v)           { this.finClase = v; }

    public String  getDiaTaller()                   { return diaTaller; }
    public void    setDiaTaller(String v)           { this.diaTaller = v; }
    public Integer getInicioTaller()                { return inicioTaller; }
    public void    setInicioTaller(Integer v)       { this.inicioTaller = v; }
    public Integer getFinTaller()                   { return finTaller; }
    public void    setFinTaller(Integer v)          { this.finTaller = v; }

    public String  getDiaLab()                      { return diaLab; }
    public void    setDiaLab(String v)              { this.diaLab = v; }
    public Integer getInicioLab()                   { return inicioLab; }
    public void    setInicioLab(Integer v)          { this.inicioLab = v; }
    public Integer getFinLab()                      { return finLab; }
    public void    setFinLab(Integer v)             { this.finLab = v; }
}