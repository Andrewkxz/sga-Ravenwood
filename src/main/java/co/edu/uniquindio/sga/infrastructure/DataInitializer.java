package co.edu.uniquindio.sga.infrastructure;

import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.entity.Temporada;
import co.edu.uniquindio.sga.domain.repository.ApartamentoRepository;
import co.edu.uniquindio.sga.domain.valueobject.IdentificacionApartamento;
import co.edu.uniquindio.sga.domain.valueobject.IdTemporada;
import co.edu.uniquindio.sga.domain.valueobject.Periodo;
import co.edu.uniquindio.sga.infrastructure.persistence.jpa.TemporadaJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ApartamentoRepository apartamentoRepository;
    private final TemporadaJpaRepository temporadaRepository;

    public DataInitializer(
            ApartamentoRepository apartamentoRepository,
            TemporadaJpaRepository temporadaRepository) {
        this.apartamentoRepository = apartamentoRepository;
        this.temporadaRepository = temporadaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Cargar Apartamentos de prueba
        if (apartamentoRepository.obtenerTodos().isEmpty()) {
            Apartamento apt1 = new Apartamento(
                new IdentificacionApartamento("APT-101"),
                "Apartamento Vista El Poblado 101",
                2,
                4
            );
            
            Apartamento apt2 = new Apartamento(
                new IdentificacionApartamento("APT-102"),
                "Apartamento Suite Ravenwood 102",
                3,
                6
            );

            apartamentoRepository.guardar(apt1);
            apartamentoRepository.guardar(apt2);
        }

        // 2. Cargar Temporada Regular de prueba (Sección 12.3)
        if (temporadaRepository.buscarTodas().isEmpty()) {
            Temporada temporadaRegular = new Temporada(
                new IdTemporada("TEMP-2026-REGULAR"),
                "Temporada Regular 2026",
                new Periodo(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31)),
                false
            );
            temporadaRepository.guardar(temporadaRegular);
        }

        System.out.println(">>> Datos de prueba cargados automáticamente en H2 (Apartamentos y Temporadas) <<<");
    }
}