package co.edu.uniquindio.sga.infrastructure.persistence.jpa.entity;

import co.edu.uniquindio.sga.domain.valueobject.EstadoOperativo;
import jakarta.persistence.*;

@Entity
@Table(name = "apartamentos")
public class ApartamentoEntity {

    @Id
    @Column(name = "identificacion", nullable = false, length = 50)
    private String identificacion;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "dormitorios", nullable = false)
    private int dormitorios;

    @Column(name = "capacidad", nullable = false)
    private int capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_operativo", nullable = false, length = 30)
    private EstadoOperativo estadoOperativo;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    public ApartamentoEntity() {
    }

    public ApartamentoEntity(String identificacion, String nombre, int dormitorios, int capacidad, EstadoOperativo estadoOperativo, boolean activo) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.dormitorios = dormitorios;
        this.capacidad = capacidad;
        this.estadoOperativo = estadoOperativo;
        this.activo = activo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(int dormitorios) {
        this.dormitorios = dormitorios;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoOperativo getEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(EstadoOperativo estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}