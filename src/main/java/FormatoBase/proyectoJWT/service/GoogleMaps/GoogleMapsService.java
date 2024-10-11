package FormatoBase.proyectoJWT.service.GoogleMaps;

import FormatoBase.proyectoJWT.service.IGoogleMapsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class GoogleMapsService implements IGoogleMapsService {

    private static final String API_KEY = "AIzaSyC4_BK38Vw1dTBTt5PeI-LGsM4kFMusO78";// Clave de API de Google Maps

    public BigDecimal calcularDistancia(Double latitudOrigen, Double longitudOrigen, Double latitudDestino, Double longitudDestino) {
        try {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + latitudOrigen + "," + longitudOrigen +
                    "&destinations=" + latitudDestino + "," + longitudDestino + "&key=" + API_KEY;

            RestTemplate restTemplate = new RestTemplate();
            String respuesta = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode raiz = objectMapper.readTree(respuesta);
            JsonNode nodoDistancia = raiz.path("rows").get(0).path("elements").get(0).path("distance").path("value");

            // Convertir distancia a BigDecimal y luego a kilómetros
            BigDecimal distanciaEnMetros = new BigDecimal(nodoDistancia.asText());
            BigDecimal distanciaEnKilometros = distanciaEnMetros.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);

            return distanciaEnKilometros;
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
}

