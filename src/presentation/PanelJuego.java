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

import domain.DOPOSoccer;

public class PanelJuego extends JPanel {
    private static final int MILISEGUNDOS_POR_CUADRO = 16;
    private static final double MAXIMO_SEGUNDOS_POR_CUADRO = 0.05;
    private static final double PASO_FISICA = 0.01;

    private DOPOSoccerGUI ventana;
    private DOPOSoccer juego;
    private Controles controlesIzquierdo;
    private Controles controlesDerecho;
    private Timer reloj;
    private long ultimoInstante;


    public PanelJuego(DOPOSoccerGUI ventana, DOPOSoccer juego, int ancho, int alto) {
        this.ventana = ventana;
        this.juego = juego;
        setPreferredSize(new Dimension(ancho, alto));
        setFocusable(true);
        controlesIzquierdo = new Controles(DOPOSoccer.IZQUIERDO, KeyEvent.VK_A,KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_SPACE);
        controlesDerecho = new Controles(DOPOSoccer.DERECHO, KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_K);

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
        while (segundos > 0){
            double paso = Math.min(segundos, PASO_FISICA);
            juego.actualizar(paso);
            segundos -= paso;
        }
        repaint();
        if (juego.haTerminado()){
            terminarJuego();
        }
    }

    private void terminarJuego() {
        reloj.stop();
        int ganador = juego.getGanador();
        String mensaje;
        if (ganador == DOPOSoccer.EMPATE){
            mensaje = "Empate";
        } else if (ganador == DOPOSoccer.IZQUIERDO){
            mensaje = "Ganó el jugador izquierdo";
        } else {
            mensaje = "Ganó el jugador derecho";
        }
        mensaje += "\nMarcador: " + juego.getGoles(DOPOSoccer.IZQUIERDO) + "-" + juego.getGoles(DOPOSoccer.DERECHO);
        mensaje += "\nPuntos jugador izquierdo: " + juego.getPuntos(DOPOSoccer.IZQUIERDO);
        mensaje += "\nPuntos jugador derecho: " + juego.getPuntos(DOPOSoccer.DERECHO);
        JOptionPane.showMessageDialog(this, mensaje,"Fin del partido", JOptionPane.INFORMATION_MESSAGE);
        ventana.mostrarConfiguracion();
    }

    private void cambiarPausa() {
         if (juego.estaPausado()){
            juego.reanudar();
         } else{
            juego.pausar();
         }
    }

    private void confirmarTerminar(){
        boolean estabaPausado = juego.estaPausado();
        juego.pausar();
        controlesIzquierdo.soltarTodo(juego);
        controlesDerecho.soltarTodo(juego);
        String[] opciones = {"Sí", "No"};
        int respuestas = JOptionPane.showOptionDialog(this, "¿Terminar el partido?", "Terminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
        if (respuestas == 0){
            juego.terminar();
        } else if (!estabaPausado){
            juego.reanudar();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.scale(getWidth() / juego.getAnchoCancha(), getHeight() / juego.getAltoCancha());
        dibujarCancha(g2);
        dibujarJugador(g2, DOPOSoccer.IZQUIERDO, new Color(200, 40, 40), 1);
        dibujarJugador(g2, DOPOSoccer.DERECHO, new Color(40, 80, 200), -1);
        dibujarBalon(g2);
        dibujarMarcador(g2);
        g2.dispose();
    }

    private void dibujarCancha(Graphics2D g2) {
        int ancho = (int) juego.getAnchoCancha();
        int suelo = (int) juego.getSuelo();
        int arcoAncho = (int) juego.getAnchoArco();
        int arcoAlto = (int) juego.getAltoArco();
        g2.setColor(new Color(135, 200, 235));
        g2.fillRect(0, 0, ancho, suelo);
        g2.setColor(new Color(60, 160, 60));
        g2.fillRect(0, suelo, ancho, (int) juego.getAltoCancha() - suelo);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(0, suelo - arcoAlto, arcoAncho, arcoAlto);
        g2.drawRect(ancho - arcoAncho, suelo - arcoAlto, arcoAncho, arcoAlto);
    }

    private void dibujarJugador(Graphics2D g2, int jugador, Color color, int sentido) {
        int x = (int) juego.getXJugador(jugador);
        int y = (int) juego.getYJugador(jugador);
        int ancho = (int) juego.getAnchoJugador(jugador);
        int alto = (int) juego.getAltoJugador(jugador);
        Color relleno = juego.estaAturdido(jugador) ? Color.GRAY : color;
        g2.setColor(relleno);
        g2.fillOval(x, y, ancho, ancho);
        g2.fillRect(x + 15, y + ancho, ancho - 30, alto - ancho);
        if (juego.estaPateando(jugador)) {
            int pieX = sentido == 1 ? x + ancho : x - 40;
            g2.fillRect(pieX, y + alto - 20, 40, 20);
        }
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, ancho, ancho);
    }

    private void dibujarBalon(Graphics2D g2) {
        int radio = (int) juego.getRadioBalon();
        int x = (int) juego.getXBalon() - radio;
        int y = (int) juego.getYBalon() - radio;
        g2.setColor(Color.WHITE);
        g2.fillOval(x, y, radio * 2, radio * 2);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, radio * 2, radio * 2);
    }

    private void dibujarMarcador(Graphics2D g2) {
        int centro = (int) juego.getAnchoCancha() / 2;
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 48));
        g2.drawString(juego.getGoles(DOPOSoccer.IZQUIERDO) + " - " + juego.getGoles(DOPOSoccer.DERECHO), centro - 55, 60);
        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        String informacion = juego.getInformacion();
        int anchoTexto = g2.getFontMetrics().stringWidth(informacion);
        g2.drawString(informacion, centro - anchoTexto/2, 100);
        dibujarBarras(g2, DOPOSoccer.IZQUIERDO, 30);
        dibujarBarras(g2, DOPOSoccer.DERECHO, (int) juego.getAnchoCancha() - 330);
        if (juego.estaPausado()) {
            g2.setFont(new Font("SansSerif", Font.BOLD, 72));
            g2.drawString("PAUSA", centro - 120, (int) juego.getAltoCancha() / 2);
        }
    }

    private void dibujarBarras(Graphics2D g2, int jugador, int x) {
        double vida = juego.getVida(jugador) / juego.getVidaMaxima(jugador);
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, 20, 300, 20);
        g2.fillRect(x, 50, 300, 12);
        g2.setColor(Color.RED);
        g2.fillRect(x, 20, (int) (300 * vida), 20);
        g2.setColor(Color.ORANGE);
        g2.fillRect(x, 50, (int) (300 * juego.getProgresoPoder(jugador)), 12);
    }

    private class ControlTeclado extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int tecla = e.getKeyCode();
            if (tecla == KeyEvent.VK_P){
                cambiarPausa();
            } else if (tecla == KeyEvent.VK_ESCAPE){
                confirmarTerminar();
            } else{
                controlesIzquierdo.presionar(tecla, juego);
                controlesDerecho.presionar(tecla, juego);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            controlesIzquierdo.soltar(e.getKeyCode(), juego);  
            controlesDerecho.soltar(e.getKeyCode(), juego);
            }
        }
}