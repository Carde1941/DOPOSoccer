package presentation;

import java.awt.Graphics2D;

/** Strategy: cómo se dibuja sobre el personaje el efecto que le dejó un tiro de poder. */
@FunctionalInterface
public interface AdornoEfecto {
    /**
     * @param centro x del centro del personaje
     * @param arriba y de la parte de arriba de su caja
     * @param ancho  ancho de su caja
     * @param alto   alto de su caja
     * @param tiempo segundos que lleva el juego, para animar
     */
    void dibujar(Graphics2D g2, int centro, int arriba, int ancho, int alto, double tiempo);
}
