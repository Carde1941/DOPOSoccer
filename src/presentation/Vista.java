package presentation;

import java.awt.Graphics2D;

import domain.DOPOSoccer;

/** Algo que se dibuja en la cancha: el balón, un jugador, el marcador, o un grupo de ellos. */
public interface Vista {
    /**
     * Lo dibuja con la información que da el juego.
     *
     * @param g2 donde se dibuja
     * @param juego el juego; la pantalla le pide a él toda la información
     */
    void dibujar(Graphics2D g2, DOPOSoccer juego);
}