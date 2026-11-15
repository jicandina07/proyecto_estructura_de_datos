# UrbanRoute

Aplicación de escritorio en Java Swing para gestionar y analizar redes de transporte urbano usando grafos y estructuras de datos propias (sin colecciones de `java.util`).

## Funcionalidades

* **Carga y guardado en CSV:** Lee y exporta la red usando dos archivos (estaciones y conexiones).
* **Mapa de la red:** Dibuja las estaciones y sus conexiones en pantalla, resaltando la ruta calculada.
* **Búsqueda rápida:** Busca estaciones por ID o nombre mediante tablas hash (ignora mayúsculas y tildes).
* **Gestión de nodos y tramos:** Permite agregar o eliminar estaciones y conexiones bidireccionales.
* **Ruta más rápida:** Calcula el camino de menor tiempo entre dos estaciones usando Dijkstra.
* **Subredes y Hubs:** Detecta componentes conectados o estaciones aisladas (DFS) y muestra las estaciones con más conexiones.

## Organización del código (`proyecto`)

* **Estructuras y modelo:** `Grafo`, `Estacion`, `Conexion`, `ListaEnlazada`, `NodoLista`, `TablaHash`, `NodoTabla`.
* **Lógica y archivos:** `AnalizadorRedes`, `GestorArchivos`, `ControladorRed`.
* **Interfaz:** `VentanaPrincipal`, `VisualizadorGrafo`, `VentanaEstaciones`, `VentanaAgregarEstacion`, `VentanaConexiones`, `VentanaAgregarConexion`, `VentanaRutaRapida`, `VentanaSubredes`, `VentanaHubs`.

## Cómo ejecutarlo

1. Abrir el proyecto en **NetBeans** (JDK 11 o superior).
2. Ejecutar `proyecto.VentanaPrincipal`.
3. Pulsar **Cargar red** y seleccionar primero el archivo de estaciones (`diccionario.csv`) y luego el de conexiones (`grafo.csv`).