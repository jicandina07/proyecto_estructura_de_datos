/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Se encarga de leer y guardar los archivos CSV del sistema.
 * 
 * @author josei
 */
public class GestorArchivos {

    /**
     * Lee los archivos CSV de estaciones y conexiones para construir el grafo.
     * 
     * @param archivoEstaciones Archivo CSV con las estaciones.
     * @param archivoConexiones Archivo CSV con las conexiones.
     * @return El grafo cargado con los datos de los archivos.
     */
    public Grafo cargarRed(File archivoEstaciones, File archivoConexiones) {
        Grafo grafo = new Grafo();
        try {
            if (archivoEstaciones != null && archivoEstaciones.exists()) {
                try (BufferedReader readerEst = new BufferedReader(new FileReader(archivoEstaciones))) {
                    String linea = readerEst.readLine();
                    while ((linea = readerEst.readLine()) != null) {
                        linea = linea.trim();
                        if (!linea.isEmpty()) {
                            String[] datos = linea.split(",");
                            if (datos.length >= 5) {
                                String id = datos[0].trim();
                                String nombre = datos[1].trim();
                                String lineaTransporte = datos[2].trim();
                                String zona = datos[3].trim();
                                int capacidad = Integer.parseInt(datos[4].trim());
                                
                                Estacion est = new Estacion(id, nombre, lineaTransporte, zona, capacidad);
                                grafo.agregarEstacion(est);
                            }
                        }
                    }
                }
            }

            if (archivoConexiones != null && archivoConexiones.exists()) {
                try (BufferedReader readerCon = new BufferedReader(new FileReader(archivoConexiones))) {
                    String linea = readerCon.readLine();
                    while ((linea = readerCon.readLine()) != null) {
                        linea = linea.trim();
                        if (!linea.isEmpty()) {
                            String[] datos = linea.split(",");
                            if (datos.length >= 3) {
                                String origen = datos[0].trim();
                                String destino = datos[1].trim();
                                int tiempo = Integer.parseInt(datos[2].trim());
                                
                                grafo.agregarConexion(origen, destino, tiempo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer los archivos");
        }
        return grafo;
    }

    /**
     * Guarda la informacion del grafo en los archivos CSV concatenando el texto.
     * 
     * @param grafo Grafo actual en memoria.
     * @param rutaEstaciones Ruta donde se guardara el CSV de estaciones.
     * @param rutaConexiones Ruta donde se guardara el CSV de conexiones.
     * @return true si se guardo correctamente, false si hubo error.
     */
    public boolean guardarRed(Grafo grafo, String rutaEstaciones, String rutaConexiones) {
        try {
            String textoEstaciones = "ID_Estacion,Nombre,Linea,Zona,Capacidad_Pasajeros_Hora\n";
            ListaEnlazada estaciones = grafo.getEstaciones();

            for (int i = 0; i < estaciones.getTamaño(); i++) {
                Estacion est = (Estacion) estaciones.obtener(i);
                textoEstaciones = textoEstaciones + est.getId() + "," 
                                + est.getNombre() + "," 
                                + est.getLinea() + "," 
                                + est.getZona() + "," 
                                + est.getCapacidadPasajerosHora() + "\n";
            }

            FileWriter writerEst = new FileWriter(rutaEstaciones);
            writerEst.write(textoEstaciones);
            writerEst.close();

            String textoConexiones = "Estacion_Origen,Estacion_Destino,Tiempo_Minutos\n";
            ListaEnlazada guardadas = new ListaEnlazada();

            for (int i = 0; i < estaciones.getTamaño(); i++) {
                Estacion origen = (Estacion) estaciones.obtener(i);
                ListaEnlazada conexiones = origen.getConexiones();

                for (int j = 0; j < conexiones.getTamaño(); j++) {
                    Conexion con = (Conexion) conexiones.obtener(j);
                    String idA = origen.getId();
                    String idB = con.getDestino().getId();
                    String enlace1 = idA + "-" + idB;
                    String enlace2 = idB + "-" + idA;

                    if (!guardadas.contiene(enlace1) && !guardadas.contiene(enlace2)) {
                        textoConexiones = textoConexiones + idA + "," + idB + "," + con.getTiempoMinutos() + "\n";
                        guardadas.agregar(enlace1);
                    }
                }
            }

            FileWriter writerCon = new FileWriter(rutaConexiones);
            writerCon.write(textoConexiones);
            writerCon.close();

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}