package co.edu.uniquindio.sga.domain.service;

import co.edu.uniquindio.sga.domain.entity.Bloqueo;
import co.edu.uniquindio.sga.domain.entity.Reserva;
import co.edu.uniquindio.sga.domain.exception.ReglaDominioException;
import co.edu.uniquindio.sga.domain.repository.BloqueoRepository;
import co.edu.uniquindio.sga.domain.repository.ReservaRepository;
import co.edu.uniquindio.sga.domain.valueobject.Estancia;
import co.edu.uniquindio.sga.domain.valueobject.HorarioAlojamiento;
import co.edu.uniquindio.sga.domain.valueobject.IdentificacionApartamento;
import co.edu.uniquindio.sga.domain.valueobject.Periodo;
import co.edu.uniquindio.sga.domain.valueobject.TiempoPreparacion;

import java.util.List;

/** Servicio de dominio (Guía 05) */
public class DisponibilidadApartamentoService {

    private final ReservaRepository reservaRepository;
    private final BloqueoRepository bloqueoRepository;
    private final HorarioAlojamiento horario;

    public DisponibilidadApartamentoService(ReservaRepository reservaRepository, BloqueoRepository bloqueoRepository, HorarioAlojamiento horario) {
        this.reservaRepository = reservaRepository;
        this.bloqueoRepository = bloqueoRepository;
        this.horario = horario;
    }

    public boolean estaDisponible(IdentificacionApartamento apartamento, Estancia estancia, TiempoPreparacion preparacion) {
        Periodo periodo = new Periodo(estancia.fechaEntrada(), estancia.fechaSalida());

        List<Reserva> activas = reservaRepository.buscarActivasPorApartamento(apartamento, periodo);
        for (Reserva otra : activas) {
            // RN-12: una reserva que dejó de estar activa no ocupa noches
            if (!otra.estaActiva()) {
                continue;
            }
            // RN-01: sin importar el canal de origen
            if (estancia.seSolapaCon(otra.getEstancia())) {
                return false;
            }
            // RN-20
            if (violaTiempoPreparacion(estancia, otra.getEstancia(), preparacion)) {
                return false;
            }
        }

        // RN-07
        List<Bloqueo> bloqueos = bloqueoRepository.buscarVigentesPorApartamento(apartamento, periodo);
        for (Bloqueo bloqueo : bloqueos) {
            if (bloqueo.afecta(estancia)) {
                return false;
            }
        }

        return true;
    }

    public void verificarDisponibilidad(IdentificacionApartamento apartamento, Estancia estancia, TiempoPreparacion preparacion) {
        if (!estaDisponible(apartamento, estancia, preparacion)) {
            throw new ReglaDominioException(
                    "El apartamento no está disponible para la estancia solicitada.");
        }
    }

    // RN-20: entre la salida de un grupo y la entrada del siguiente debe respetarse
    // el tiempo de preparación configurado
    private boolean violaTiempoPreparacion(Estancia nueva, Estancia otra, TiempoPreparacion preparacion) {
        if (preparacion.horas() == 0) {
            return false;
        }

        // Si una reserva entra el mismo día que otra sale, calculamos la brecha de horas exactas
        if (otra.fechaSalida().isEqual(nueva.fechaEntrada()) || nueva.fechaSalida().isEqual(otra.fechaEntrada())) {
            long horasDisponibles = java.time.Duration.between(
                    horario.horaSalida(),
                    horario.horaEntrada()
            ).toHours();

            if (horasDisponibles < preparacion.horas()) {
                return true;
            }
        }
        return false;
    }
}