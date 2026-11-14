/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Panel encargado de dibujar el grafo de estaciones y conexiones.
 * 
 * @author josei
 */
public class VisualizadorGrafo extends JPanel {
    private Grafo grafo;
    private ListaEnlazada rutaResaltada;

    public VisualizadorGrafo() {
        this.setBackground(Color.WHITE);
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
        this.rutaResaltada = null;
        repaint();
    }

    public void setRutaResaltada(ListaEnlazada ruta) {
        this.rutaResaltada = ruta;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grafo == null || grafo.getEstaciones().esVacia()) {
            g.setColor(Color.GRAY);
            g.drawString("Cargue una red para visualizar el mapa", 40, 90);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        ListaEnlazada estaciones = grafo.getEstaciones();
        int total = estaciones.getTamaño();

        int[] posX = new int[total];
        int[] posY = new int[total];

        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int radio = Math.min(centroX, centroY) - 35;
        if (radio < 40) {
            radio = 40;
        }

        for (int i = 0; i < total; i++) {
            double angulo = (2 * Math.PI * i) / total;
            posX[i] = centroX + (int) (radio * Math.cos(angulo));
            posY[i] = centroY + (int) (radio * Math.sin(angulo));
        }

        for (int i = 0; i < total; i++) {
            Estacion origen = (Estacion) estaciones.obtener(i);
            ListaEnlazada conexiones = origen.getConexiones();
            for (int j = 0; j < conexiones.getTamaño(); j++) {
                Conexion con = (Conexion) conexiones.obtener(j);
                int idxDestino = buscarIndice(estaciones, con.getDestino().getId());
                if (idxDestino > i) {
                    boolean enRuta = esTramoDeRuta(origen.getId(), con.getDestino().getId());
                    if (enRuta) {
                        g2.setColor(Color.RED);
                        g2.setStroke(new BasicStroke(3));
                    } else {
                        g2.setColor(Color.LIGHT_GRAY);
                        g2.setStroke(new BasicStroke(1));
                    }
                    g2.drawLine(posX[i], posY[i], posX[idxDestino], posY[idxDestino]);

                    int midX = (posX[i] + posX[idxDestino]) / 2;
                    int midY = (posY[i] + posY[idxDestino]) / 2;
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawString(con.getTiempoMinutos() + "m", midX, midY);
                }
            }
        }

        for (int i = 0; i < total; i++) {
            Estacion est = (Estacion) estaciones.obtener(i);
            if (estaEnRuta(est.getId())) {
                g2.setColor(Color.RED);
            } else {
                g2.setColor(new Color(0, 102, 204));
            }
            g2.fillOval(posX[i] - 12, posY[i] - 12, 24, 24);
            g2.setColor(Color.WHITE);
            g2.drawString(est.getId(), posX[i] - 9, posY[i] + 4);
            g2.setColor(Color.BLACK);
            g2.drawString(est.getNombre(), posX[i] - 20, posY[i] - 15);
        }
    }

    private int buscarIndice(ListaEnlazada estaciones, String id) {
        for (int i = 0; i < estaciones.getTamaño(); i++) {
            Estacion e = (Estacion) estaciones.obtener(i);
            if (e.getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private boolean estaEnRuta(String id) {
        if (rutaResaltada == null) return false;
        for (int i = 0; i < rutaResaltada.getTamaño(); i++) {
            Estacion e = (Estacion) rutaResaltada.obtener(i);
            if (e.getId().equals(id)) return true;
        }
        return false;
    }

    private boolean esTramoDeRuta(String idA, String idB) {
        if (rutaResaltada == null) return false;
        for (int i = 0; i < rutaResaltada.getTamaño() - 1; i++) {
            Estacion actual = (Estacion) rutaResaltada.obtener(i);
            Estacion sig = (Estacion) rutaResaltada.obtener(i + 1);
            if ((actual.getId().equals(idA) && sig.getId().equals(idB)) ||
                (actual.getId().equals(idB) && sig.getId().equals(idA))) {
                return true;
            }
        }
        return false;
    }
}