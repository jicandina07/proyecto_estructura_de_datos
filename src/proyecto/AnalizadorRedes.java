/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;
/**
 * Servicio encargado de realizar análisis de conectividad y rutas sobre la red.
 * Aplica algoritmos de búsqueda en profundidad (DFS) para la detección de subredes.
 * 
 * @author josei
 */
public class AnalizadorRedes {

    /**
     * Identifica todas las subredes (componentes conexas) presentes en el grafo.
     * 
     * @param grafo El grafo que contiene las estaciones y conexiones.
     * @return Una lista enlazada con las subredes encontradas.
     */
    public ListaEnlazada obtenerSubredes(Grafo grafo) {
        ListaEnlazada subredes = new ListaEnlazada();
        ListaEnlazada estaciones = grafo.getEstaciones();
        ListaEnlazada visitados = new ListaEnlazada();

        for (int i = 0; i < estaciones.getTamano(); i++) {
            Estacion estacion = (Estacion) estaciones.obtener(i);
            if (!visitados.contiene(estacion.getId())) {
                ListaEnlazada subredActual = new ListaEnlazada();
                dfsRecorrido(grafo, estacion, visitados, subredActual);
                subredes.agregar(subredActual);
            }
        }
        return subredes;
    }

    /**
     * Realiza la exploración en profundidad recursiva desde una estación origen.
     * 
     * @param grafo El grafo de la red de transporte.
     * @param actual La estación actual en la exploración.
     * @param visitados Registro de IDs de estaciones ya visitadas.
     * @param subredActual Lista donde se acumulan las estaciones de la subred.
     */
    private void dfsRecorrido(Grafo grafo, Estacion actual, ListaEnlazada visitados, ListaEnlazada subredActual) {
        visitados.agregar(actual.getId());
        subredActual.agregar(actual);

        ListaEnlazada conexiones = grafo.obtenerConexionesDe(actual.getId());
        if (conexiones != null) {
            for (int i = 0; i < conexiones.getTamano(); i++) {
                Conexion conexion = (Conexion) conexiones.obtener(i);
                Estacion vecino = conexion.getDestino();
                if (!visitados.contiene(vecino.getId())) {
                    dfsRecorrido(grafo, vecino, visitados, subredActual);
                }
            }
        }
    }
}
