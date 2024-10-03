package FormatoBase.proyectoJWT.service.GoogleMaps;

import FormatoBase.proyectoJWT.service.IGoogleMapsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService implements IGoogleMapsService {

    private static final String API_KEY = "AIzaSyC4_BK38Vw1dTBTt5PeI-LGsM4kFMusO78";// Clave de API de Google Maps

    public Integer calcularDistancia(Double latitudOrigen, Double longitudOrigen, Double latitudDestino, Double longitudDestino) {
        try {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + latitudOrigen + "," + longitudOrigen +
                    "&destinations=" + latitudDestino + "," + longitudDestino + "&key=" + API_KEY;  // URL para hacer la solicitud a la API de Google Maps Distance Matrix

            RestTemplate restTemplate = new RestTemplate(); // Hacer una solicitud HTTP GET usando RestTemplate
            String respuesta = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper(); // Convertir la respuesta JSON a un objeto Java
            JsonNode raiz = objectMapper.readTree(respuesta);
            JsonNode nodoDistancia = raiz.path("rows").get(0).path("elements").get(0).path("distance").path("value"); // Navegar en el JSON para obtener el valor de la distancia en metros

            Integer distanciaEnMetros = nodoDistancia.asInt(); // Convertir la distancia de metros a kilómetros
            return distanciaEnMetros / 1000;  // Convertir metros a kilómetros
        } catch (Exception e) {
            e.printStackTrace();
            return 0;  // Retorna 0 en caso de error
        }
    }
}

