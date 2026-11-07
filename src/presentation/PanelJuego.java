package presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import domain.*;

public class PanelJuego extends JPanel {
    private static final int MILISEGUNDOS_POR_CUADRO = 16;
    private static final double MAXIMO_SEGUNDOS_POR_CUADRO = 0.05;

    private DOPOSoccerGUI ventana;
    private Partido partido;
    private Timer reloj;
    private long ultimoInstante;

    public PanelJuego(DOPOSoccerGUI ventana, Partido partido, int ancho, int alto) {
        this.ventana = ventana;
        this.partido = partido;
        setPreferredSize(new Dimension(ancho, alto));
        setFocusable(true);
        addKeyListener(new ControlTeclado());
        reloj = new Timer(MILISEGUNDOS_POR_CUADRO, e -> ciclo());
    }

    public void iniciar() {
        ultimoInstante = System.nanoTime();
        reloj.start();
        requestFocusInWindow();
    }

    private void ciclo() {
        long ahora = System.nanoTime();
        double segundos = Math.min((ahora - ultimoInstante) / 1_000_000_000.0, MAXIMO_SEGUNDOS_POR_CUADRO);
        ultimoInstante = ahora;
        // TE TOCA (1): actualiza el partido con esos segundos, redibuja con repaint(),
        // y si el partido ya terminó, llama a terminarJuego()
    }

    private void terminarJuego() {
        reloj.stop();
        // TE TOCA (2): arma un mensaje con quién ganó (o empate) y los puntos de cada jugador,
        // muéstralo con JOptionPane.showMessageDialog(...) y vuelve con ventana.mostrarConfiguracion()
    }

    private void cambiarPausa() {
        // TE TOCA (3): si el partido está pausado, reanúdalo; si no, páusalo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Cancha cancha = partido.getCancha();
        g2.scale(getWidth() / cancha.getAncho(), getHeight() / cancha.getAlto());
        dibujarCancha(g2, cancha);
        dibujarJugador(g2, partido.getJugadorIzquierdo(), new Color(200, 40, 40), 1);
        dibujarJugador(g2, partido.getJugadorDerecho(), new Color(40, 80, 200), -1);
        dibujarBalon(g2, partido.getBalon());
        dibujarMarcador(g2, cancha);
        g2.dispose();
    }

    private void dibujarCancha(Graphics2D g2, Cancha cancha) {
        int ancho = (int) cancha.getAncho();
        int suelo = (int) cancha.getSuelo();
        int arcoAncho = (int) cancha.getArcoAncho();
        int arcoAlto = (int) cancha.getArcoAlto();
        g2.setColor(new Color(135, 200, 235));
        g2.fillRect(0, 0, ancho, suelo);
        g2.setColor(new Color(60, 160, 60));
        g2.fillRect(0, suelo, ancho, (int) cancha.getAlto() - suelo);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(0, suelo - arcoAlto, arcoAncho, arcoAlto);
        g2.drawRect(ancho - arcoAncho, suelo - arcoAlto, arcoAncho, arcoAlto);
    }

    private void dibujarJugador(Graphics2D g2, Jugador jugador, Color color, int sentido) {
        int x = (int) jugador.getX();
        int y = (int) jugador.getY();
        int ancho = (int) jugador.getAncho();
        int alto = (int) jugador.getAlto();
        Color relleno = jugador.estaAturdido() ? Color.GRAY : color;
        g2.setColor(relleno);
        g2.fillOval(x, y, ancho, ancho);
        g2.fillRect(x + 15, y + ancho, ancho - 30, alto - ancho);
        if (jugador.estaPateando()) {
            int pieX = sentido == 1 ? x + ancho : x - 40;
            g2.fillRect(pieX, y + alto - 20, 40, 20);
        }
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, ancho, ancho);
    }

    private void dibujarBalon(Graphics2D g2, Balon balon) {
        int radio = (int) balon.getRadio();
        int x = (int) balon.getX() - radio;
        int y = (int) balon.getY() - radio;
        g2.setColor(Color.WHITE);
        g2.fillOval(x, y, radio * 2, radio * 2);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, radio * 2, radio * 2);
    }

    private void dibujarMarcador(Graphics2D g2, Cancha cancha) {
        Jugador izquierdo = partido.getJugadorIzquierdo();
        Jugador derecho = partido.getJugadorDerecho();
        int centro = (int) cancha.getAncho() / 2;
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 48));
        g2.drawString(izquierdo.getGoles() + " - " + derecho.getGoles(), centro - 55, 60);
        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        if (partido.getTipoPartido() instanceof PorTiempo) {
            int segundos = (int) Math.ceil(((PorTiempo) partido.getTipoPartido()).getSegundosRestantes());
            g2.drawString(String.format("%d:%02d", segundos / 60, segundos % 60), centro - 35, 100);
        }
        dibujarBarras(g2, izquierdo, 30);
        dibujarBarras(g2, derecho, (int) cancha.getAncho() - 330);
        if (partido.estaPausado()) {
            g2.setFont(new Font("SansSerif", Font.BOLD, 72));
            g2.drawString("PAUSA", centro - 120, (int) cancha.getAlto() / 2);
        }
    }

    private void dibujarBarras(Graphics2D g2, Jugador jugador, int x) {
        double vida = jugador.getVida() / jugador.getEstadisticas().vidaMaxima();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, 20, 300, 20);
        g2.fillRect(x, 50, 300, 12);
        g2.setColor(Color.RED);
        g2.fillRect(x, 20, (int) (300 * vida), 20);
        g2.setColor(Color.ORANGE);
        g2.fillRect(x, 50, (int) (300 * jugador.getBarra().getProgreso()), 12);
    }

    private class ControlTeclado extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            Jugador izquierdo = partido.getJugadorIzquierdo();
            Jugador derecho = partido.getJugadorDerecho();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> izquierdo.moverIzquierda();
                // TE TOCA (4): D mueve a la derecha, W salta, S patea
                //              flechas LEFT, RIGHT, UP, DOWN hacen lo mismo con el derecho
                //              P llama a cambiarPausa() y ESCAPE termina el partido
                default -> { }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A, KeyEvent.VK_D -> partido.getJugadorIzquierdo().detenerse();
                // TE TOCA (5): al soltar LEFT o RIGHT se detiene el derecho
                default -> { }
            }
        }
    }
}