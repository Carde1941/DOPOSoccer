package presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import domain.DOPOSoccer;

/** Dibuja un arco con su malla y sus postes. */
public class VistaArco implements Vista {
    private static final Color MALLA = new Color(230, 230, 240, 150);
    private static final Color ROJO = new Color(225, 40, 50);
    private static final int GROSOR = 12;
    private static final int RAYA = 22;
    private int lado;

    /**
     * Crea el dibujo del arco.
     *
     * @param lado el arco: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     */
    public VistaArco(int lado) {
        this.lado = lado;
    }

    @Override
    public void dibujar(Graphics2D g2, DOPOSoccer juego) {
        int ancho = (int) juego.getAnchoArco();
        int alto = (int) juego.getAltoArco();
        int suelo = (int) juego.getSuelo();
        int x = lado == DOPOSoccer.IZQUIERDO ? 0 : (int) juego.getAnchoCancha() - ancho;
        int arriba = suelo - alto;

        g2.setColor(MALLA);
        g2.setStroke(new BasicStroke(1));
        for (int i = x; i <= x + ancho; i += 12) {
            g2.drawLine(i, arriba, i, suelo);
        }
        for (int j = arriba; j <= suelo; j += 12) {
            g2.drawLine(x, j, x + ancho, j);
        }

        int poste = lado == DOPOSoccer.IZQUIERDO ? x + ancho - GROSOR : x;
        for (int j = arriba; j < suelo; j += RAYA) {
            g2.setColor((j - arriba) / RAYA % 2 == 0 ? ROJO : Color.WHITE);
            g2.fillRect(poste, j, GROSOR, Math.min(RAYA, suelo - j));
        }
        for (int i = x; i < x + ancho; i += RAYA) {
            g2.setColor((i - x) / RAYA % 2 == 0 ? Color.WHITE : ROJO);
            g2.fillRect(i, arriba - GROSOR / 2, Math.min(RAYA, x + ancho - i), GROSOR);
        }
    }
}