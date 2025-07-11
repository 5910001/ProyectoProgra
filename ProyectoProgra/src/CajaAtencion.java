
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dylan
 */
public class CajaAtencion {
     private int idCaja; 
    private List<Cliente> clientesAtendidos; // Lista de clientes que la caja ya atendio 
    private long tiempoTotalAtencion; // Esta es la variable que suma los tiempos de trámite de clientes atendidos (en minutos)
    private Cliente clienteActual; // Este es el cliente que está siendo atendido en este momento por la caja
    private char tipoCaja; // 'N' para normal, 'P' para Plataforma de Servicios

    public CajaAtencion(int idCaja, char tipoCaja) {
        this.idCaja = idCaja;
        this.clientesAtendidos = new ArrayList<>();
        this.tiempoTotalAtencion = 0;
        this.clienteActual = null; // La caja inicia libre
        this.tipoCaja = tipoCaja;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public char getTipoCaja() {
        return tipoCaja;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }
    
    // Revisa si la caja está libre
    public boolean estaLibre() {
        return clienteActual == null;
    }

    // Esto es una ayuda para la simulación ya que el clienteActual no se pone en null hasta finalizarAtencion
    public boolean sigueLibre() {
        return clienteActual == null;
    }

    // Se le da un cliente a la caja para que empiece a ser atendido
    public void atenderCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }
        this.clienteActual = cliente;
        System.out.println("Caja " + idCaja + " (" + tipoCaja + ") empieza a atender a: " + cliente.getTiquete());
    }

    // Simula el paso del tiempo en la atención de un cliente. 
    public void asanSimulacionDuracion(int minutos) {
        if (clienteActual != null) {
            clienteActual.disminuirTiempoTramite(minutos);
        }
    }

    // Finaliza la atención del cliente actual si su tiempo de trámite llegó a cero.
    public Cliente finalizarAtencion() {
        if (clienteActual != null && clienteActual.getTiempoTramite() <= 0) {
            // Suma el tiempo de trámite del cliente al tiempo total de atención de la caja
            tiempoTotalAtencion += clienteActual.getTiempoTramite(); 
            
            clientesAtendidos.add(clienteActual); // Añade un cliente a la lista de que ya fue atendidio
            Cliente clienteAtendido = clienteActual; // Guarda el cliente que acaba de ser atendido
            this.clienteActual = null; // La caja queda libre
            System.out.println("Caja " + idCaja + " (" + tipoCaja + ") terminó de atender a: " + clienteAtendido.getTiquete());
            return clienteAtendido; 
        }
        return null; // El cliente aún no ha terminado su trámite o no hay cliente
    }

    // Retorna la cantidad de clientes que esta caja ha atendido
    public int getCantidadClientesAtendidos() {
        return clientesAtendidos.size();
    }

    // Calcula el tiempo promedio que tarda en atender a un cliente
    public double getTiempoPromedioAtencion() {
        if (clientesAtendidos.isEmpty()) {
            return 0;
        }
        
        long totalTiempoClientes = 0;
        for (Cliente c : clientesAtendidos) {
           
        }
        return 0; // Se recalculará en el reporte principal para mayor claridad.
    }

    // Retorna la lista de clientes atendidos por esta caja 
    public List<Cliente> getClientesAtendidos() {
        return clientesAtendidos;
}
    }
