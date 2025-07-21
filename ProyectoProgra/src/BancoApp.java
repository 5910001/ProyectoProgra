/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
     // Objetos globales que todas las funciones necesitarán acceder.
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
   