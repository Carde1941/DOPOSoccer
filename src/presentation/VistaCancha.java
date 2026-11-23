package presentation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import domain.DOPOSoccer;

/**
 * Hoja: el fondo de la cancha. La imagen ya trae los arcos pintados y su piso coincide con el suelo del dominio.
 * Si la imagen no carga, se pinta un fondo plano y los arcos se dibujan con VistaArco.
 */
public class VistaCancha implements Vista {
    private final Vista arcos = new VistaCompuesta()
        .agregar(new VistaArco(DOPOSoccer.IZQUIERDO))
        .agregar(new VistaArco(DOPOSoccer.DERECHO));

    @Override
    public void dibujar(Graphics2D g2, DOPOSoccer juego) {
        int ancho = (int) juego.getAnchoCancha();
        int alto = (int) juego.getAltoCancha();
        BufferedImage fondo = Imagenes.get("DSCancha.png");
        if (fondo != null) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(fondo, 0, 0, ancho, alto, null);
        } else {
            int suelo = (int) juego.getSuelo();
            g2.setColor(Estilo.FONDO);
            g2.fillRect(0, 0, ancho, suelo);
            g2.setColor(new Color(40, 150, 160));
            g2.fillRect(0, suelo, ancho, alto - suelo);
            arcos.dibujar(g2, juego);
        }
    }
}