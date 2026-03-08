package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import mx.sauap_db.desarrollo.persistence.AbstractDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.Asignacion;
import mx.sauap_db.entity.Profesor;
import mx.sauap_db.entity.UnidadAprendizaje;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class AsignacionDAO extends AbstractDAO<Asignacion> {

    public AsignacionDAO() {
        super(Asignacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }
    public void guardar(Asignacion asignacion) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(asignacion);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ── NUEVO: asignaciones de un profesor ────────────────────────────────────
    public List<Asignacion> buscarPorProfesor(Profesor profesor) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT a FROM Asignacion a WHERE a.idProfesor = :prof",
                            Asignacion.class)
                    .setParameter("prof", profesor)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    // ── NUEVO: lista de profesores para el select ─────────────────────────────
    public List<Profesor> obtenerProfesores() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT p FROM Profesor p ORDER BY p.apellidoPaterno",
                            Profesor.class)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    // ── NUEVO: lista de unidades para el select ───────────────────────────────
    public List<UnidadAprendizaje> obtenerUnidades() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT u FROM UnidadAprendizaje u ORDER BY u.nombreUnidad",
                            UnidadAprendizaje.class)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    // ── NUEVO: buscar Profesor por id ─────────────────────────────────────────
    public Profesor buscarProfesor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profesor.class, id);
        } finally {
            em.close();
        }
    }

    // ── NUEVO: buscar UnidadAprendizaje por id ────────────────────────────────
    public UnidadAprendizaje buscarUnidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UnidadAprendizaje.class, id);
        } finally {
            em.close();
        }
    }


    //nuevos
    public List<Asignacion> buscarPorProfesorYUnidad(Profesor profesor, Integer idUnidad) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT a FROM Asignacion a " +
                                    "WHERE a.idProfesor = :prof AND a.idUnidad.id = :unidad " +
                                    "ORDER BY a.id ASC",
                            Asignacion.class)
                    .setParameter("prof", profesor)
                    .setParameter("unidad", idUnidad)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    // Buscar una asignación por su PK
    public Asignacion buscarAsignacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asignacion.class, id);
        } finally {
            em.close();
        }
    }

    // Actualizar (merge) una asignación existente
    public void actualizar(Asignacion asignacion) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(asignacion);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Verifica traslape excluyendo el registro que se está editando
    public boolean existeTraslapeExcluyendo(Profesor profesor, String diaSemana,
                                            LocalTime horaInicio, LocalTime horaFin,
                                            Integer idExcluir) {
        for (Asignacion a : buscarPorProfesor(profesor)) {
            if (idExcluir != null && a.getId().equals(idExcluir)) continue;
            if (a.getDiaSemana().equals(diaSemana)) {
                if (horaInicio.isBefore(a.getHoraFin()) && a.getHoraInicio().isBefore(horaFin)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void eliminar(Integer id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Asignacion a = em.find(Asignacion.class, id);
            if (a != null) em.remove(a);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ── Traslape normal (para registrar) ──────────────────────────────────────
    public boolean existeTraslape(Profesor profesor, String diaSemana,
                                  LocalTime horaInicio, LocalTime horaFin) {
        for (Asignacion a : buscarPorProfesor(profesor)) {
            if (a.getDiaSemana().equals(diaSemana)) {
                if (horaInicio.isBefore(a.getHoraFin()) && a.getHoraInicio().isBefore(horaFin)) {
                    return true;
                }
            }
        }
        return false;
    }
}
