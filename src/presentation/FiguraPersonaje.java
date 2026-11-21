package presentation;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/** Dibuja a un personaje como cabeza grande con un guayo de perfil asomando debajo. */
public final class FiguraPersonaje {
    private static final double ALTO_CABEZA = 1.0;
    private static final double ALTO_GUAYO = 0.32;
    private static final double AVANCE_PATADA = 0.5;

    private FiguraPersonaje() { }

    /**
     * @param centro         x del centro del personaje
     * @param pies           y donde apoya el guayo
     * @param alto           alto total de la figura
     * @param mirandoDerecha true si la punta del guayo apunta a la derecha
     * @param pateando       true para mover el guayo hacia adelante
     */
    public static void dibujar(Graphics2D g, Pais pais, int centro, int pies, int alto,
                               boolean mirandoDerecha, boolean pateando) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int altoGuayo = (int) (alto * ALTO_GUAYO);
        int sentido = mirandoDerecha ? 1 : -1;
        dibujarGuayo(g2, pais, centro, pies, altoGuayo, sentido, pateando);
        dibujarCabeza(g2, pais, centro, pies - altoGuayo / 3, (int) (alto * ALTO_CABEZA), sentido);
        g2.dispose();
    }

    /** La imagen mira a la derecha; con sentido -1 se dibuja en espejo para que mire a la izquierda. */
    private static void dibujarCabeza(Graphics2D g2, Pais pais, int centro, int abajo, int alto, int sentido) {
        BufferedImage cabeza = Imagenes.get(pais.getCabeza());
        if (cabeza == null) {
            return;
        }
        int ancho = alto * cabeza.getWidth() / cabeza.getHeight();
        int izquierda = centro - sentido * ancho / 2;
        g2.drawImage(cabeza, izquierda, abajo - alto, sentido * ancho, alto, null);
    }

    /** El guayo va antes que la cabeza: la cabeza lo tapa por arriba y asoma la punta. */
    private static void dibujarGuayo(Graphics2D g2, Pais pais, int centro, int pies, int alto,
                                     int sentido, boolean pateando) {
        BufferedImage guayo = Imagenes.get(pais.getGuayo());
        if (guayo == null) {
            return;
        }
        int ancho = alto * guayo.getWidth() / guayo.getHeight();
        Graphics2D pie = (Graphics2D) g2.create();
        pie.translate(centro - sentido * ancho / 5, pies);
        pie.scale(sentido, 1);
        if (pateando) {
            pie.translate(ancho * AVANCE_PATADA, 0);
        }
        pie.drawImage(guayo, new AffineTransform((double) ancho / guayo.getWidth(), 0, 0,
            (double) alto / guayo.getHeight(), 0, -alto), null);
        pie.dispose();
    }
}