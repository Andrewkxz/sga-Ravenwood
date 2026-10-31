package co.edu.uniquindio.sga.infrastructure.persistence.jpa;

import co.edu.uniquindio.sga.domain.entity.Temporada;
import co.edu.uniquindio.sga.domain.repository.TemporadaRepository;
import co.edu.uniquindio.sga.domain.valueobject.IdTemporada;
import co.edu.uniquindio.sga.infrastructure.persistence.jpa.entity.TemporadaEntity;
import co.edu.uniquindio.sga.infrastructure.persistence.jpa.mapper.TemporadaPersistenceMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class TemporadaJpaRepository implements TemporadaRepository {

    private final TemporadaJpaDataRepository dataRepository;
    private final TemporadaPersistenceMapper mapper;

    public TemporadaJpaRepository(TemporadaJpaDataRepository dataRepository, TemporadaPersistenceMapper mapper) {
        this.dataRepository = dataRepository;
        this.mapper = mapper;
    }

    public Optional<Temporada> obtenerPorId(IdTemporada id) {
        return dataRepository.findById(id.valor())
                .map(mapper::toDomain);
    }

    @Override
    public List<Temporada> buscarTodas() {
        return dataRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    public Optional<Temporada> buscarVigenteEn(LocalDate fecha) {
        return dataRepository.findAll().stream()
                .map(mapper::toDomain)
                .filter(t -> t.getPeriodo().contiene(fecha))
                .findFirst();
    }

    public void guardar(Temporada temporada) {
        dataRepository.save(mapper.toEntity(temporada));
    }
}