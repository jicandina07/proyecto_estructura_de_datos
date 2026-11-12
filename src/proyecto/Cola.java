/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 * Estructura de datos FIFO apoyada en la ListaEnlazada propia.
 * 
 * @author josei
 */
public class Cola {
    private ListaEnlazada lista;

    public Cola() {
        this.lista = new ListaEnlazada();
    }

    public void encolar(Object dato) {
        lista.agregar(dato);
    }

    public Object desencolar() {
        if (esVacia()) {
            return null;
        }
        Object primero = lista.obtener(0);
        lista.eliminarEn(0);
        return primero;
    }

    public boolean esVacia() {
        return lista.esVacia();
    }

    public int getTamaño() {
        return lista.getTamaño();
    }
}
