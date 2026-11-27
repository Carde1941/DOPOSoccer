package presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import domain.DOPOSoccer;

/** Hoja: el balón, blanco con un pentágono negro; con estela si lleva un tiro de poder y repetido si hay confusión. */
public class VistaBalon implements Vista {
    private static final int TRAMOS_ESTELA = 6;
    private static final double SEGUNDOS_POR_TRAMO = 0.012;

    @Override
    public void dibujar(Graphics2D g2, DOPOSoccer juego) {
        int r = (int) juego.getRadioBalon();
        int suelo = (int) juego.getSuelo();
        for (int i = 0; i < juego.getCantidadBalonesFalsos(); i++) {
            balon(g2, (int) juego.getXBalonFalso(i), (int) juego.getYBalonFalso(i), r, suelo);
        }
        int cx = (int) juego.getXBalon();
        int cy = (int) juego.getYBalon();
        if (juego.balonTienePoder()) {
            estela(g2, juego, Apariencia.aura(juego.getPersonajeDelPoder()), cx, cy, r);
        }
        balon(g2, cx, cy, r, suelo);
    }

    /** Círculos que se desvanecen detrás del balón, en sentido contrario a su movimiento. */
    private void estela(Graphics2D g2, DOPOSoccer juego, Color color, int cx, int cy, int r) {
        double vx = juego.getVxBalon();
        double vy = juego.getVyBalon();
        for (int i = TRAMOS_ESTELA; i >= 1; i--) {
            int x = (int) (cx - vx * SEGUNDOS_POR_TRAMO * i);
            int y = (int) (cy - vy * SEGUNDOS_POR_TRAMO * i);
            int radio = (int) (r * (1.3 - 0.12 * i));
            int alfa = 200 - i * 28;
            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alfa));
            g2.fillOval(x - radio, y - radio, radio * 2, radio * 2);
        }
        int brillo = (int) (r * 1.5);
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 110));
        g2.fillOval(cx - brillo, cy - brillo, brillo * 2, brillo * 2);
    }

    private void balon(Graphics2D g2, int cx, int cy, int r, int suelo) {
        g2.setColor(new Color(0, 0, 0, 70));
        g2.fillOval(cx - r, suelo - 5, r * 2, 10);

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
