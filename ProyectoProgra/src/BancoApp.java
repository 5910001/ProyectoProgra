import javax.swing.JOptionPane; // Para usar ventanas de diálogo
import java.util.ArrayList;     // Para usar listas dinámicas
import java.util.List;
import java.util.Comparator;    // Para ordenar las listas al mostrar
import java.util.stream.Collectors; // Para facilitar el agrupamiento en reportes
/**
 *
 * @author Andrew Mena
 */
public class BancoApp {
     // Objetos globales que todas las funciones necesitarán acceder
    private static FilaPrioridad filaPrincipal = new FilaPrioridad(); // La única fila del banco
    private static List<CajaAtencion> cajas = new ArrayList<>(); // Lista de todas las cajas de atención
    private static List<Cliente> clientesNoAtendidos = new ArrayList<>(); // Lista de clientes que se fueron sin ser atendidos
    private static int totalClientesEntraron = 0; // Contador de todos los clientes que generaron un tiquete
    private static int simulacionTiempo = 0; // Tiempo total de simulación en minutos

    // Método principal: punto de entrada del programa
    public static void main(String[] args) {
        // Inicializar las 5 cajas normales
        for (int i = 1; i <= 5; i++) {
            cajas.add(new CajaAtencion(i, 'N')); // 'N' indica caja Normal
        }
        // Inicializar la 1 caja de Plataforma de Servicios
        cajas.add(new CajaAtencion(6, 'P')); // 'P' indica caja de Plataforma

        // Iniciar el menú principal de la aplicación
        menuPrincipal();
    }

