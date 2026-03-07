package ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.sauap_db.desarrollo.facade.FacadeUnidadAprendizaje;
import mx.sauap_db.entity.UnidadAprendizaje;

import java.io.Serializable;
import java.util.List;

@Named("unidadBean")
@SessionScoped
public class UnidadBean implements Serializable{
    //Facade
    private final FacadeUnidadAprendizaje facade = new FacadeUnidadAprendizaje();

    //Estado
    private UnidadAprendizaje unidad = new UnidadAprendizaje();
    private String nombreBusqueda;
    private boolean unidadEncontrada = false;
    private boolean bosquedaRealiza = false;
    private String mensajeBusqueda = "Introduce el nombre y presiona Buscar";

    //Registrar
    public void guardar(){
        FacesContext ctx = FacesContext.getCurrentInstance();

        if(!facade.nombreDisponible(unidad.getNombreUnidad(), 0)){
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR", "El nombre de la unidad ya esta registrado"
            ));
            return;
        }
        try{
            facade.registrar(unidad);
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Exito", "Unidad registrado correctamente"));
            unidad = new UnidadAprendizaje();
        }catch(Exception e){
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR", "no se pudo registrar"));
        }
    }

    //BUSCAR
    public void buscar(){
        busquedaRealizada = true;
        unidadEncontrada = false;

        if (nombreBusqueda == null || nombreBusqueda.trim().isEmpty()){
            mensajeBusqueda = "Introduce un nombre para buscar";
            return;
        }

        UnidadAprendizaje encontrada = facade.buscarPorNombre(nombreBusqueda.trim());
        if(encontrada != null){
            unidad = encontrada;
            unidadEncontrada = true;
            mensajeBusqueda = "Unidad encontrada";
        }else{
            unidad = new UnidadAprendizaje();
            mensajeBusqueda = "No se encontro ningun unidad con ese nombre";
        }
    }

    //MODIFICAR
    public void actualizar(){
        FacesContext ctx = FacesContext.getCurrentInstance();

        if(!facade.nombreDisponible(unidad.getNombreUnidad(), unidad.getId())){
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR", "El nombre ya pertenece a otra unidad"));
            return;
        }

        try{
            facade.modificar(unidad);
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Exito", "Unidad modificada correctamente"));
            resetBusqueda();
        }catch(Exception e){
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR", "No se pudo modificar"));
        }
    }

    //Eliminar
    public void eliminarUnidadSeleccionada(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        if(unidad == null || unidad.getId() == null){
            ctx.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_WARN, "Aviso", "Primero busca unidad"));
            return;
        }
        try{
            facade.eliminar(unidad.getId());
            ctx.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Exito", "Unidad eliminada correctamente"));
            resetBusqueda();
        }catch (Exception e){
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR", "No se pudo eliminar"));
        }
    }
    //Consulta General
    public List<UnidadAprendizaje> listarTodas(){
        return facade.listarTodas();
    }

    //Reset
    private void resetBusqueda(){
        unidad = new UnidadAprendizaje();
        nombreBusqueda = null;
        unidadEncontrada = false;
        busquedaRealizada = false;
        mensajeBusqueda = "Introduce el nombre y presiona Buscar";
    }

    //Getters y Setters
    public UnidadAprendizaje getUnidad(){
        return unidad;
    }

    public void setUnidad(UnidadAprendizaje u){
        this.unidad = u;
    }

    public String getNombreBusqueda(){
        return nombreBusqueda;
    }

    public void setNombreBusqueda(String n){
        this.nombreBusqueda = n;
    }

    public boolean isUnidadEncontrada(){
        return unidadEncontrada;
    }

    public boolean isBusquedaRealizada(){
        return busquedaRealizada;
    }

    public String getMensajeBusqueda(){
        return mensajeBusqueda;
    }
}
