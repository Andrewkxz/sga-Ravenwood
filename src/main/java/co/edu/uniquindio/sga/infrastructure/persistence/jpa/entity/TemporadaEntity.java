package co.edu.uniquindio.sga.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "temporadas")
public class TemporadaEntity {

    @Id
    @Column(name = "id_temporada", nullable = false, length = 50)
    private String id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "precio_base_noche", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioBaseNoche;

    public TemporadaEntity() {
    }

    public TemporadaEntity(String id, String nombre, LocalDate fechaInicio, LocalDate fechaFin, BigDecimal precioBaseNoche) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioBaseNoche = precioBaseNoche;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public BigDecimal getPrecioBaseNoche() { return precioBaseNoche; }
    public void setPrecioBaseNoche(BigDecimal precioBaseNoche) { this.precioBaseNoche = precioBaseNoche; }
}