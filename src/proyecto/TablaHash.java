/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 *Implementación de una Tabla Hash para el almacenamiento eficiente de Resúmenes.
 * Maneja las colisiones mediante encadenamiento.
 * Permite acceso directo a las investigaciones con complejidad O(1) en el caso promedio.
 * @author josei
 */
public class TablaHash {
    private NodoTabla[] tabla;
    private int capacidad;
    private int tamano;

    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new NodoTabla[capacidad];
        this.tamano = 0;
    }

    private int funcionHash(String clave) {
        char[] arrCodigo = clave.trim().toLowerCase().toCharArray();
        int codigo = 0;
        for (int i=0; i< arrCodigo.length - 1; i++){
            codigo = codigo + (arrCodigo[i]*20^(arrCodigo.length - (i+1)));
        }
        int Indice = codigo % this.capacidad;
        return Math.abs(Indice);
    }
    
    public void insertar(String clave, Estacion valor) {
        int indice = funcionHash(clave);
        NodoTabla actual = tabla[indice];
        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                actual.setValor(valor);
                return;
            }
            actual = actual.getSiguiente();
        }
        NodoTabla nuevoNodo = new NodoTabla(clave, valor);
        nuevoNodo.setSiguiente(tabla[indice]);
        tabla[indice] = nuevoNodo;
        tamano++;
    }
    
    public Estacion buscar(String clave) {
        int indice = funcionHash(clave);
        NodoTabla actual = tabla[indice];

        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                return actual.getValor();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
}
