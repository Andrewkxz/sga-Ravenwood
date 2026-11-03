package co.edu.uniquindio.sga.application.usecase;

import co.edu.uniquindio.sga.application.exception.ApartamentoNoEncontradoException;
import co.edu.uniquindio.sga.application.exception.ReservaNoEncontradaException;
import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.entity.Ocupante;
import co.edu.uniquindio.sga.domain.entity.Reserva;
import co.edu.uniquindio.sga.domain.repository.ApartamentoRepository;
import co.edu.uniquindio.sga.domain.repository.FolioRepository;
import co.edu.uniquindio.sga.domain.repository.ReservaRepository;
import co.edu.uniquindio.sga.domain.repository.TemporadaRepository;
import co.edu.uniquindio.sga.domain.service.DisponibilidadApartamentoService;
import co.edu.uniquindio.sga.domain.service.LiquidacionEstanciaService;
import co.edu.uniquindio.sga.domain.valueobject.CodigoReserva;
import co.edu.uniquindio.sga.domain.valueobject.Dinero;
import co.edu.uniquindio.sga.domain.valueobject.Estancia;
import co.edu.uniquindio.sga.domain.valueobject.IdentificacionApartamento;
import co.edu.uniquindio.sga.domain.valueobject.TiempoPreparacion;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Caso de uso para modificar una reserva activa (Guía 06, Guía 09) */
@Service
public class ModificarReservaUseCase {

    private final ReservaRepository reservaRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final FolioRepository folioRepository;
    private final TemporadaRepository temporadaRepository;
    private final DisponibilidadApartamentoService disponibilidadService;
    private final LiquidacionEstanciaService liquidacionService;
    private final TiempoPreparacion tiempoPreparacion;
    private final Dinero cargoMascota; 

    public ModificarReservaUseCase(ReservaRepository reservaRepository,
                                   ApartamentoRepository apartamentoRepository,
                                   FolioRepository folioRepository,
                                   TemporadaRepository temporadaRepository,
                                   DisponibilidadApartamentoService disponibilidadService,
                                   LiquidacionEstanciaService liquidacionService,
                                   TiempoPreparacion tiempoPreparacion,
                                   Dinero cargoMascota) { 
        this.reservaRepository = reservaRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.folioRepository = folioRepository;
        this.temporadaRepository = temporadaRepository;
        this.disponibilidadService = disponibilidadService;
        this.liquidacionService = liquidacionService;
        this.tiempoPreparacion = tiempoPreparacion;
        this.cargoMascota = cargoMascota;
    }

    @Transactional
    public Reserva ejecutar(CodigoReserva codigo, IdentificacionApartamento apartamentoId,
                            Estancia nuevaEstancia, Ocupante titular, List<Ocupante> ocupantes,
                            int cantidadMascotas, LocalDateTime ahora) {

        Reserva reserva = reservaRepository.obtenerPorCodigo(codigo)
                .orElseThrow(() -> new ReservaNoEncontradaException(codigo));

        Apartamento apartamento = apartamentoRepository.obtenerPorIdentificacion(apartamentoId)
                .orElseThrow(() -> new ApartamentoNoEncontradoException(apartamentoId));

        apartamento.verificarQueAceptaReservas();

        disponibilidadService.verificarDisponibilidad(apartamentoId, nuevaEstancia, tiempoPreparacion);

        Dinero nuevoValorTotal = liquidacionService
                .liquidar(apartamento, nuevaEstancia, ocupantes, cantidadMascotas, this.cargoMascota, temporadaRepository.buscarTodas())
                .total();

        reserva.modificarEstancia(
                nuevaEstancia,
                nuevoValorTotal,
                apartamento.getCapacidad(),
                ahora.toLocalDate()
        );

        reservaRepository.guardar(reserva);

        folioRepository.obtenerPorReserva(codigo).ifPresent(folio -> {
            folioRepository.guardar(folio);
        });

        return reserva;
    }
}