package co.edu.uniquindio.sga.domain.service;

import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.entity.Ocupante;
import co.edu.uniquindio.sga.domain.entity.Temporada;
import co.edu.uniquindio.sga.domain.exception.ReglaDominioException;
import co.edu.uniquindio.sga.domain.valueobject.Dinero;
import co.edu.uniquindio.sga.domain.valueobject.Estancia;
import co.edu.uniquindio.sga.domain.valueobject.LiquidacionEstancia;
import co.edu.uniquindio.sga.domain.valueobject.NocheLiquidada;
import co.edu.uniquindio.sga.domain.valueobject.Tarifa;
import co.edu.uniquindio.sga.domain.valueobject.UmbralEdadFacturable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Servicio de dominio (Guía 05) */
public class LiquidacionEstanciaService {

    // L-09: el umbral es configuración del alojamiento, llega por constructor
    private final UmbralEdadFacturable umbral;

    public LiquidacionEstanciaService(UmbralEdadFacturable umbral) {
        if (umbral == null) {
            throw new ReglaDominioException("El umbral de edad facturable es obligatorio.");
        }
        this.umbral = umbral;
    }

    public LiquidacionEstancia liquidar(Apartamento apartamento, Estancia estancia,
                                        List<Ocupante> ocupantes, int cantidadMascotas,
                                        Dinero cargoMascota, List<Temporada> temporadas) {
        int facturables = (int) ocupantes.stream()
                .filter(ocupante -> ocupante.esFacturableEn(estancia, umbral))
                .count();

        List<NocheLiquidada> noches = new ArrayList<>();
        for (LocalDate noche : estancia.nochesOcupadas()) {
            Temporada temporada = temporadaDe(noche, temporadas);
            // 7.5 condición 3: el apartamento debe tener tarifa para cada temporada que toque
            Tarifa tarifa = apartamento.tarifaEn(temporada.getId())
                    .orElseThrow(() -> new ReglaDominioException(
                            "El apartamento no tiene tarifa definida para la temporada " + temporada.getNombre() + "."));
            
            Dinero subtotalNoche = tarifa.valorPorOcupanteNoche().multiplicarPor(facturables);
            
            // RN-02: 2 mascotas gratis, la 3ra con cargo por noche
            if (cantidadMascotas == 3) {
                subtotalNoche = subtotalNoche.sumar(cargoMascota);
            }

            noches.add(new NocheLiquidada(noche, temporada.getId(), tarifa.valorPorOcupanteNoche(),
                    facturables, subtotalNoche));
        }
        return new LiquidacionEstancia(noches);
    }

    // F-05: una temporada específica prevalece; si ninguna cubre la noche, rige la temporada base
    private Temporada temporadaDe(LocalDate noche, List<Temporada> temporadas) {
        return temporadas.stream()
                .filter(temporada -> !temporada.isEsBase() && temporada.cubre(noche))
                .findFirst()
                .or(() -> temporadas.stream().filter(Temporada::isEsBase).findFirst())
                .orElseThrow(() -> new ReglaDominioException(
                        "No existe una temporada que cubra la noche " + noche + "."));
    }
}