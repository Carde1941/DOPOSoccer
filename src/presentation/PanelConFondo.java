package presentation;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/** Pantalla con una imagen de fondo. */
public class PanelConFondo extends JPanel {
    private static final Color VELO = new Color(10, 30, 60, 140);
    private String imagen;
    private boolean conVelo;

    /**
     * Crea la pantalla.
     *
     * @param imagen el nombre de la imagen de fondo
     * @param conVelo true para oscurecer un poco el fondo y que se lea mejor lo de encima
     */
    public PanelConFondo(String imagen, boolean conVelo) {
        this.imagen = imagen;
        this.conVelo = conVelo;
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int ancho = getWidth();
        int alto = getHeight();
        BufferedImage fondo = Imagenes.get(imagen);
        if (fondo != null) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            double escala = Math.max((double) ancho / fondo.getWidth(), (double) alto / fondo.getHeight());
            int w = (int) Math.ceil(fondo.getWidth() * escala);
            int h = (int) Math.ceil(fondo.getHeight() * escala);
            g2.drawImage(fondo, (ancho - w) / 2, (alto - h) / 2, w, h, null);
            if (conVelo){
                g2.setColor(VELO);
                g2.fillRect(0, 0, ancho, alto);
        }
        } else {
            g2.setPaint(new GradientPaint(0, 0, Estilo.PANEL, 0, alto, Estilo.FONDO));
            g2.fillRect(0, 0, ancho, alto);
        }
        g2.dispose();
    }
}