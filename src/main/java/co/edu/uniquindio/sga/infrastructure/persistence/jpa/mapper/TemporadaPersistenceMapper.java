package co.edu.uniquindio.sga.infrastructure.persistence.jpa.mapper;

import co.edu.uniquindio.sga.domain.entity.Temporada;
import co.edu.uniquindio.sga.domain.valueobject.IdTemporada;
import co.edu.uniquindio.sga.domain.valueobject.Periodo;
import co.edu.uniquindio.sga.infrastructure.persistence.jpa.entity.TemporadaEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TemporadaPersistenceMapper {

    public TemporadaEntity toEntity(Temporada dominio) {
        if (dominio == null) {
            return null;
        }

        return new TemporadaEntity(
            dominio.getId().valor(),
            dominio.getNombre(),
            dominio.getPeriodo().desde(),
            dominio.getPeriodo().hasta(),
            BigDecimal.ZERO
        );
    }

    public Temporada toDomain(TemporadaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Temporada(
            new IdTemporada(entity.getId()),
            entity.getNombre(),
            new Periodo(entity.getFechaInicio(), entity.getFechaFin()),
            false
        );
    }
}