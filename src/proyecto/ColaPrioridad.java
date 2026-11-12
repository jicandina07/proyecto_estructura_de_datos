/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 * Cola de prioridad simple para nodos de exploración en Dijkstra.
 * 
 * @author josei
 */
public class ColaPrioridad {
    
    private class Elemento {
        Estacion estacion;
        int prioridad;

        Elemento(Estacion estacion, int prioridad) {
            this.estacion = estacion;
            this.prioridad = prioridad;
        }
    }

    private ListaEnlazada elementos;

    public ColaPrioridad() {
        this.elementos = new ListaEnlazada();
    }

    public void insertar(Estacion estacion, int prioridad) {
        Elemento nuevo = new Elemento(estacion, prioridad);
        if (elementos.esVacia()) {
            elementos.agregar(nuevo);
            return;
        }

        boolean insertado = false;
        for (int i = 0; i < elementos.getTamaño(); i++) {
            Elemento actual = (Elemento) elementos.obtener(i);
            if (prioridad < actual.prioridad) {
                insertarEn(i, nuevo);
                insertado = true;
                break;
            }
        }
        if (!insertado) {
            elementos.agregar(nuevo);
        }
    }

    private void insertarEn(int indice, Elemento nuevo) {
        ListaEnlazada auxiliar = new ListaEnlazada();
        for (int i = 0; i < elementos.getTamaño(); i++) {
            if (i == indice) {
                auxiliar.agregar(nuevo);
            }
            auxiliar.agregar(elementos.obtener(i));
        }
        elementos = auxiliar;
    }

    public Estacion extraerMinimo() {
        if (elementos.esVacia()) {
            return null;
        }
        Elemento min = (Elemento) elementos.obtener(0);
        elementos.eliminarEn(0);
        return min.estacion;
    }

    public boolean esVacia() {
        return elementos.esVacia();
    }
}