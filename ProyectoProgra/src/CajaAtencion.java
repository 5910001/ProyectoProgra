
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
     private int idCaja; // Número de identificación de la caja
    private List<Cliente> clientesAtendidos; // Lista de clientes que esta caja ha atendido
    private long tiempoTotalAtencion; // Suma de los tiempos de trámite de clientes atendidos (en minutos)
    private Cliente clienteActual; // El cliente que está siendo atendido en este momento por la caja
    private char tipoCaja; // 'N' para normal, 'P' para Plataforma de Servicios

    // Constructor de la CajaAtencion
    public CajaAtencion(int idCaja, char tipoCaja) {
        this.idCaja = idCaja;
        this.clientesAtendidos = new ArrayList<>();
        this.tiempoTotalAtencion = 0;
        this.clienteActual = null; // La caja inicia libre
        this.tipoCaja = tipoCaja;
    }

    // Getters
    public int getIdCaja() {
        return idCaja;
    }

    public char getTipoCaja() {
        return tipoCaja;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }
}
