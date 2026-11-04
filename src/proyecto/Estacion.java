/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;
/**
 * Representa una estación dentro de la red de transporte multimodal.
 * Almacena su identificación, ubicación, capacidad operativa y conexiones directas.
 * 
 * @author josei
 */
public class Estacion {
    private String id;
    private String nombre;
    private String linea;
    private String zona;
    private int capacidadPasajerosHora;
    private ListaEnlazada conexiones;

    /**
     * Constructor principal de la clase Estacion.
     * 
     * @param id Identificador único de la estación.
     * @param nombre Nombre descriptivo de la estación.
     * @param linea Línea de transporte a la que pertenece.
     * @param zona Zona urbana o sector de la ciudad.
     * @param capacidadPasajerosHora Capacidad máxima estimada de pasajeros por hora.
     */
    public Estacion(String id, String nombre, String linea, String zona, int capacidadPasajerosHora) {
        this.id = id;
        this.nombre = nombre;
        this.linea = linea;
        this.zona = zona;
        this.capacidadPasajerosHora = capacidadPasajerosHora;
        this.conexiones = new ListaEnlazada();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getCapacidadPasajerosHora() {
        return capacidadPasajerosHora;
    }

    public void setCapacidadPasajerosHora(int capacidadPasajerosHora) {
        this.capacidadPasajerosHora = capacidadPasajerosHora;
    }

    public ListaEnlazada getConexiones() {
        return conexiones;
    }

    public void setConexiones(ListaEnlazada conexiones) {
        this.conexiones = conexiones;
    }

    /**
     * Retorna el tamaño actual de la lista de conexiones de la estación.
     * 
     * @return El tamaño de las conexiones asociadas.
     */
    public int getTamañoConexiones() {
        return conexiones.getTamaño();
    }

    @Override
    public String toString() {
        return nombre + " (" + id + ")";
    }
}