package presentation;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

/** Texto grande con contorno, para los títulos de las pantallas. */
public class Titulo extends JComponent {
    private String texto;

    /**
     * Crea el título.
     *
     * @param texto lo que dice
     */
    public Titulo(String texto) {
        this.texto = texto.toUpperCase();
        setFont(Estilo.TITULO);
        setPreferredSize(new Dimension(600, 58));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(texto)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.setColor(Estilo.CONTORNO);
        for (int dx = -3; dx <= 3; dx += 3) {
            for (int dy = -3; dy <= 3; dy += 3) {
                g2.drawString(texto, x + dx, y + dy);
            }
        }
        g2.setColor(Estilo.AMARILLO);
        g2.drawString(texto, x, y);
        g2.dispose();
    }
}