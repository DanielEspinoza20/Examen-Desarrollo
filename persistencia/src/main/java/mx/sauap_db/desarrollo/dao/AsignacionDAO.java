package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import mx.sauap_db.desarrollo.persistence.AbstractDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.Asignacion;

import java.util.List;

public class AsignacionDAO extends AbstractDAO<Asignacion> {
    @PersistenceContext
    private EntityManager em;

    public AsignacionDAO() {
        super(Asignacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    public List<Asignacion> obtenerUnidadesConHoras() {
        return execute(em ->
                em.createQuery(
                        "SELECT a FROM Asignacion a JOIN FETCH a.idUnidad",
                        Asignacion.class
                ).getResultList()
        );
    }

    public List<Object[]> getHorarioProfesor(int idProfesor) {
        String sql =
                "SELECT p.nombre, p.apellido_paterno, " +
                        "u.nombre_unidad, u.horas_clase, u.horas_taller, u.horas_laboratorio, " +
                        "a.dia_semana, a.hora_inicio " +
                        "FROM Asignacion a " +
                        "JOIN Profesor p ON a.id_profesor = p.id_profesor " +
                        "JOIN Unidad_Aprendizaje u ON a.id_unidad = u.id_unidad " +
                        "WHERE a.id_profesor = :idProfesor";

        return (List<Object[]>) getEntityManager()
                .createNativeQuery(sql)
                .setParameter("idProfesor", idProfesor)
                .getResultList();
    }

}
