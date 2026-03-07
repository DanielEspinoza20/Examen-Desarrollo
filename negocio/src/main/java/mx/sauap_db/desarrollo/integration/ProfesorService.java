package mx.sauap_db.desarrollo.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.sauap_db.desarrollo.dao.AsignacionDAO;

@ApplicationScoped
public class ProfesorService {

    @Inject
    private AsignacionDAO asignacionDAO;

    public List<Map<String, Object>> getHorarioJson(int idProfesor) {
        List<Object[]> rows = asignacionDAO.getHorarioProfesor(idProfesor);
        List<Map<String, Object>> lista = new ArrayList<>();

        for (Object[] r : rows) {
            Map<String, Object> m = new HashMap<>();
            m.put("nombre_profesor",   r[0] + " " + r[1]);
            m.put("nombre_unidad",     r[2]);
            m.put("horas_clase",       r[3]);
            m.put("horas_taller",      r[4]);
            m.put("horas_laboratorio", r[5]);
            m.put("dia_semana",        r[6]);
            m.put("hora_inicio",       r[7].toString());
            lista.add(m);
        }
        return lista;
    }
}