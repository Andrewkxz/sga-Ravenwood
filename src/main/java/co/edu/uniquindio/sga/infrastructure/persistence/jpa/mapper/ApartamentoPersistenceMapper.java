package co.edu.uniquindio.sga.infrastructure.persistence.jpa.mapper;

import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.valueobject.IdentificacionApartamento;
import co.edu.uniquindio.sga.infrastructure.persistence.jpa.entity.ApartamentoEntity;
import org.springframework.stereotype.Component;

@Component
public class ApartamentoPersistenceMapper {

    public ApartamentoEntity toEntity(Apartamento dominio) {
        if (dominio == null) {
            return null;
        }
        return new ApartamentoEntity(
            dominio.getIdentificacion().valor(),
            dominio.getNombre(),
            dominio.getDormitorios(),
            dominio.getCapacidad(),
            dominio.getEstadoOperativo(),
            dominio.estaActivo()
        );
    }

    public Apartamento toDomain(ApartamentoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Apartamento apartamento = new Apartamento(
            new IdentificacionApartamento(entity.getIdentificacion()),
            entity.getNombre(),
            entity.getDormitorios(),
            entity.getCapacidad()
        );

        if (!entity.isActivo()) {
            apartamento.desactivar();
        }

        return apartamento;
    }
}