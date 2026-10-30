package co.edu.uniquindio.sga.application.usecase;

import co.edu.uniquindio.sga.application.dto.response.ApartamentoResponse;
import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.repository.ApartamentoRepository;
import co.edu.uniquindio.sga.domain.service.DisponibilidadApartamentoService;
import co.edu.uniquindio.sga.domain.valueobject.Estancia;
import co.edu.uniquindio.sga.domain.valueobject.TiempoPreparacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultarApartamentosDisponiblesUseCase {

    private final ApartamentoRepository apartamentoRepository;
    private final DisponibilidadApartamentoService disponibilidadService;
    private final TiempoPreparacion tiempoPreparacion;

    public ConsultarApartamentosDisponiblesUseCase(
            ApartamentoRepository apartamentoRepository,
            DisponibilidadApartamentoService disponibilidadService,
            TiempoPreparacion tiempoPreparacion) {
        this.apartamentoRepository = apartamentoRepository;
        this.disponibilidadService = disponibilidadService;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Transactional(readOnly = true)
    public List<ApartamentoResponse> ejecutar(LocalDate fechaEntrada, LocalDate fechaSalida, int numeroPersonas) {
        Estancia estancia = new Estancia(fechaEntrada, fechaSalida);
        
        List<Apartamento> todos = apartamentoRepository.obtenerTodos();

        return todos.stream()
                .filter(Apartamento::estaActivo)
                .filter(apt -> apt.admite(numeroPersonas))
                .filter(apt -> disponibilidadService.estaDisponible(apt.getIdentificacion(), estancia, tiempoPreparacion))
                .map(apt -> new ApartamentoResponse(
                        apt.getIdentificacion().valor(),
                        apt.getNombre(),
                        apt.getDormitorios(),
                        apt.getCapacidad(),
                        apt.getEstadoOperativo().name(),
                        apt.estaActivo()
                ))
                .toList();
    }
}