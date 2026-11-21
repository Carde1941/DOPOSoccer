package presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import domain.DOPOSoccer;

/** Hoja: el HUD de arriba con país, vida y poder de cada jugador, y el marcador en el centro. */
public class VistaMarcador implements Vista {
    private static final Color CAJA = new Color(20, 10, 40, 215);
    private static final Color VACIO = new Color(60, 45, 90);
    private static final Color VIDA = new Color(80, 220, 110);
    private static final Font NOMBRE = Fuentes.juego(22f);
    private static final Font GOLES = Fuentes.juego(48f);
    private static final Font INFO = Fuentes.juego(18f);
    private static final Font ETIQUETA = Estilo.NORMAL.deriveFont(13f);
    private static final int ANCHO_LADO = 420;
    private static final int ANCHO_CENTRO = 220;
    private static final int MARGEN = 16;
    private static final int ALTO = 96;

    private Pais pais1;
    private Pais pais2;

    public VistaMarcador(Pais pais1, Pais pais2) {
        this.pais1 = pais1;
        this.pais2 = pais2;
    }

    @Override
    public void dibujar(Graphics2D g2, DOPOSoccer juego) {
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int ancho = (int) juego.getAnchoCancha();
        dibujarLado(g2, juego, DOPOSoccer.IZQUIERDO, pais1, MARGEN, Estilo.CIAN);
        dibujarLado(g2, juego, DOPOSoccer.DERECHO, pais2, ancho - MARGEN - ANCHO_LADO, Estilo.ROSADO);
        dibujarCentro(g2, juego, (ancho - ANCHO_CENTRO) / 2);
    }

    private void dibujarLado(Graphics2D g2, DOPOSoccer juego, int lado, Pais pais, int x, Color color) {
        caja(g2, x, MARGEN, ANCHO_LADO, ALTO, color);
        boolean izquierdo = lado == DOPOSoccer.IZQUIERDO;
        g2.setFont(NOMBRE);
        g2.setColor(Estilo.TEXTO);
        String nombre = pais.toString().toUpperCase();
        int anchoNombre = g2.getFontMetrics().stringWidth(nombre);
        g2.drawString(nombre, izquierdo ? x + 18 : x + ANCHO_LADO - 18 - anchoNombre, MARGEN + 34);

        double vida = juego.getVida(lado) / juego.getVidaMaxima(lado);
        double poder = juego.getProgresoPoder(lado);
        barra(g2, x + 18, MARGEN + 46, ANCHO_LADO - 36, 16, vida, VIDA, "VIDA", izquierdo);
        barra(g2, x + 18, MARGEN + 70, ANCHO_LADO - 36, 12, poder, poder >= 1 ? Estilo.AMARILLO : color, "PODER", izquierdo);
    }

    private void dibujarCentro(Graphics2D g2, DOPOSoccer juego, int x) {
        caja(g2, x, MARGEN, ANCHO_CENTRO, ALTO, Estilo.AMARILLO);
        String marcador = juego.getGoles(DOPOSoccer.IZQUIERDO) + " - " + juego.getGoles(DOPOSoccer.DERECHO);
        centrado(g2, marcador, GOLES, Estilo.AMARILLO, x, ANCHO_CENTRO, MARGEN + 56);
        centrado(g2, juego.getInformacion().toUpperCase(), INFO, Estilo.TEXTO, x, ANCHO_CENTRO, MARGEN + 84);
    }

    private void barra(Graphics2D g2, int x, int y, int ancho, int alto, double progreso, Color color,
                       String etiqueta, boolean izquierdo) {
        double p = Math.max(0, Math.min(1, progreso));
        int lleno = (int) (ancho * p);
        g2.setColor(VACIO);
        g2.fillRoundRect(x, y, ancho, alto, alto, alto);
        g2.setColor(color);
        g2.fillRoundRect(izquierdo ? x : x + ancho - lleno, y, lleno, alto, alto, alto);
        g2.setColor(Estilo.CONTORNO);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, ancho, alto, alto, alto);
        g2.setFont(ETIQUETA);
        FontMetrics fm = g2.getFontMetrics();
        int texto = izquierdo ? x + ancho - fm.stringWidth(etiqueta) - 8 : x + 8;
        g2.setColor(Estilo.TEXTO);
        g2.drawString(etiqueta, texto, y + (alto + fm.getAscent()) / 2 - 2);
    }

    private void caja(Graphics2D g2, int x, int y, int ancho, int alto, Color borde) {
        g2.setColor(CAJA);
        g2.fillRoundRect(x, y, ancho, alto, 22, 22);
        g2.setColor(borde);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, ancho, alto, 22, 22);
    }

    private void centrado(Graphics2D g2, String texto, Font fuente, Color color, int x, int ancho, int y) {
        g2.setFont(fuente);
        int anchoTexto = g2.getFontMetrics().stringWidth(texto);
        g2.setColor(color);
        g2.drawString(texto, x + (ancho - anchoTexto) / 2, y);
    }
}