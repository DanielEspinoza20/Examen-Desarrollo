package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
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

@Named("asignacionBean")
@ViewScoped
public class AsignacionBean implements Serializable {

    // ── NUEVO: todo el contenido ───────────────────────────────────────────────
    private final FacadeAsignacion facade = new FacadeAsignacion();

    private List<Profesor> profesores;
    private List<UnidadAprendizaje> unidades;

    private Integer idProfesorSel;
    private Integer idUnidadSel;

    private String  diaClase;    private Integer inicioClase;   private Integer finClase;
    private String  diaTaller;   private Integer inicioTaller;  private Integer finTaller;
    private String  diaLab;      private Integer inicioLab;     private Integer finLab;

    @PostConstruct
    public void init() {
        profesores = facade.obtenerProfesores();
        unidades   = facade.obtenerUnidades();
    }

    public void asignarClase()  { asignar(diaClase,  inicioClase,  finClase,  "Clase");       }
    public void asignarTaller() { asignar(diaTaller, inicioTaller, finTaller, "Taller");      }
    public void asignarLab()    { asignar(diaLab,    inicioLab,    finLab,    "Laboratorio"); }

    private void asignar(String dia, Integer inicio, Integer fin, String tipo) {
        FacesContext ctx = FacesContext.getCurrentInstance();

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
            Profesor profesor        = facade.buscarProfesor(idProfesorSel);
            UnidadAprendizaje unidad = facade.buscarUnidad(idUnidadSel);
            LocalTime horaInicio     = LocalTime.of(inicio, 0);
            LocalTime horaFin        = LocalTime.of(fin, 0);

            if (facade.existeTraslape(profesor, dia, horaInicio, horaFin)) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", "Traslape: el profesor ya tiene asignación en "
                        + dia + " " + inicio + ":00–" + fin + ":00"));
                return;
            }

            Asignacion a = new Asignacion();
            a.setIdProfesor(profesor);
            a.setIdUnidad(unidad);
            a.setDiaSemana(dia);
            a.setHoraInicio(horaInicio);
            a.setHoraFin(horaFin);

            facade.registrar(a);

            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito", tipo + " asignado: " + dia + " " + inicio + ":00–" + fin + ":00"));

        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se pudo registrar: " + e.getMessage()));
        }
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────
    public List<Profesor> getProfesores()           { return profesores; }
    public List<UnidadAprendizaje> getUnidades()    { return unidades; }

    public Integer getIdProfesorSel()               { return idProfesorSel; }
    public void setIdProfesorSel(Integer v)         { this.idProfesorSel = v; }
    public Integer getIdUnidadSel()                 { return idUnidadSel; }
    public void setIdUnidadSel(Integer v)           { this.idUnidadSel = v; }

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