    // Muestra el menú principal y maneja las opciones del usuario
    private static void menuPrincipal() {
        String opcion;
        do {
            String menu = "SIMULADOR DE BANCO - Tiempo Actual: " + simulacionTiempo + " minutos\n\n" +
                          "1. Generar nuevo cliente (Tomar tiquete)\n" +
                          "2. Avanzar simulación (Atender clientes)\n" +
                          "3. Ver estado de la fila\n" +
                          "4. Ver estado de las cajas\n" +
                          "5. Reportes y Consultas\n" +
                          "6. Salir";

            // Muestra el diálogo del menú y captura la opción del usuario
            opcion = JOptionPane.showInputDialog(null, menu, "Menú Principal", JOptionPane.PLAIN_MESSAGE);

            // Manejar si el usuario cierra el diálogo o presiona cancelar
            if (opcion == null) {
                opcion = "6"; // Forzar la salida si se cierra el diálogo
            }

            // Ejecuta la acción según la opción seleccionada
            switch (opcion) {
                case "1":
                    generarCliente();
                    break;
                case "2":
                    avanzarSimulacion();
                    break;
                case "3":
                    mostrarFila();
                    break;
                case "4":
                    mostrarEstadoCajas();
                    break;
                case "5":
                    mostrarReportes();
                    break;
                case "6":
                    JOptionPane.showMessageDialog(null, "Saliendo del simulador. ¡Hasta pronto!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!opcion.equals("6")); // El bucle continúa hasta que el usuario elija "Salir"
    }

    // Permite al usuario generar un nuevo cliente y asignarle una prioridad
    private static void generarCliente() {
        String[] opcionesPrioridad = {"Adulto Mayor (A)", "Embarazada/Niño (B)", "Discapacidad (C)",
                                      "Dos o más asuntos (D)", "Plataforma de Servicios (E)",
                                      "Mujer (F)", "Hombre (G)"};
        
        // Muestra un menú desplegable para que el usuario elija la prioridad
        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de cliente:",
                                                                "Generar Cliente", JOptionPane.QUESTION_MESSAGE,
                                                                null, opcionesPrioridad, opcionesPrioridad[0]);

        if (seleccion == null) { // Si el usuario cancela la selección
            return;
        }

        char prioridad;
        // Asigna la letra de prioridad según la selección del usuario
        switch (seleccion) {
            case "Adulto Mayor (A)": prioridad = 'A'; break;
            case "Embarazada/Niño (B)": prioridad = 'B'; break;
            case "Discapacidad (C)": prioridad = 'C'; break;
            case "Dos o más asuntos (D)": prioridad = 'D'; break;
            case "Plataforma de Servicios (E)": prioridad = 'E'; break;
            case "Mujer (F)": prioridad = 'F'; break;
            case "Hombre (G)": prioridad = 'G'; break;
            default: prioridad = 'F'; // Valor por defecto si algo sale mal
        }

        // Genera el tiquete y crea un nuevo objeto Cliente
        String tiquete = filaPrincipal.generarTiquete(prioridad);
        Cliente nuevoCliente = new Cliente(tiquete, prioridad);

        // Intenta agregar el cliente a la fila
        if (filaPrincipal.agregarCliente(nuevoCliente)) {
            totalClientesEntraron++; // Incrementa el contador de clientes que han entrado al banco
            JOptionPane.showMessageDialog(null, "Cliente generado y añadido a la fila:\n" + nuevoCliente.toString(), "Nuevo Cliente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "La fila ha alcanzado su capacidad máxima (" + filaPrincipal.capacidad_max + " clientes). No se pudo agregar el cliente.", "Fila Llena", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Avanza la simulación en un "tick" de tiempo (ej. 1 minuto)
    private static void avanzarSimulacion() {
        int minutosSimulados = 1; // Cada vez que se llama a esta función, pasa 1 minuto
        simulacionTiempo += minutosSimulados; // Actualiza el tiempo total de simulación

        // 1. Todos los clientes en la fila aumentan su tiempo de espera
        filaPrincipal.avanzarTiempoEsperaEnFila(minutosSimulados);

        // 2. Identificar y remover clientes que se van por exceder su tolerancia
        List<Cliente> clientesQueSeFueron = new ArrayList<>();
        for (Cliente c : filaPrincipal.getFilaClientes()) {
            if (c.seFuePorTolerancia()) {
                clientesQueSeFueron.add(c);
            }
        }
        // Removerlos de la fila y añadirlos a la lista de no atendidos
        for (Cliente c : clientesQueSeFueron) {
            filaPrincipal.removerCliente(c);
            clientesNoAtendidos.add(c);
            JOptionPane.showMessageDialog(null, "Cliente " + c.getTiquete() + " se fue del banco por exceder la tolerancia de espera.", "Cliente Abandonó", JOptionPane.WARNING_MESSAGE);
        }

        // 3. Simular atención en cajas
        int clientesAtendidosEsteTurno = 0;
        
        // Primero, intentar atender clientes de Plataforma con la caja de Plataforma
        CajaAtencion cajaPlataforma = cajas.get(5); // Asumiendo que la caja 6 (índice 5) es la de Plataforma
        if (cajaPlataforma.estaLibre()) {
            Cliente clienteE = filaPrincipal.getSiguienteClientePlataforma();
            if (clienteE != null) {
                filaPrincipal.removerCliente(clienteE);
                cajaPlataforma.atenderCliente(clienteE);
                clientesAtendidosEsteTurno++;
            }
        }
        // Avanzar el tiempo para la caja de Plataforma (si está ocupada)
        if (!cajaPlataforma.estaLibre()) {
            cajaPlataforma.asanSimulacionDuracion(minutosSimulados);
        }
        // Intentar finalizar atención en caja de Plataforma si el cliente ya terminó
        Cliente clienteFinalizadoPlataforma = cajaPlataforma.finalizarAtencion();
        if (clienteFinalizadoPlataforma != null) {
            // No hacemos nada aquí con el cliente finalizado, ya está en la lista de la caja
        }


        // Luego, intentar atender clientes con las 5 cajas normales
        for (int i = 0; i < 5; i++) { // Iterar sobre las primeras 5 cajas (normales)
            CajaAtencion cajaNormal = cajas.get(i);
            if (cajaNormal.estaLibre()) {
                Cliente clienteAAtender = filaPrincipal.getSiguienteClienteNormal();
                if (clienteAAtender != null) {
                    filaPrincipal.removerCliente(clienteAAtender);
                    cajaNormal.atenderCliente(clienteAAtender);
                    clientesAtendidosEsteTurno++;
                }
            }
            // Avanzar el tiempo para la caja normal (si está ocupada)
            if (!cajaNormal.estaLibre()) {
                cajaNormal.asanSimulacionDuracion(minutosSimulados);
            }
            // Intentar finalizar atención en caja normal si el cliente ya terminó
            Cliente clienteFinalizadoNormal = cajaNormal.finalizarAtencion();
            if (clienteFinalizadoNormal != null) {
                // No hacemos nada aquí con el cliente finalizado, ya está en la lista de la caja
            }
        }

        String mensaje = "Simulación avanzada por " + minutosSimulados + " minuto(s).\n";
        mensaje += "Se atendieron " + clientesAtendidosEsteTurno + " clientes nuevos.\n";
        mensaje += clientesQueSeFueron.size() + " clientes se fueron por tolerancia.";

        JOptionPane.showMessageDialog(null, mensaje, "Avance de Simulación", JOptionPane.INFORMATION_MESSAGE);
    }

    // Muestra el estado actual de la fila de espera
    private static void mostrarFila() {
        if (filaPrincipal.getFilaClientes().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La fila está vacía.", "Estado de la Fila", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("Clientes en la fila (ordenados por prioridad y llegada):\n\n");
        // Crea una copia de la lista de clientes en la fila y la ordena para mostrarla
        List<Cliente> filaParaMostrar = new ArrayList<>(filaPrincipal.getFilaClientes());
        filaParaMostrar.sort(Comparator.comparing(Cliente::getPrioridad) // Ordena por prioridad (A, B, C...)
                                        .thenComparing(Cliente::getTiempoLlegada)); // Luego por tiempo de llegada (si la prioridad es la misma)

        for (Cliente c : filaParaMostrar) {
            sb.append(c.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Estado de la Fila", JOptionPane.PLAIN_MESSAGE);
    }

    // Muestra el estado actual de cada caja de atención
    private static void mostrarEstadoCajas() {
        StringBuilder sb = new StringBuilder("Estado de las Cajas:\n\n");
        for (CajaAtencion caja : cajas) {
            sb.append("Caja ").append(caja.getIdCaja()).append(" (Tipo: ").append(caja.getTipoCaja()).append("): ");
            if (caja.estaLibre()) {
                sb.append("Libre\n");
            } else {
                sb.append("Atendiendo a ").append(caja.getClienteActual().getTiquete())
                  .append(" (Tiempo restante: ").append(caja.getClienteActual().getTiempoTramite()).append(" min)\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Estado de las Cajas", JOptionPane.PLAIN_MESSAGE);
    }

    // Muestra varios reportes y consultas sobre la simulación
    private static void mostrarReportes() {
        StringBuilder sb = new StringBuilder("REPORTES DEL BANCO\n\n");

        // 1. Cantidad de clientes atendidos por cada cajero.
        sb.append("--- Clientes atendidos por cajero ---\n");
        for (CajaAtencion caja : cajas) {
            sb.append("Caja ").append(caja.getIdCaja()).append(" (").append(caja.getTipoCaja()).append("): ")
              .append(caja.getCantidadClientesAtendidos()).append(" clientes\n");
        }
        sb.append("\n");

        // 2. Promedio de tiempo de atención por cajeros.
        // Para esto, sumamos los 'tiempoTramiteOriginal' de los clientes que cada caja atendió.
        sb.append("--- Tiempo promedio de atención por cajero ---\n");
        for (CajaAtencion caja : cajas) {
            long totalTiempoCaja = 0;
            for (Cliente c : caja.getClientesAtendidos()) {
                totalTiempoCaja += c.getTiempoTramiteOriginal(); // Usamos el tiempo original
            }
            double promedio = (caja.getCantidadClientesAtendidos() > 0) ? 
                               (double) totalTiempoCaja / caja.getCantidadClientesAtendidos() : 0;
            
            sb.append("Caja ").append(caja.getIdCaja()).append(" (").append(caja.getTipoCaja()).append("): ")
              .append(String.format("%.2f", promedio)).append(" minutos/cliente\n");
        }
        sb.append("\n");

        // 3. Total de clientes que entraron en el banco.
        sb.append("--- Totales Generales ---\n");
        sb.append("Total de clientes que entraron al banco: ").append(totalClientesEntraron).append("\n");

        // 4. Total de clientes atendidos y sin atender en el banco.
        int totalAtendidos = 0;
        for (CajaAtencion caja : cajas) {
            totalAtendidos += caja.getCantidadClientesAtendidos();
        }
        sb.append("Total de clientes atendidos: ").append(totalAtendidos).append("\n");
        sb.append("Clientes actualmente en fila (sin atender): ").append(filaPrincipal.getCantidadClientes()).append("\n");
        sb.append("Clientes que se fueron sin ser atendidos: ").append(clientesNoAtendidos.size()).append("\n\n");

        // 5. Total de clientes atendidos por cada categoría (letra).
        sb.append("--- Clientes atendidos por categoría (Prioridad) ---\n");
        cajas.stream() // Stream de todas las cajas
             .flatMap(caja -> caja.getClientesAtendidos().stream()) // Obtener todos los clientes atendidos de todas las cajas
             .collect(Collectors.groupingBy(Cliente::getPrioridad, Collectors.counting())) // Agrupar por prioridad y contar
             .entrySet().stream() // Convertir a un stream de entradas (prioridad -> conteo)
             .sorted(Comparator.comparing(java.util.Map.Entry::getKey)) // Ordenar por la letra de prioridad (A, B, C...)
             .forEach(entry -> sb.append("Prioridad ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" clientes\n"));
        sb.append("\n");


        // 6. Reporte de los clientes que se fueron sin ser atendidos.
        if (!clientesNoAtendidos.isEmpty()) {
            sb.append("--- Clientes que se fueron sin ser atendidos ---\n");
            for (Cliente c : clientesNoAtendidos) {
                sb.append(c.getTiquete()).append(" (Prioridad: ").append(c.getPrioridad())
                  .append(", Tolerancia: ").append(c.getTolerancia()).append("min")
                  .append(", Esperó: ").append(c.getTiempoEsperaActual()).append("min)\n");
            }
        } else {
            sb.append("No hay clientes que se hayan ido sin ser atendidos.\n");
        }

        // Muestra todos los reportes en un único diálogo
        JOptionPane.showMessageDialog(null, sb.toString(), "Reportes del Banco", JOptionPane.PLAIN_MESSAGE);
    }
}