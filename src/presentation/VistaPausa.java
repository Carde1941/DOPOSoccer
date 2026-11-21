package presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import domain.DOPOSoccer;

/** Hoja: oscurece la cancha y muestra PAUSA cuando el partido está pausado. */
public class VistaPausa implements Vista {
    private static final Font GRANDE = Fuentes.juego(96f);
    private static final Font PEQUENA = Estilo.NORMAL.deriveFont(24f);

    @Override
    public void dibujar(Graphics2D g2, DOPOSoccer juego) {
        if (!juego.estaPausado()) {
            return;
        }
        int ancho = (int) juego.getAnchoCancha();
        int alto = (int) juego.getAltoCancha();
        g2.setColor(new Color(10, 4, 22, 160));
        g2.fillRect(0, 0, ancho, alto);
        centrado(g2, "PAUSA", GRANDE, Estilo.AMARILLO, ancho, alto / 2);
        centrado(g2, "P para seguir  ·  Esc para terminar", PEQUENA, Estilo.TEXTO, ancho, alto / 2 + 50);
    }

    private void centrado(Graphics2D g2, String texto, Font fuente, Color color, int ancho, int y) {
        g2.setFont(fuente);
        int x = (ancho - g2.getFontMetrics().stringWidth(texto)) / 2;
        g2.setColor(Estilo.CONTORNO);
        g2.drawString(texto, x + 4, y + 4);
        g2.setColor(color);
        g2.drawString(texto, x, y);
    }
}