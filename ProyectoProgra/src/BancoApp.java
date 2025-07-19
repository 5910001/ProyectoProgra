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

    }
   }