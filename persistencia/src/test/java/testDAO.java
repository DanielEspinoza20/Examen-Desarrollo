
import mx.sauap_db.desarrollo.dao.ProfesorDAO;
import mx.sauap_db.desarrollo.persistence.HibernateUtil;
import mx.sauap_db.entity.Profesor;

public class testDAO {

    public static void main(String[] args) {
        ProfesorDAO ProfesorDAO = new ProfesorDAO();



        for (Profesor Profesor : ProfesorDAO.findAll()) {
            System.out.println(Profesor.getNombre()+ " " + Profesor.getApellidoPaterno() + "|| id [" + Profesor.getId()+ "]");
        }
    }
}
