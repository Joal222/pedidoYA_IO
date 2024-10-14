package FormatoBase.proyectoJWT.service;

import java.math.BigDecimal;

public interface IGoogleMapsService {
    BigDecimal calcularDistancia(Double latitudOrigen, Double longitudOrigen, Double latitudDestino, Double longitudDestino);
}
