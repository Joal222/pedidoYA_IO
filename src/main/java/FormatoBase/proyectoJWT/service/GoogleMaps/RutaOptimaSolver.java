package FormatoBase.proyectoJWT.service.GoogleMaps;

import FormatoBase.proyectoJWT.model.dto.Solver.OptimalRouteResponse;
import FormatoBase.proyectoJWT.service.IRutaOptimaSolver;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import FormatoBase.proyectoJWT.model.dto.Solver.AsignacionDto;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RutaOptimaSolver implements IRutaOptimaSolver {

    @Transactional
    @Override
    public OptimalRouteResponse resolverRutas(int[] demanda, int[] oferta, BigDecimal[][] costos) {
        Model model = new Model("Optimización de rutas");

        int numProveedores = oferta.length;
        int numPedidos = demanda.length;

        // Variables de asignación: cuántos productos asignar de cada proveedor a cada pedido
        IntVar[][] asignacion = new IntVar[numProveedores][numPedidos];
        for (int i = 0; i < numProveedores; i++) {
            for (int j = 0; j < numPedidos; j++) {
                asignacion[i][j] = model.intVar("asignacion_" + i + "_" + j, 0, Math.min(oferta[i], demanda[j]));
            }
        }

        // Restricción de oferta: La suma de productos asignados por cada proveedor no debe exceder su oferta
        for (int i = 0; i < numProveedores; i++) {
            model.sum(asignacion[i], "<=", oferta[i]).post();
        }

        // Restricción de demanda: Cada pedido debe ser completamente satisfecho
        for (int j = 0; j < numPedidos; j++) {
            IntVar[] productosAsignados = new IntVar[numProveedores];
            for (int i = 0; i < numProveedores; i++) {
                productosAsignados[i] = asignacion[i][j];
            }
            model.sum(productosAsignados, ">=", demanda[j]).post();
        }

        // Aplanar los costos para calcular el costo total
        BigDecimal[] flatCostos = flattenCostos(costos);
        IntVar[] costoVars = flattenCostos(flatCostos, model);

        // Definir la función objetivo (minimización de costos)
        IntVar costoTotal = model.intVar("costoTotal", 0, 1000000);
        model.scalar(flatten(asignacion), toIntArray(flatCostos), "=", costoTotal).post();

        // Establecer la minimización del costo total
        model.setObjective(Model.MINIMIZE, costoTotal);

        // Resolver el modelo
        OptimalRouteResponse response = new OptimalRouteResponse();
        if (model.getSolver().solve()) {
            List<AsignacionDto> asignaciones = new ArrayList<>();
            System.out.println("Solución óptima encontrada:");
            for (int i = 0; i < numProveedores; i++) {
                for (int j = 0; j < numPedidos; j++) {
                    int cantidadAsignada = asignacion[i][j].getValue();
                    if (cantidadAsignada > 0) {
                        AsignacionDto asignacionDto = AsignacionDto.builder()
                                .proveedorId(i)
                                .pedidoId(j)
                                .cantidadAsignada(cantidadAsignada)
                                .build();
                        asignaciones.add(asignacionDto);
                    }
                }
            }
            response.setAsignaciones(asignaciones);
            response.setCostoTotal(BigDecimal.valueOf(costoTotal.getValue()));
        } else {
            System.out.println("No se encontró una solución.");
        }

        return response;
    }

    // Método para convertir un IntVar[][] en un IntVar[]
    private IntVar[] flatten(IntVar[][] array) {
        IntVar[] flat = new IntVar[array.length * array[0].length];
        int k = 0;
        for (IntVar[] row : array) {
            for (IntVar val : row) {
                flat[k++] = val;
            }
        }
        return flat;
    }

    // Método para convertir los BigDecimal[] en IntVar[]
    private IntVar[] flattenCostos(BigDecimal[] flatCostos, Model model) {
        IntVar[] costoVars = new IntVar[flatCostos.length];
        for (int i = 0; i < flatCostos.length; i++) {
            costoVars[i] = model.intVar("costo_" + i, flatCostos[i].intValue());
        }
        return costoVars;
    }

    // Método para aplanar un BigDecimal[][] en un BigDecimal[]
    private BigDecimal[] flattenCostos(BigDecimal[][] costos) {
        BigDecimal[] flat = new BigDecimal[costos.length * costos[0].length];
        int k = 0;
        for (BigDecimal[] row : costos) {
            for (BigDecimal val : row) {
                flat[k++] = val;
            }
        }
        return flat;
    }

    // Método para convertir un BigDecimal[] a un int[]
    private int[] toIntArray(BigDecimal[] array) {
        int[] intArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = array[i].intValue();
        }
        return intArray;
    }
}
