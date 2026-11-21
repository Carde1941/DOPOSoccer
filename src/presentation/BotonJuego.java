package presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class BotonJuego extends JButton {
    private static final int ARCO = 18;
    private static final int BORDE = 3;
    private static final int SOMBRA = 6;
    private Color color;

    public BotonJuego(String texto, Color color) {
        super(texto.toUpperCase());
        this.color = color;
        setFont(Estilo.BOTON);
        setForeground(Estilo.TEXTO);
        setPreferredSize(new Dimension(300, 50));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Color base = isEnabled() ? color : Estilo.DESHABILITADO;
        if (getModel().isRollover() && isEnabled()) {
            base = base.brighter();
        }
        int ancho = getWidth() - BORDE;
        int alto = getHeight() - SOMBRA - BORDE;
        int hundido = getModel().isPressed() ? SOMBRA - 2 : 0;
        int x0 = BORDE / 2;
        int y0 = BORDE / 2 + hundido;

        // sombra dura, como en los dibujos pixelados
        g2.setColor(Estilo.CONTORNO);
        g2.fillRoundRect(x0, BORDE / 2 + SOMBRA, ancho, alto, ARCO, ARCO);
        // cuerpo con degradado y franja clara arriba
        g2.setPaint(new GradientPaint(0, y0, base.brighter(), 0, y0 + alto, base.darker()));
        g2.fillRoundRect(x0, y0, ancho, alto, ARCO, ARCO);
        g2.setColor(new Color(255, 255, 255, 70));
        g2.fillRoundRect(x0 + 6, y0 + 4, ancho - 12, alto / 3, ARCO / 2, ARCO / 2);
        // contorno grueso
        g2.setStroke(new BasicStroke(BORDE));
        g2.setColor(Estilo.CONTORNO);
        g2.drawRoundRect(x0, y0, ancho, alto, ARCO, ARCO);

        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = y0 + (alto - fm.getHeight()) / 2 + fm.getAscent();
        // texto con contorno negro
        g2.setColor(Estilo.CONTORNO);
        for (int dx = -2; dx <= 2; dx += 2) {
            for (int dy = -2; dy <= 2; dy += 2) {
                g2.drawString(getText(), x + dx, y + dy);
            }
        }
        g2.setColor(isEnabled() ? getForeground() : new Color(200, 205, 212));
        g2.drawString(getText(), x, y);
        g2.dispose();
    }
}