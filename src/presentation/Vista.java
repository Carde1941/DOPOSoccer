package presentation;

import java.awt.Graphics2D;

import domain.DOPOSoccer;

/** Composite: cualquier cosa que se dibuja en la cancha, sola o en grupo. */
public interface Vista {
    void dibujar(Graphics2D g2, DOPOSoccer juego);
}