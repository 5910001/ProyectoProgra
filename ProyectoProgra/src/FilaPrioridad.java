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
     private List<Cliente> filaClientes; // Aquí se guardan todos los clientes de la fila
    private int consecuTique; // Cuenta el numero de ticketes
    final int capacidad_max = 25; // el maximo de la fila es de 25 clientes

    // Constructor de la FilaPrioridad
    public FilaPrioridad() {
        this.filaClientes = new ArrayList<>(); 
        this.consecuTique = 1; 
    }

    // Agrega un nuevo cliente a la fila
    public boolean agregarCliente(Cliente cliente) {
        if (filaClientes.size() < capacidad_max) { // Verifica si hay espacio
            filaClientes.add(cliente); // Añadir cliemte
            return true; // Se agrega el cliente exitosamente
        } else {
            return false; 
    }
  }
    // Se genera el tickete
    public String generarTiquete(char tipoPrioridad) {
        return (consecuTique++) + String.valueOf(tipoPrioridad);
    }

    // Se obtiene el cliente de mayor prioridad
    public Cliente getSiguienteClienteNormal() {
        if (filaClientes.isEmpty()) {
            return null; 
        }

        Cliente siguiente = null;
        char mayorPrioridadChar = 'Z'; 

        for (Cliente c : filaClientes) {
           // Las cajas normales no atienden clientes de plataforma
            if (c.getPrioridad() != 'E') {
                if (c.getPrioridad() < mayorPrioridadChar) { // Si encontramos una prioridad más alta
                    mayorPrioridadChar = c.getPrioridad();
                    siguiente = c;
                } else if (c.getPrioridad() == mayorPrioridadChar) {
                    
                    if (siguiente == null || c.getTiempoLlegada() < siguiente.getTiempoLlegada()) {
                        siguiente = c;
                    }
                }
            }
        }
        return siguiente; 
    }

    // Obtiene el cliente de prioridad para la caja de plataforma
    public Cliente getSiguienteClientePlataforma() {
        if (filaClientes.isEmpty()) {
            return null;
        }

        Cliente siguiente = null;
        for (Cliente c : filaClientes) {
            if (c.getPrioridad() == 'E') {
                // Si hay varios toma se toma el que llega primero 
                if (siguiente == null || c.getTiempoLlegada() < siguiente.getTiempoLlegada()) {
                    siguiente = c;
                }
            }
        }
        return siguiente; 
    }

    // Remueve un cliente de la fila 
    public void removerCliente(Cliente cliente) {
        filaClientes.remove(cliente);
    }

    // Retorna la lista completa de clientes 
    public List<Cliente> getFilaClientes() {
        return filaClientes;
    }

    // Retorna la cantidad actual de clientes de la fila
    public int getCantidadClientes() {
        return filaClientes.size();
    }

    // Incrementa el tiempo de espera de los clientes en la fila
    public void avanzarTiempoEsperaEnFila(int minutos) {
        for (Cliente c : filaClientes) {
            c.incrementarTiempoEspera(minutos);
        }
    }
}
    
    
    
    
    

