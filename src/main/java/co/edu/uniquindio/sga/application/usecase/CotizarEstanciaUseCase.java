package co.edu.uniquindio.sga.application.usecase;

import co.edu.uniquindio.sga.application.exception.ApartamentoNoEncontradoException;
import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.entity.Ocupante;
import co.edu.uniquindio.sga.domain.repository.ApartamentoRepository;
import co.edu.uniquindio.sga.domain.repository.TemporadaRepository;
import co.edu.uniquindio.sga.domain.service.LiquidacionEstanciaService;
import co.edu.uniquindio.sga.domain.valueobject.Dinero;
import co.edu.uniquindio.sga.domain.valueobject.Estancia;
import co.edu.uniquindio.sga.domain.valueobject.IdentificacionApartamento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Caso de uso de solo consulta (Query) para cotizar el valor de la estancia (Guía 07, 4.1) */
@Service
public class CotizarEstanciaUseCase {

    private final ApartamentoRepository apartamentoRepository;
    private final TemporadaRepository temporadaRepository;
    private final LiquidacionEstanciaService liquidacionService;
    private final Dinero cargoMascota;

    public CotizarEstanciaUseCase(ApartamentoRepository apartamentoRepository,
                                  TemporadaRepository temporadaRepository,
                                  LiquidacionEstanciaService liquidacionService,
                                  Dinero cargoMascota) {
        this.apartamentoRepository = apartamentoRepository;
        this.temporadaRepository = temporadaRepository;
        this.liquidacionService = liquidacionService;
        this.cargoMascota = cargoMascota;
    }

    @Transactional(readOnly = true)
    public Dinero ejecutar(IdentificacionApartamento apartamentoId, Estancia estancia, 
                            List<Ocupante> ocupantes, int cantidadMascotas) {
        Apartamento apartamento = apartamentoRepository.obtenerPorIdentificacion(apartamentoId)
                .orElseThrow(() -> new ApartamentoNoEncontradoException(apartamentoId));

        return liquidacionService
                .liquidar(apartamento, estancia, ocupantes, cantidadMascotas, cargoMascota, temporadaRepository.buscarTodas())
                .total();
    }
}