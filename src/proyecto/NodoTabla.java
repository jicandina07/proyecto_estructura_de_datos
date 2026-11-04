/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 *Nodo especializado para la Tabla Hash.
 *Contiene la clave, y un puntero al siguiente nodo
 *para manejar colisiones por encadenamiento.
 *
 * @author josei
 */
public class NodoTabla {
    private String clave;
    private Estacion valor;
    private NodoTabla siguiente;

    /**
     * Constructor principal del nodo de la tabla hash.
     * 
     * @param clave Clave única de la estación (ID o Nombre).
     * @param valor Referencia al objeto Estacion asociado.
     */
    public NodoTabla(String clave, Estacion valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Estacion getValor() {
        return valor;
    }

    public void setValor(Estacion valor) {
        this.valor = valor;
    }

    public NodoTabla getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoTabla siguiente) {
        this.siguiente = siguiente;
    }
}
