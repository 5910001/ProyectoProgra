/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList; 
import java.util.List;     
import java.util.Comparator; 

/**
 *
 * @author Andrew Mena
 */

public class FilaPrioridad {
     private List<Cliente> filaClientes; // Aquí guardamos a todos los clientes en la fila
    private int consecuTique; // Contador para el número de tiquete (1, 2, 3...)
    final int capacidad_max = 25; // La fila no puede tener más de 25 clientes

    // Constructor de la FilaPrioridad
    public FilaPrioridad() {
        this.filaClientes = new ArrayList<>(); // Inicializa la lista de clientes
        this.consecuTique = 1; // El primer tiquete será el número 1
    }

    // Agrega un nuevo cliente a la fila
    public boolean agregarCliente(Cliente cliente) {
        if (filaClientes.size() < capacidad_max) { // Verifica si hay espacio
            filaClientes.add(cliente); // Añade el cliente al final de la lista
            return true; // Cliente agregado exitosamente
        } else {
            return false; // La fila está llena
    }
  }
}
