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
import java.util.List;

@Named("eliminarAsignacionBean")
@ViewScoped
public class EliminarAsignacionBean implements Serializable {

    private final FacadeAsignacion facade = new FacadeAsignacion();

    private List<Profesor>          profesores;
    private List<UnidadAprendizaje> unidades;

    private Integer idProfesorSel;
    private Integer idUnidadSel;
    private boolean asignacionEncontrada = false;


    private Integer idAsignacionClase;
    private Integer idAsignacionTaller;
    private Integer idAsignacionLab;


    private String  diaClase;
    private Integer inicioClase;
    private Integer finClase;
    private String  diaTaller;
    private Integer inicioTaller;
    private Integer finTaller;
    private String  diaLab;
    private Integer inicioLab;
    private Integer finLab;

    @PostConstruct
    public void init() {
        profesores = facade.obtenerProfesores();
        unidades   = facade.obtenerUnidades();
    }


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
        List<Asignacion> lista = facade.buscarPorProfesorYUnidad(profesor, idUnidadSel);

        if (lista == null || lista.isEmpty()) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se encontró asignación para esa combinación"));
            return;
        }


        idAsignacionClase = null; diaClase = null; inicioClase = null; finClase = null;
        idAsignacionTaller = null; diaTaller = null; inicioTaller = null; finTaller = null;
        idAsignacionLab = null; diaLab = null; inicioLab = null; finLab = null;

        if (lista.size() > 0) {
            Asignacion a = lista.get(0);
            idAsignacionClase = a.getId();
            diaClase    = a.getDiaSemana();
            inicioClase = a.getHoraInicio().getHour();
            finClase    = a.getHoraFin().getHour();
        }
        if (lista.size() > 1) {
            Asignacion a = lista.get(1);
            idAsignacionTaller = a.getId();
            diaTaller    = a.getDiaSemana();
            inicioTaller = a.getHoraInicio().getHour();
            finTaller    = a.getHoraFin().getHour();
        }
        if (lista.size() > 2) {
            Asignacion a = lista.get(2);
            idAsignacionLab = a.getId();
            diaLab    = a.getDiaSemana();
            inicioLab = a.getHoraInicio().getHour();
            finLab    = a.getHoraFin().getHour();
        }

        asignacionEncontrada = true;
        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Éxito", "Asignación encontrada"));
    }


    public void eliminarTodo() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        int eliminados = 0;

        try {
            if (idAsignacionClase != null)  { facade.eliminar(idAsignacionClase);  eliminados++; }
            if (idAsignacionTaller != null) { facade.eliminar(idAsignacionTaller); eliminados++; }
            if (idAsignacionLab != null)    { facade.eliminar(idAsignacionLab);    eliminados++; }

            // Limpiar estado
            idAsignacionClase = null; diaClase = null; inicioClase = null; finClase = null;
            idAsignacionTaller = null; diaTaller = null; inicioTaller = null; finTaller = null;
            idAsignacionLab = null; diaLab = null; inicioLab = null; finLab = null;
            asignacionEncontrada = false;

            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito", eliminados + " asignación(es) eliminada(s) correctamente"));

        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se pudo eliminar: " + e.getMessage()));
        }
    }


    public List<Profesor>   getProfesores(){ return profesores; }
    public List<UnidadAprendizaje> getUnidades(){ return unidades; }
    public boolean    isAsignacionEncontrada() { return asignacionEncontrada; }

    public Integer getIdProfesorSel(){ return idProfesorSel; }
    public void    setIdProfesorSel(Integer v) { this.idProfesorSel = v; }
    public Integer getIdUnidadSel(){ return idUnidadSel; }
    public void    setIdUnidadSel(Integer v)   { this.idUnidadSel = v; }

    public Integer getIdAsignacionClase(){ return idAsignacionClase; }
    public Integer getIdAsignacionTaller(){ return idAsignacionTaller; }
    public Integer getIdAsignacionLab(){ return idAsignacionLab; }

    public String  getDiaClase(){ return diaClase; }
    public Integer getInicioClase(){ return inicioClase; }
    public Integer getFinClase() { return finClase; }

    public String  getDiaTaller() { return diaTaller; }
    public Integer getInicioTaller(){ return inicioTaller; }
    public Integer getFinTaller() { return finTaller; }

    public String  getDiaLab() { return diaLab; }
    public Integer getInicioLab() { return inicioLab; }
    public Integer getFinLab() { return finLab; }
}