package presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import domain.DOPOSoccer;

/** Hoja: el balón, blanco con un pentágono negro en el centro. */
public class VistaBalon implements Vista {

    @Override
    public void dibujar(Graphics2D g2, DOPOSoccer juego) {
        int r = (int) juego.getRadioBalon();
        int cx = (int) juego.getXBalon();
        int cy = (int) juego.getYBalon();

        g2.setColor(new Color(0, 0, 0, 70));
        g2.fillOval(cx - r, (int) juego.getSuelo() - 5, r * 2, 10);

        g2.setColor(Color.WHITE);
        g2.fillOval(cx - r, cy - r, r * 2, r * 2);
        Polygon pentagono = new Polygon();
        for (int i = 0; i < 5; i++) {
            double angulo = -Math.PI / 2 + i * 2 * Math.PI / 5;
            pentagono.addPoint(cx + (int) (r * 0.42 * Math.cos(angulo)), cy + (int) (r * 0.42 * Math.sin(angulo)));
        }
        g2.setColor(Estilo.CONTORNO);
        g2.fillPolygon(pentagono);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(cx - r, cy - r, r * 2, r * 2);
    }
}