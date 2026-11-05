package co.edu.uniquindio.sga.infrastructure.persistence.jpa;

import co.edu.uniquindio.sga.infrastructure.persistence.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // <-- Esta es la importación que faltaba

@Repository
public interface UsuarioJpaDataRepository extends JpaRepository<UsuarioEntity, String> {
    Optional<UsuarioEntity> findByCorreo(String correo);
}