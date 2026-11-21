package presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/** Panel oscuro semitransparente con borde de color redondeado. */
public class PanelNeon extends JPanel {
    private static final int ARCO = 22;
    private static final int BORDE = 3;
    private Color color;

    public PanelNeon(Color color) {
        this.color = color;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(20, 10, 40, 205));
        g2.fillRoundRect(BORDE, BORDE, getWidth() - 2 * BORDE, getHeight() - 2 * BORDE, ARCO, ARCO);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(BORDE));
        g2.drawRoundRect(BORDE, BORDE, getWidth() - 2 * BORDE, getHeight() - 2 * BORDE, ARCO, ARCO);
        g2.dispose();
        super.paintComponent(g);
    }
}