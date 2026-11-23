package presentation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFileChooser;

import domain.DOPOSoccer;
import domain.DOPOSoccerException;

public class PanelJuego extends JPanel {
    private static final int MILISEGUNDOS_POR_CUADRO = 16;
    private static final double MAXIMO_SEGUNDOS_POR_CUADRO = 0.05;
    private static final double PASO_FISICA = 0.01;

    private DOPOSoccerGUI ventana;
    private DOPOSoccer juego;
    private List<Controles> controles = new ArrayList<>();
    private Timer reloj;
    private long ultimoInstante;
    private Vista escena;


    public PanelJuego(DOPOSoccerGUI ventana, DOPOSoccer juego, ConfiguracionPartido configuracion) {
    this.ventana = ventana;
    this.juego = juego;
    setPreferredSize(new Dimension(configuracion.getAncho(), configuracion.getAlto()));
    escena = new VistaCompuesta()
        .agregar(new VistaCancha())
        .agregar(new VistaJugador(DOPOSoccer.IZQUIERDO))
        .agregar(new VistaJugador(DOPOSoccer.DERECHO))
        .agregar(new VistaBalon())
        .agregar(new VistaMarcador())
        .agregar(new VistaPausa());
    setFocusable(true);
        agregarControles(DOPOSoccer.IZQUIERDO, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_SPACE, KeyEvent.VK_S);
        agregarControles(DOPOSoccer.DERECHO, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_K, KeyEvent.VK_DOWN);

        addKeyListener(new ControlTeclado());
        reloj = new Timer(MILISEGUNDOS_POR_CUADRO, e -> ciclo());
    }

    private void agregarControles(int jugador, int izquierda, int derecha, int saltar, int patear, int poder) {
        if (juego.usaTeclado(jugador)) {
            controles.add(new Controles(jugador, izquierda, derecha, saltar, patear, poder));
        }
    }

    private void soltarTodo() {
        for (Controles control : controles) {
            control.soltarTodo(juego);
        }
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
        ventana.guardarProgreso();
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
        mensaje += "\nPuntos jugador izquierdo: " + juego.getPuntos(DOPOSoccer.IZQUIERDO)
            + " (acumulados: " + juego.getPuntosAcumulados(DOPOSoccer.IZQUIERDO) + ")";
        mensaje += "\nPuntos jugador derecho: " + juego.getPuntos(DOPOSoccer.DERECHO)
            + " (acumulados: " + juego.getPuntosAcumulados(DOPOSoccer.DERECHO) + ")";
        JOptionPane.showMessageDialog(this, mensaje,"Fin del partido", JOptionPane.INFORMATION_MESSAGE);
        ventana.mostrar(DOPOSoccerGUI.MENU);
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
        soltarTodo();
        String[] opciones = {"Sí", "No"};
        int respuestas = JOptionPane.showOptionDialog(this, "¿Terminar el partido?", "Terminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
        if (respuestas == 0){
            juego.terminar();
        } else if (!estabaPausado){
            juego.reanudar();
        }
    }

    private void guardar(){
        boolean estabaPausado = juego.estaPausado();
        juego.pausar();
        soltarTodo();
        JFileChooser selector = new JFileChooser();
        if (selector.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                try {
                    juego.guardar(selector.getSelectedFile());
                } catch (DOPOSoccerException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Guardar partida",JOptionPane.ERROR_MESSAGE);
                    Log.record(e);
                }
        }
        if (!estabaPausado){
            juego.reanudar();
        }
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.scale(getWidth() / juego.getAnchoCancha(), getHeight() / juego.getAltoCancha());
        escena.dibujar(g2, juego);
        g2.dispose();
    }

    private class ControlTeclado extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int tecla = e.getKeyCode();
            if (tecla == KeyEvent.VK_P){
                cambiarPausa();
            } else if (tecla == KeyEvent.VK_ESCAPE){
                confirmarTerminar();
            } 
            else if (tecla == KeyEvent.VK_G && juego.estaPausado()) {
                guardar();
            }
            else{
                for (Controles control : controles) {
                    control.presionar(tecla, juego);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            for (Controles control : controles) {
                control.soltar(e.getKeyCode(), juego);
            }
            }
        }
}