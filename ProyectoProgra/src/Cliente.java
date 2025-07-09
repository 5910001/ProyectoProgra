
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dylan
 */
public class Cliente {
    private String tiquete;
    private char prioridad;
    private int tiempoTramite;
    private final int tiempoTramiteOriginal; // Atributo para el tiempo de trámite inicial
    private int tolerancia;
    private long tiempoLlegada;
    private int tiempoEsperaActual;

    public Cliente(String tiquete, char prioridad) {
        this.tiquete = tiquete;
        this.prioridad = prioridad;
        
        Random rand = new Random();
        this.tiempoTramite = rand.nextInt(111) + 10;
        this.tiempoTramiteOriginal = this.tiempoTramite; // Asigna el valor inicial aquí
        this.tolerancia = rand.nextInt(146) + 5;
        
        this.tiempoLlegada = System.currentTimeMillis();
        this.tiempoEsperaActual = 0;
    }

    public String getTiquete() {
        return tiquete;
    }

    public char getPrioridad() {
        return prioridad;
    }

    public int getTiempoTramite() {
        return tiempoTramite;
    }

    public int getTiempoTramiteOriginal() { // Getter para el tiempo de trámite inicial
        return tiempoTramiteOriginal;
    }

    public int getTolerancia() {
        return tolerancia;
    }

    public long getTiempoLlegada() {
        return tiempoLlegada;
    }

    public int getTiempoEsperaActual() {
        return tiempoEsperaActual;
}
}
