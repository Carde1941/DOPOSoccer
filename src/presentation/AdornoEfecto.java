package presentation;

import java.awt.Graphics2D;

/** Lo que se dibuja encima de un personaje según el efecto que tenga, como los rayos del electrocutado. */
@FunctionalInterface
public interface AdornoEfecto {
    /**
     * Dibuja el adorno alrededor del personaje.
     *
     * @param g2 donde se dibuja
     * @param centro el centro del personaje, de izquierda a derecha
     * @param arriba dónde empieza el personaje, de arriba hacia abajo
     * @param ancho el ancho del personaje
     * @param alto el alto del personaje
     * @param tiempo los segundos que llevan corriendo, para animar
     */
    void dibujar(Graphics2D g2, int centro, int arriba, int ancho, int alto, double tiempo);
}
