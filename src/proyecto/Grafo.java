/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 *
 * @author josei
 */
public class Grafo {
    private ListaEnlazada estaciones;
    private TablaHash tablaPorId;
    private TablaHash tablaPorNombre;

    /**
     * Inicializa las estructuras internas del grafo.
     */
    public Grafo() {
        this.estaciones = new ListaEnlazada();
        this.tablaPorId = new TablaHash(100);
        this.tablaPorNombre = new TablaHash(100);
    }

    /**
     * Agrega una estación al grafo y actualiza las tablas hash de búsqueda.
     * 
     * @param estacion Estación que se va a registrar.
     */
    public void agregarEstacion(Estacion estacion) {
        if (estacion != null && buscarPorId(estacion.getId()) == null) {
            estaciones.agregar(estacion);
            tablaPorId.insertar(estacion.getId(), estacion);
            tablaPorNombre.insertar(estacion.getNombre(), estacion);
        }
    }

    /**
     * Registra una conexión bidireccional entre dos estaciones existentes.
     * 
     * @param idOrigen ID de la estación de origen.
     * @param idDestino ID de la estación de destino.
     * @param tiempoMinutos Tiempo de traslado en minutos.
     */
    public void agregarConexion(String idOrigen, String idDestino, int tiempoMinutos) {
        Estacion origen = buscarPorId(idOrigen);
        Estacion destino = buscarPorId(idDestino);

        if (origen != null && destino != null && !idOrigen.equals(idDestino)) {
            Conexion conexionA = new Conexion(destino, tiempoMinutos);
            Conexion conexionB = new Conexion(origen, tiempoMinutos);

            origen.getConexiones().agregar(conexionA);
            destino.getConexiones().agregar(conexionB);
        }
    }

    /**
     * Elimina una estación del grafo y remueve todas las conexiones que apuntan a ella.
     * 
     * @param idEstacion ID de la estación a eliminar.
     */
    public void eliminarEstacion(String idEstacion) {
        Estacion estacion = buscarPorId(idEstacion);
        if (estacion != null) {
            for (int i = 0; i < estaciones.getTamaño(); i++) {
                Estacion otra = (Estacion) estaciones.obtener(i);
                eliminarConexionEntre(otra, idEstacion);
            }
            estaciones.eliminar(estacion);
        }
    }

    /**
     * Elimina la conexión bidireccional entre dos estaciones.
     * 
     * @param idOrigen ID de la primera estación.
     * @param idDestino ID de la segunda estación.
     */
    public void eliminarConexion(String idOrigen, String idDestino) {
        Estacion origen = buscarPorId(idOrigen);
        Estacion destino = buscarPorId(idDestino);

        if (origen != null && destino != null) {
            eliminarConexionEntre(origen, idDestino);
            eliminarConexionEntre(destino, idOrigen);
        }
    }

    /**
     * Elimina la referencia de una conexión en la lista de adyacencia de una estación.
     * 
     * @param estacion Estación de origen.
     * @param idDestino ID de la estación destino.
     */
    private void eliminarConexionEntre(Estacion estacion, String idDestino) {
        ListaEnlazada conexiones = estacion.getConexiones();
        if (conexiones != null) {
            for (int i = 0; i < conexiones.getTamaño(); i++) {
                Conexion con = (Conexion) conexiones.obtener(i);
                if (con.getDestino().getId().equals(idDestino)) {
                    conexiones.eliminarEn(i);
                    break;
                }
            }
        }
    }

    /**
     * Busca una estación por su ID en tiempo O(1) promedio.
     * 
     * @param id ID de la estación.
     * @return La estación correspondiente o null si no se encuentra.
     */
    public Estacion buscarPorId(String id) {
        return tablaPorId.buscar(id);
    }

    /**
     * Busca una estación por su Nombre en tiempo O(1) promedio.
     * 
     * @param nombre Nombre de la estación.
     * @return La estación correspondiente o null si no se encuentra.
     */
    public Estacion buscarPorNombre(String nombre) {
        return tablaPorNombre.buscar(nombre);
    }

    /**
     * Devuelve la lista de conexiones asociadas a un ID de estación.
     * 
     * @param idEstacion ID de la estación.
     * @return ListaEnlazada con los objetos Conexion.
     */
    public ListaEnlazada obtenerConexionesDe(String idEstacion) {
        Estacion estacion = buscarPorId(idEstacion);
        if (estacion != null) {
            return estacion.getConexiones();
        }
        return null;
    }

    /**
     * Retorna la lista completa de estaciones cargadas.
     * 
     * @return ListaEnlazada de estaciones del grafo.
     */
    public ListaEnlazada getEstaciones() {
        return estaciones;
    }
}
