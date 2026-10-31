package co.edu.uniquindio.sga.application.usecase;

import co.edu.uniquindio.sga.domain.entity.Reserva;
import co.edu.uniquindio.sga.domain.repository.ReservaRepository;
import co.edu.uniquindio.sga.domain.valueobject.EstadoReserva;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Caso de uso (Guía 06), servicio de Spring con transacción (Guía 09) */
@Service
public class ConsultarReservasPorEstadoUseCase {

    private final ReservaRepository reservaRepository;

    public ConsultarReservasPorEstadoUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Transactional(readOnly = true)
    public List<Reserva> ejecutar(EstadoReserva estado) {
        return reservaRepository.buscarPorEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Reserva> ejecutarPaginado(EstadoReserva estado, int pagina, int tamano) {
        List<Reserva> todas = reservaRepository.buscarPorEstado(estado);
        if (todas.isEmpty()) {
            return List.of();
        }
        int desde = Math.min(pagina * tamano, todas.size());
        int hasta = Math.min(desde + tamano, todas.size());
        return todas.subList(desde, hasta);
    }
}