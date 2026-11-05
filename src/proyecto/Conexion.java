/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 * Representa un enlace o arista entre dos estaciones de la red de transporte,
 * almacenando la estación de llegada y el tiempo estimado de traslado en minutos.
 * 
 * @author josei
 */
public class Conexion {
    private Estacion destino;
    private int tiempoMinutos;

    /**
     * Constructor principal de la clase Conexion.
     * 
     * @param destino Estación de llegada o destino.
     * @param tiempoMinutos Tiempo de traslado en minutos.
     */
    public Conexion(Estacion destino, int tiempoMinutos) {
        this.destino = destino;
        this.tiempoMinutos = tiempoMinutos;
    }

    public Estacion getDestino() {
        return destino;
    }

    public void setDestino(Estacion destino) {
        this.destino = destino;
    }

    public int getTiempoMinutos() {
        return tiempoMinutos;
    }

    public void setTiempoMinutos(int tiempoMinutos) {
        this.tiempoMinutos = tiempoMinutos;
    }

    @Override
    public String toString() {
        return destino.getNombre() + " (" + tiempoMinutos + " min)";
    }
}
