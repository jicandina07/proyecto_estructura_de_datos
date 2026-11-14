/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.text.Normalizer;

/**
 * Implementacion de una Tabla Hash para el almacenamiento eficiente de Estaciones.
 * Maneja las colisiones mediante encadenamiento.
 * 
 * @author josei
 */
public class TablaHash {
    private NodoTabla[] tabla;
    private int capacidad;
    private int tamaño;

    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new NodoTabla[capacidad];
        this.tamaño = 0;
    }

    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String sinAcentos = Normalizer.normalize(texto.trim().toLowerCase(), Normalizer.Form.NFD);
        return sinAcentos.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private int funcionHash(String clave) {
        String limpia = normalizar(clave);
        int codigo = 0;
        for (int i = 0; i < limpia.length(); i++) {
            codigo = (31 * codigo + limpia.charAt(i)) % capacidad;
        }
        return Math.abs(codigo);
    }

    public void insertar(String clave, Estacion valor) {
        if (clave == null) {
            return;
        }
        String limpia = normalizar(clave);
        int indice = funcionHash(limpia);
        NodoTabla actual = tabla[indice];
        while (actual != null) {
            if (normalizar(actual.getClave()).equals(limpia)) {
                actual.setValor(valor);
                return;
            }
            actual = actual.getSiguiente();
        }
        NodoTabla nuevoNodo = new NodoTabla(clave.trim(), valor);
        nuevoNodo.setSiguiente(tabla[indice]);
        tabla[indice] = nuevoNodo;
        tamaño++;
    }

    public Estacion buscar(String clave) {
        if (clave == null) {
            return null;
        }
        String limpia = normalizar(clave);
        int indice = funcionHash(limpia);
        NodoTabla actual = tabla[indice];
        while (actual != null) {
            if (normalizar(actual.getClave()).equals(limpia)) {
                return actual.getValor();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public void eliminar(String clave) {
        if (clave == null) {
            return;
        }
        String limpia = normalizar(clave);
        int indice = funcionHash(limpia);
        NodoTabla actual = tabla[indice];
        NodoTabla anterior = null;

        while (actual != null) {
            if (normalizar(actual.getClave()).equals(limpia)) {
                if (anterior == null) {
                    tabla[indice] = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                tamaño--;
                return;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
    }

    public int getTamaño() {
        return tamaño;
    }
}