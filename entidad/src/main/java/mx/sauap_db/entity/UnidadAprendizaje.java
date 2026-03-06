package mx.sauap_db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "unidad_aprendizaje")
public class UnidadAprendizaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombre_unidad", nullable = false, length = 50)
    private String nombreUnidad;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "horas_clase", nullable = false)
    private Integer horasClase;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "horas_taller", nullable = false)
    private Integer horasTaller;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "horas_laboratorio", nullable = false)
    private Integer horasLaboratorio;

    public UnidadAprendizaje() {}

    public UnidadAprendizaje(String nombreUnidad, int horasClase, int horasTaller, int horasLaboratorio) {
        this.nombreUnidad = nombreUnidad;
        this.horasClase = horasClase;
        this.horasTaller = horasTaller;
        this.horasLaboratorio = horasLaboratorio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public Integer getHorasClase() {
        return horasClase;
    }

    public void setHorasClase(Integer horasClase) {
        this.horasClase = horasClase;
    }

    public Integer getHorasTaller() {
        return horasTaller;
    }

    public void setHorasTaller(Integer horasTaller) {
        this.horasTaller = horasTaller;
    }

    public Integer getHorasLaboratorio() {
        return horasLaboratorio;
    }

    public void setHorasLaboratorio(Integer horasLaboratorio) {
        this.horasLaboratorio = horasLaboratorio;
    }

}