package co.edu.uniquindio.sga.infrastructure.persistence.jpa;

import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.repository.ApartamentoRepository;
import co.edu.uniquindio.sga.domain.valueobject.IdentificacionApartamento;
import co.edu.uniquindio.sga.infrastructure.persistence.jpa.mapper.ApartamentoPersistenceMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class ApartamentoJpaRepository implements ApartamentoRepository {

    private final ApartamentoJpaDataRepository dataRepository;
    private final ApartamentoPersistenceMapper mapper;

    public ApartamentoJpaRepository(ApartamentoJpaDataRepository dataRepository, ApartamentoPersistenceMapper mapper) {
        this.dataRepository = dataRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Apartamento> obtenerPorIdentificacion(IdentificacionApartamento identificacion) {
        return dataRepository.findById(identificacion.valor())
                .map(mapper::toDomain);
    }

    @Override
    public List<Apartamento> obtenerTodos() {
        return dataRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void guardar(Apartamento apartamento) {
        dataRepository.save(mapper.toEntity(apartamento));
    }
}