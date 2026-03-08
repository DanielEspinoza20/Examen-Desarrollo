package mx.sauap_db.desarrollo.dao;

import jakarta.persistence.EntityManager;
import mx.sauap_db.desarrollo.persistence.AbstractDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.UnidadAprendizaje;
import java.util.List;

public class UnidadAprendizajeDAO extends AbstractDAO<UnidadAprendizaje> {

    public void registrar(UnidadAprendizaje u) {
        save(u);
    }

    public void modificar(UnidadAprendizaje u) {
        update(u);
    }

    public void eliminar(int id) {
        find(id).ifPresent(this::delete);
    }

    public UnidadAprendizaje buscarPorId(int id) {
        return find(id).orElse(null);
    }

    public List<UnidadAprendizaje> listarTodas() {
        List<UnidadAprendizaje> lista = findAll();
        System.out.println(">>> resultado: " + lista.size());
        return lista; // ← devuelve la que ya tienes
    }

    public UnidadAprendizajeDAO() {
        super(UnidadAprendizaje.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }
}