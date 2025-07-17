
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
        this.tiempoTramite = rand.nextInt(10) + 10; //El tiempo de atencion que se le va a dar va a ser minimo de 10 y maximo de 20
        this.tiempoTramiteOriginal = this.tiempoTramite; // Asigna el valor inicial aquí
        this.tolerancia = rand.nextInt(15) + 5; //El tiempo de tolerancia que se le va a dar va a ser minimo de 5 y maximo de 20
        
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
    
    public void disminuirTiempoTramite(int minutos) {
        this.tiempoTramite -= minutos;
        if (this.tiempoTramite < 0) {
            this.tiempoTramite = 0;
            // Disminuye el tiempo de el tramite y hace que se eviten valores negativos
        }
    }

    public void incrementarTiempoEspera(int minutos) {
        this.tiempoEsperaActual += minutos;
    }

    public boolean seFuePorTolerancia() {
        return this.tiempoEsperaActual >= this.tolerancia;
    }

    @Override
    public String toString() {
        return "Tiquete: " + tiquete + ", Prioridad: " + prioridad +
               ", Trámite: " + tiempoTramite + "min (Original: " + tiempoTramiteOriginal + "min)" +
               ", Tolerancia: " + tolerancia + "min, Espera: " + tiempoEsperaActual + "min";
    }
}


