package FormatoBase.proyectoJWT.service;

import FormatoBase.proyectoJWT.model.dto.Solver.OptimalRouteResponse;

import java.math.BigDecimal;

public interface IRutaOptimaSolver {
    OptimalRouteResponse resolverRutas(int[] demanda, int[] oferta, BigDecimal[][] costos);
}
