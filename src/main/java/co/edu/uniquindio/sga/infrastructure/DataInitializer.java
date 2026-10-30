package co.edu.uniquindio.sga.infrastructure;

import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.repository.ApartamentoRepository;
import co.edu.uniquindio.sga.domain.valueobject.IdentificacionApartamento;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ApartamentoRepository apartamentoRepository;

    public DataInitializer(ApartamentoRepository apartamentoRepository) {
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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

            System.out.println(">>> Datos de prueba cargados automáticamente en H2 (Apartamentos) <<<");
        }
    }
}