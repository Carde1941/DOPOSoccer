package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import domain.DOPOSoccer;
import domain.DOPOSoccerException;

/**
 * La columna de un jugador en "Arma el picadito": su nombre, la imagen del personaje, el selector, su
 * poder y el botón de mejoras.
 */
public class TarjetaJugador extends PanelNeon {
    private DOPOSoccer juego;
    private Selector<String> personaje;
    private Figura figura;
    private JLabel poder;

    /**
     * Crea la tarjeta.
     *
     * @param titulo el nombre del jugador, por ejemplo "Jugador 1"
     * @param inicial la posición en la lista del personaje que sale escogido al principio
     * @param color el color del jugador en la pantalla
     * @param mirandoDerecha true si el personaje mira hacia la derecha
     * @param ventana la ventana principal del juego
     * @param lado el lado del jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     */
    public TarjetaJugador(String titulo, int inicial, Color color, boolean mirandoDerecha, DOPOSoccerGUI ventana, int lado) {
        super(color);
        juego = ventana.getJuego();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3, 0, 3, 0);

        add(etiqueta(titulo, Estilo.BOTON, Estilo.TEXTO), c);
        figura = new Figura(mirandoDerecha);
        add(figura, c);

        List<String> personajes = juego.getPersonajes();
        personaje = new Selector<>(personajes);
        personaje.seleccionar(personajes.get(inicial % personajes.size()));
        personaje.alCambiar(this::mostrarPersonaje);
        add(personaje, c);

        poder = etiqueta("", Estilo.NORMAL, Estilo.CIAN);
        add(poder, c);

        BotonJuego info = new BotonJuego("Info del poder", Estilo.AMARILLO);
        info.setFont(Estilo.BOTON.deriveFont(13f));
        info.setPreferredSize(new Dimension(170, 34));
        info.addActionListener(e -> mostrarPoder(color));
        c.fill = GridBagConstraints.NONE;
        add(info, c);

        BotonJuego mejoras = new BotonJuego("Mejorar estadísticas", color);
        mejoras.setFont(Estilo.BOTON.deriveFont(13f));
        mejoras.setPreferredSize(new Dimension(220, 34));
        mejoras.addActionListener(e -> new DialogoMejoras(this, ventana, lado, titulo, color).setVisible(true));
        add(mejoras, c);

        mostrarPersonaje();
    }

    /**
     * El personaje escogido.
     *
     * @return el nombre del personaje
     */
    public String getPersonaje() {
        return personaje.getValor();
    }

    private void mostrarPersonaje() {
        try {
            poder.setText("Poder: " + juego.getNombrePoder(getPersonaje()));
        } catch (DOPOSoccerException e) {
            Log.record(e);
        }
        figura.repaint();
    }

    private void mostrarPoder(Color color) {
        try {
            new DialogoPoder(this, juego, getPersonaje(), color).setVisible(true);
        } catch (DOPOSoccerException e) {
            Log.record(e);
        }
    }

    private static JLabel etiqueta(String texto, Font fuente, Color color) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(fuente);
        l.setForeground(color);
        return l;
    }

    /**
     * Dibuja al personaje elegido de cuerpo completo, centrado y sin deformarlo.
     * Las imágenes miran a la izquierda; el jugador 1 se dibuja en espejo para que mire al centro.
     */
    private class Figura extends JComponent {
        private boolean mirandoDerecha;

        Figura(boolean mirandoDerecha) {
            this.mirandoDerecha = mirandoDerecha;
            setPreferredSize(new Dimension(160, 170));
        }

        @Override
        protected void paintComponent(Graphics g) {
            BufferedImage imagen = Apariencia.cuerpo(getPersonaje());
            if (imagen == null) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int alto = getHeight();
            int ancho = alto * imagen.getWidth() / imagen.getHeight();
            int sentido = mirandoDerecha ? -1 : 1;
            int izquierda = getWidth() / 2 - sentido * ancho / 2;
            g2.drawImage(imagen, izquierda, 0, sentido * ancho, alto, null);
            g2.dispose();
        }
    }
}