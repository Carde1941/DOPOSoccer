package presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.util.Map;
import java.util.Set;

import domain.DOPOSoccer;

/** Hoja: el personaje de un jugador con su aura, dormido si está noqueado y con el adorno de su efecto. */
public class VistaJugador implements Vista {
    private static final double ESCALA = 1.3;
    private static final Font LETRA_ZZZ = Fuentes.juego(26f);
    private static final Font LETRA_DUDA = Fuentes.juego(40f);
    private static final Set<String> EFECTOS_QUE_DUERMEN = Set.of("Inconsciente");
    private static final Map<String, AdornoEfecto> ADORNOS = Map.of(
        "Electrocutado", VistaJugador::chispas,
        "Confusión", VistaJugador::duda,
        "Empuje", VistaJugador::rafagas);
    private int lado;

    public VistaJugador(int lado) {
        this.lado = lado;
    }

    @Override
    public void dibujar(Graphics2D g2, DOPOSoccer juego) {
        String personaje = juego.getPersonaje(lado);
        int x = (int) juego.getXJugador(lado);
        int y = (int) juego.getYJugador(lado);
        int ancho = (int) juego.getAnchoJugador(lado);
        int alto = (int) juego.getAltoJugador(lado);
        int pies = y + alto;
        int centro = x + ancho / 2;
        double tiempo = System.nanoTime() / 1_000_000_000.0;

        g2.setColor(new Color(0, 0, 0, 70));
        g2.fillOval(centro - ancho / 2, (int) juego.getSuelo() - 6, ancho, 12);

        if (juego.tieneAura(lado)) {
            aura(g2, Apariencia.aura(personaje), centro, y + alto / 2, alto, tiempo);
        }
        boolean mirandoDerecha = lado == DOPOSoccer.IZQUIERDO;
        String efecto = juego.getEfecto(lado);
        if (juego.estaAturdido(lado) || EFECTOS_QUE_DUERMEN.contains(efecto)) {
            dormido(g2, personaje, centro, pies, alto, mirandoDerecha, tiempo);
        } else {
            FiguraPersonaje.dibujar(g2, personaje, centro, pies, (int) (alto * ESCALA), mirandoDerecha, juego.estaPateando(lado));
        }
        AdornoEfecto adorno = ADORNOS.get(efecto);
        if (adorno != null) {
            adorno.dibujar(g2, centro, y, ancho, alto, tiempo);
        }
    }

    /** Un resplandor que late alrededor del personaje, del color de su poder. */
    private void aura(Graphics2D g2, Color color, int centro, int medio, int alto, double tiempo) {
        float radio = (float) (alto * (0.95 + 0.08 * Math.sin(tiempo * 8)));
        Color fuerte = new Color(color.getRed(), color.getGreen(), color.getBlue(), 170);
        Color nada = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
        Graphics2D brillo = (Graphics2D) g2.create();
        brillo.setPaint(new RadialGradientPaint(centro, medio, radio, new float[] {0.35f, 1f}, new Color[] {fuerte, nada}));
        brillo.fillOval((int) (centro - radio), (int) (medio - radio), (int) (radio * 2), (int) (radio * 2));
        brillo.setColor(fuerte);
        brillo.setStroke(new BasicStroke(3));
        int anillo = (int) (radio * 0.7);
        brillo.drawOval(centro - anillo, medio - anillo, anillo * 2, anillo * 2);
        brillo.dispose();
    }

    /** Acostado de espaldas sobre el suelo, con las Z del sueño subiendo. */
    private void dormido(Graphics2D g2, String personaje, int centro, int pies, int alto, boolean mirandoDerecha, double tiempo) {
        int altoFigura = (int) (alto * ESCALA);
        int grosor = (int) (altoFigura * 0.42);
        Graphics2D acostado = (Graphics2D) g2.create();
        acostado.rotate(mirandoDerecha ? -Math.PI / 2 : Math.PI / 2, centro, pies - grosor);
        FiguraPersonaje.dibujar(acostado, personaje, centro, pies - grosor + altoFigura / 2, altoFigura, mirandoDerecha, false);
        acostado.dispose();

        int cabeza = centro + (mirandoDerecha ? -altoFigura / 3 : altoFigura / 3);
        g2.setFont(LETRA_ZZZ);
        for (int i = 0; i < 3; i++) {
            double fase = (tiempo * 0.8 + i / 3.0) % 1.0;
            int alfa = (int) (255 * (1 - fase));
            g2.setColor(new Color(255, 255, 255, alfa));
            int zx = cabeza + (int) (fase * 30) * (mirandoDerecha ? -1 : 1);
            int zy = pies - grosor * 2 - (int) (fase * 60);
            g2.drawString("Z", zx, zy);
        }
    }

    /** Electrocutado: rayos amarillos que saltan alrededor del cuerpo. */
    private static void chispas(Graphics2D g2, int centro, int arriba, int ancho, int alto, double tiempo) {
        Graphics2D rayo = (Graphics2D) g2.create();
        rayo.setColor(new Color(255, 235, 80));
        rayo.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int cuadro = (int) (tiempo * 12);
        int medio = arriba + alto - (int) (alto * ESCALA / 2);
        for (int i = 0; i < 5; i++) {
            double angulo = (cuadro * 1.7 + i * Math.PI * 2 / 5) % (Math.PI * 2);
            int x0 = centro + (int) (Math.cos(angulo) * ancho * 0.9);
            int y0 = medio + (int) (Math.sin(angulo) * alto * 0.55) - 14;
            int[] xs = {x0, x0 + 8, x0 - 4, x0 + 6};
            int[] ys = {y0, y0 + 10, y0 + 16, y0 + 28};
            rayo.drawPolyline(xs, ys, xs.length);
        }
        rayo.dispose();
    }

    /** Confusión: un signo de pregunta que se balancea sobre la cabeza. */
    private static void duda(Graphics2D g2, int centro, int arriba, int ancho, int alto, double tiempo) {
        g2.setFont(LETRA_DUDA);
        int balanceo = (int) (Math.sin(tiempo * 6) * 6);
        int x = centro - g2.getFontMetrics().stringWidth("?") / 2 + balanceo;
        int y = arriba + alto - (int) (alto * ESCALA) - 12;
        g2.setColor(Estilo.CONTORNO);
        g2.drawString("?", x + 3, y + 3);
        g2.setColor(new Color(195, 120, 255));
        g2.drawString("?", x, y);
    }

    /** Empuje: líneas de viento que muestran que va volando sin control. */
    private static void rafagas(Graphics2D g2, int centro, int arriba, int ancho, int alto, double tiempo) {
        Graphics2D viento = (Graphics2D) g2.create();
        viento.setColor(new Color(150, 200, 255, 200));
        viento.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int corrimiento = (int) (tiempo * 200) % 30;
        for (int i = 0; i < 3; i++) {
            int y = arriba + alto / 4 + i * alto / 4;
            int largo = 30 + i * 8;
            viento.drawLine(centro - ancho - corrimiento, y, centro - ancho - corrimiento + largo, y);
            viento.drawLine(centro + ancho + corrimiento - largo, y, centro + ancho + corrimiento, y);
        }
        viento.dispose();
    }
}
