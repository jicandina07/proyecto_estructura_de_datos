/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 * Realiza los analisis de conectividad, rutas minimas y hubs sobre el grafo.
 * 
 * @author josei
 */
public class AnalizadorRedes {

    /**
     * Encuentra todas las subredes conectadas en el grafo usando recorrido DFS.
     * 
     * @param grafo El grafo de la red de transporte.
     * @return Lista de subredes encontradas.
     */
    public ListaEnlazada obtenerSubredes(Grafo grafo) {
        ListaEnlazada subredes = new ListaEnlazada();
        ListaEnlazada estaciones = grafo.getEstaciones();
        ListaEnlazada visitados = new ListaEnlazada();

        for (int i = 0; i < estaciones.getTamaño(); i++) {
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
     * @param grafo El grafo de la red.
     * @param actual Estacion que se esta visitando.
     * @param visitados Lista de IDs de estaciones ya visitadas.
     * @param subredActual Lista donde se guardan las estaciones de la subred.
     */
    private void dfsRecorrido(Grafo grafo, Estacion actual, ListaEnlazada visitados, ListaEnlazada subredActual) {
        visitados.agregar(actual.getId());
        subredActual.agregar(actual);

        ListaEnlazada conexiones = grafo.obtenerConexionesDe(actual.getId());
        if (conexiones != null) {
            // Recorre todas las conexiones directas de la estacion actual para explorar sus vecinos
            for (int i = 0; i < conexiones.getTamaño(); i++) {
                Conexion conexion = (Conexion) conexiones.obtener(i);
                Estacion vecino = conexion.getDestino();
                // Verifica que la estacion vecina no haya sido visitada antes para evitar ciclos infinitos
                if (!visitados.contiene(vecino.getId())) {
                    dfsRecorrido(grafo, vecino, visitados, subredActual);
                }
            }
        }
    }

    /**
     * Busca las estaciones que tienen la mayor cantidad de conexiones directas.
     * 
     * @param grafo El grafo de la red.
     * @return Lista con las estaciones que empatan en el maximo de conexiones.
     */
    public ListaEnlazada obtenerHubs(Grafo grafo) {
        ListaEnlazada estaciones = grafo.getEstaciones();
        ListaEnlazada hubs = new ListaEnlazada();
        int maximo = 0;

        for (int i = 0; i < estaciones.getTamaño(); i++) {
            Estacion est = (Estacion) estaciones.obtener(i);
            int cantidad = est.getTamañoConexiones();
            if (cantidad > maximo) {
                maximo = cantidad;
            }
        }

        if (maximo > 0) {
            for (int i = 0; i < estaciones.getTamaño(); i++) {
                Estacion est = (Estacion) estaciones.obtener(i);
                if (est.getTamañoConexiones() == maximo) {
                    hubs.agregar(est);
                }
            }
        }

        return hubs;
    }

    /**
     * Calcula la ruta mas rapida entre dos estaciones aplicando Dijkstra.
     * 
     * @param grafo El grafo de la red.
     * @param idOrigen ID de la estacion de salida.
     * @param idDestino ID de la estacion de llegada.
     * @return Lista ordenada con las estaciones del recorrido, o null si no hay camino.
     */
    public ListaEnlazada calcularRutaMinima(Grafo grafo, String idOrigen, String idDestino) {
        Estacion origen = grafo.buscarPorId(idOrigen);
        Estacion destino = grafo.buscarPorId(idDestino);

        if (origen == null || destino == null) {
            return null;
        }

        ListaEnlazada estaciones = grafo.getEstaciones();
        int total = estaciones.getTamaño();

        int[] tiempos = new int[total];
        boolean[] visitados = new boolean[total];
        Estacion[] anteriores = new Estacion[total];

        for (int i = 0; i < total; i++) {
            tiempos[i] = 999999;
            visitados[i] = false;
            anteriores[i] = null;
        }

        int posOrigen = buscarPosicion(estaciones, origen);
        if (posOrigen != -1) {
            tiempos[posOrigen] = 0;
        }

        for (int i = 0; i < total; i++) {
            int actualPos = buscarMenorTiempo(tiempos, visitados, total);
            if (actualPos == -1) {
                break;
            }

            visitados[actualPos] = true;
            Estacion actual = (Estacion) estaciones.obtener(actualPos);

            if (actual.getId().equals(idDestino)) {
                break;
            }

            ListaEnlazada conexiones = actual.getConexiones();
            for (int j = 0; j < conexiones.getTamaño(); j++) {
                Conexion con = (Conexion) conexiones.obtener(j);
                Estacion vecino = con.getDestino();
                int vecinoPos = buscarPosicion(estaciones, vecino);

                if (vecinoPos != -1 && !visitados[vecinoPos]) {
                    int nuevoTiempo = tiempos[actualPos] + con.getTiempoMinutos();
                    if (nuevoTiempo < tiempos[vecinoPos]) {
                        tiempos[vecinoPos] = nuevoTiempo;
                        anteriores[vecinoPos] = actual;
                    }
                }
            }
        }

        int posDestino = buscarPosicion(estaciones, destino);
        if (posDestino == -1 || tiempos[posDestino] == 999999) {
            return null;
        }

        ListaEnlazada rutaAlReves = new ListaEnlazada();
        Estacion aux = destino;
        while (aux != null) {
            rutaAlReves.agregar(aux);
            int pos = buscarPosicion(estaciones, aux);
            if (pos != -1) {
                aux = anteriores[pos];
            } else {
                aux = null;
            }
        }

        ListaEnlazada rutaOrdenada = new ListaEnlazada();
        for (int i = rutaAlReves.getTamaño() - 1; i >= 0; i--) {
            rutaOrdenada.agregar(rutaAlReves.obtener(i));
        }

        return rutaOrdenada;
    }

    /**
     * Suma los minutos totales de una ruta calculada.
     * 
     * @param ruta Lista de estaciones en orden de recorrido.
     * @return Tiempo total en minutos.
     */
    public int calcularTiempoRuta(ListaEnlazada ruta) {
        if (ruta == null || ruta.getTamaño() < 2) {
            return 0;
        }
        int tiempoTotal = 0;
        for (int i = 0; i < ruta.getTamaño() - 1; i++) {
            Estacion actual = (Estacion) ruta.obtener(i);
            Estacion siguiente = (Estacion) ruta.obtener(i + 1);
            ListaEnlazada conexiones = actual.getConexiones();

            for (int j = 0; j < conexiones.getTamaño(); j++) {
                Conexion con = (Conexion) conexiones.obtener(j);
                if (con.getDestino().getId().equals(siguiente.getId())) {
                    tiempoTotal = tiempoTotal + con.getTiempoMinutos();
                    break;
                }
            }
        }
        return tiempoTotal;
    }

    private int buscarPosicion(ListaEnlazada estaciones, Estacion buscada) {
        for (int i = 0; i < estaciones.getTamaño(); i++) {
            Estacion est = (Estacion) estaciones.obtener(i);
            if (est.getId().equals(buscada.getId())) {
                return i;
            }
        }
        return -1;
    }

    private int buscarMenorTiempo(int[] tiempos, boolean[] visitados, int total) {
        int menor = 999999;
        int posicionMenor = -1;
        for (int i = 0; i < total; i++) {
            if (!visitados[i] && tiempos[i] < menor) {
                menor = tiempos[i];
                posicionMenor = i;
            }
        }
        return posicionMenor;
    }
}
