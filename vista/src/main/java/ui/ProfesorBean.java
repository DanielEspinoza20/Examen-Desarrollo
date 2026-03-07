package ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.sauap_db.desarrollo.facade.FacadeProfesor;
import mx.sauap_db.entity.Profesor;

import java.io.Serializable;

@Named("profesorBean")
@SessionScoped
public class ProfesorBean implements Serializable {

    //Facade
    private final FacadeProfesor facade = new FacadeProfesor();

    //Estado del formulario
    private Profesor profesor = new Profesor();
    private String rfcBusqueda;
    private boolean profesorEncontrado = false;
    private boolean busquedaRealizada = false;
    private String mensajeBusqueda = "Introduce el RFC y presiona Buscar";

    //REGISTRAR
    public void guardar() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (facade.buscarPorRfc(profesor.getRfc()) != null) {
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "El RFC ya está registrado en el sistema"));
            return;
        }

        try {
            facade.registrar(profesor);
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Éxito", "Profesor registrado correctamente"));
            profesor = new Profesor(); // reset form
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar: " + e.getMessage()));
        }
    }

    //BUSCAR
    public void buscar() {
        busquedaRealizada  = true;
        profesorEncontrado = false;

        if (rfcBusqueda == null || rfcBusqueda.trim().isEmpty()) {
            mensajeBusqueda = "Introduce un RFC para buscar";
            return;
        }

        Profesor encontrado = facade.buscarPorRfc(rfcBusqueda.trim().toUpperCase());
        if (encontrado != null) {
            profesor = encontrado;
            profesorEncontrado = true;
            mensajeBusqueda = "✓ Profesor encontrado";
        } else {
            profesor = new Profesor();
            mensajeBusqueda = "No se encontró ningún profesor con ese RFC";
        }
    }

    //MODIFICAR
    public void actualizar() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (!facade.rfcDisponible(profesor.getRfc(), profesor.getId())) {
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "El RFC ya pertenece a otro profesor"));
            return;
        }

        try {
            facade.modificar(profesor);
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Éxito", "Profesor modificado correctamente"));
            resetBusqueda();
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "No se pudo modificar: " + e.getMessage()));
        }
    }

    //RESET
    private void resetBusqueda() {
        profesor = new Profesor();
        rfcBusqueda = null;
        profesorEncontrado = false;
        busquedaRealizada = false;
        mensajeBusqueda = "Introduce el RFC y presiona Buscar";
    }

    //Getters/Setters
    public Profesor getProfesor(){
        return profesor;
    }

    public void setProfesor(Profesor p){
        this.profesor = p;
    }

    public String getRfcBusqueda(){
        return rfcBusqueda;
    }

    public void setRfcBusqueda(String r){
        this.rfcBusqueda = r;
    }

    public boolean isProfesorEncontrado(){
        return profesorEncontrado;
    }

    public boolean isBusquedaRealizada(){
        return busquedaRealizada;
    }

    public String getMensajeBusqueda(){
        return mensajeBusqueda;
    }
}
