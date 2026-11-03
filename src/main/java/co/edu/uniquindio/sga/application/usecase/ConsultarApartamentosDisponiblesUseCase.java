package co.edu.uniquindio.sga.application.usecase;

import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.exception.ReglaDominioException;
import co.edu.uniquindio.sga.domain.repository.ApartamentoRepository;
import co.edu.uniquindio.sga.domain.service.DisponibilidadApartamentoService;
import co.edu.uniquindio.sga.domain.valueobject.Estancia;
import co.edu.uniquindio.sga.domain.valueobject.TiempoPreparacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/** Caso de uso de solo consulta (Query) para listar apartamentos disponibles */
@Service
public class ConsultarApartamentosDisponiblesUseCase {

    private final ApartamentoRepository apartamentoRepository;
    private final DisponibilidadApartamentoService disponibilidadService;
    private final TiempoPreparacion tiempoPreparacion;

    public ConsultarApartamentosDisponiblesUseCase(ApartamentoRepository apartamentoRepository,
                                                   DisponibilidadApartamentoService disponibilidadService,
                                                   TiempoPreparacion tiempoPreparacion) {
        this.apartamentoRepository = apartamentoRepository;
        this.disponibilidadService = disponibilidadService;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Transactional(readOnly = true)
    public List<Apartamento> ejecutar(Estancia estancia, int cantidadPersonas) {
        return apartamentoRepository.buscarTodos().stream()
                .filter(apt -> apt.getCapacidad() >= cantidadPersonas)
                .filter(Apartamento::estaActivo) 
                .filter(apt -> {
                    try {
                        // Llama a la firma exacta que tiene tu servicio de dominio
                        disponibilidadService.verificarDisponibilidad(apt.getIdentificacion(), estancia, tiempoPreparacion);
                        return true;
                    } catch (ReglaDominioException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}