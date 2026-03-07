package ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.inject.Named;
import mx.sauap_db.desarrollo.facade.FacadeAsignacion;

import java.io.Serializable;
import java.util.List;

@Named("profesorBean")
@SessionScoped
public class ProfesorBean implements Serializable {

    private FacadeAsignacion facade = new FacadeAsignacion();
    private String nombreProfesor = "";
    private String horarioJson = "[]";

    public void cargarHorario(ComponentSystemEvent event) {
        System.out.println("LOG: cargarHorario EJECUTADO");

        FacesContext ctx = FacesContext.getCurrentInstance();


        LoginBean loginBean = ctx.getApplication()
                .evaluateExpressionGet(ctx, "#{loginBean}", LoginBean.class);

        System.out.println("LOG: loginBean = " + loginBean);
        System.out.println("LOG: usuario = " + (loginBean != null ? loginBean.getUsuario() : "null"));

        if (loginBean == null || loginBean.getUsuario() == null) {
            System.out.println("LOG: retornando por loginBean nulo");
            return;
        }

        int idProfesor = 1123;
        List<Object[]> rows = facade.getHorarioProfesor(idProfesor);
        System.out.println("LOG horario: filas encontradas = " + rows.size());

        if (!rows.isEmpty()) {
            Object[] primera = rows.get(0);
            nombreProfesor = primera[0] + " " + primera[1];
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < rows.size(); i++) {
            Object[] r = rows.get(i);
            sb.append("{")
                    .append("\"nombre_unidad\":\"").append(r[2]).append("\",")
                    .append("\"horas_clase\":").append(r[3]).append(",")
                    .append("\"horas_taller\":").append(r[4]).append(",")
                    .append("\"horas_laboratorio\":").append(r[5]).append(",")
                    .append("\"dia_semana\":\"").append(r[6]).append("\",")
                    .append("\"hora_inicio\":\"").append(r[7]).append("\"")
                    .append("}");
            if (i < rows.size() - 1) sb.append(",");
        }
        horarioJson = sb.append("]").toString();

        System.out.println("LOG horarioJson = " + horarioJson);
    }

    public String getNombreProfesor() { return nombreProfesor; }
    public String getHorarioJson()    { return horarioJson; }
}