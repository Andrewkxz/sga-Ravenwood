package co.edu.uniquindio.sga.infrastructure.persistence.jpa;

import co.edu.uniquindio.sga.infrastructure.persistence.jpa.entity.ApartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartamentoJpaDataRepository extends JpaRepository<ApartamentoEntity, String> {
    List<ApartamentoEntity> findByActivoTrue();
}