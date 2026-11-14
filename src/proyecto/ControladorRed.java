/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.io.File;
/**
 * Controlador principal que comunica la interfaz grafica con el grafo,
 * los algoritmos de analisis y la gestion de archivos CSV.
 * 
 * @author josei
 */
public class ControladorRed {
    private Grafo grafo;
    private AnalizadorRedes analizador;
    private GestorArchivos gestor;
    private boolean cambiosSinGuardar;

    public ControladorRed() {
        this.grafo = new Grafo();
        this.analizador = new AnalizadorRedes();
        this.gestor = new GestorArchivos();
        this.cambiosSinGuardar = false;
    }
    public Grafo getGrafo() {
        return grafo;
    }
    public boolean hayCambiosSinGuardar() {
        return cambiosSinGuardar;
    }
    public boolean cargarArchivos(File archivoEstaciones, File archivoConexiones) {
        Grafo nuevoGrafo = gestor.cargarRed(archivoEstaciones, archivoConexiones);
        if (nuevoGrafo != null && !nuevoGrafo.getEstaciones().esVacia()) {
            this.grafo = nuevoGrafo;
            this.cambiosSinGuardar = false;
            return true;
        }
        return false;
    }
    public boolean guardarArchivos(String rutaEstaciones, String rutaConexiones) {
        boolean exito = gestor.guardarRed(grafo, rutaEstaciones, rutaConexiones);
        if (exito) {
            this.cambiosSinGuardar = false;
        }
        return exito;
    }

    public boolean agregarEstacion(String id, String nombre, String linea, String zona, int capacidad) {
        if (grafo.buscarPorId(id) != null || grafo.buscarPorNombre(nombre) != null) {
            return false;
        }
        Estacion nueva = new Estacion(id, nombre, linea, zona, capacidad);
        grafo.agregarEstacion(nueva);
        this.cambiosSinGuardar = true;
        return true;
    }

    public void eliminarEstacion(String id) {
        if (grafo.buscarPorId(id) != null) {
            grafo.eliminarEstacion(id);
            this.cambiosSinGuardar = true;
        }
    }

    public boolean agregarConexion(String idOrigen, String idDestino, int tiempoMinutos) {
        if (idOrigen.equals(idDestino) || tiempoMinutos <= 0) {
            return false;
        }
        Estacion origen = grafo.buscarPorId(idOrigen);
        Estacion destino = grafo.buscarPorId(idDestino);
        if (origen == null || destino == null) {
            return false;
        }
        ListaEnlazada conexiones = origen.getConexiones();
        for (int i = 0; i < conexiones.getTamaño(); i++) {
            Conexion con = (Conexion) conexiones.obtener(i);
            if (con.getDestino().getId().equals(idDestino)) {
                return false;
            }
        }
        grafo.agregarConexion(idOrigen, idDestino, tiempoMinutos);
        this.cambiosSinGuardar = true;
        return true;
    }

    public void eliminarConexion(String idOrigen, String idDestino) {
        grafo.eliminarConexion(idOrigen, idDestino);
        this.cambiosSinGuardar = true;
    }

    public Estacion buscarEstacion(String texto) {
        Estacion porId = grafo.buscarPorId(texto);
        if (porId != null) {
            return porId;
        }
        return grafo.buscarPorNombre(texto);
    }

    public ListaEnlazada calcularRutaMinima(String idOrigen, String idDestino) {
        return analizador.calcularRutaMinima(grafo, idOrigen, idDestino);
    }

    public int calcularTiempoRuta(ListaEnlazada ruta) {
        return analizador.calcularTiempoRuta(ruta);
    }

    public ListaEnlazada obtenerSubredes() {
        return analizador.obtenerSubredes(grafo);
    }

    public ListaEnlazada obtenerHubs() {
        return analizador.obtenerHubs(grafo);
    }
}
