package presentation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

import domain.DOPOSoccer;

/** Hoja: el personaje de un jugador (cabeza y guayo), parado sobre el suelo de su caja en el dominio. */
public class VistaJugador implements Vista {
    private static final double ESCALA = 1.3;
    private int lado;
    private Pais pais;

    public VistaJugador(int lado, Pais pais) {
        this.lado = lado;
        this.pais = pais;
    }

    @Override
    public void dibujar(Graphics2D g2, DOPOSoccer juego) {
        int x = (int) juego.getXJugador(lado);
        int y = (int) juego.getYJugador(lado);
        int ancho = (int) juego.getAnchoJugador(lado);
        int alto = (int) juego.getAltoJugador(lado);
        int pies = y + alto;
        int centro = x + ancho / 2;

        g2.setColor(new Color(0, 0, 0, 70));
        g2.fillOval(centro - ancho / 2, (int) juego.getSuelo() - 6, ancho, 12);

        Composite antes = g2.getComposite();
        if (juego.estaAturdido(lado)) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        boolean mirandoDerecha = lado == DOPOSoccer.IZQUIERDO;
        FiguraPersonaje.dibujar(g2, pais, centro, pies, (int) (alto * ESCALA), mirandoDerecha, juego.estaPateando(lado));
        g2.setComposite(antes);
    }
}