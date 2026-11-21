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
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/** Columna de un jugador: nombre, personaje, selector de país y su poder. */
public class TarjetaJugador extends PanelNeon {
    private Selector<Pais> pais;
    private Figura figura;
    private JLabel poder;

    public TarjetaJugador(String titulo, Pais inicial, Color color) {
        super(color);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3, 0, 3, 0);

        add(etiqueta(titulo, Estilo.BOTON, Estilo.TEXTO), c);
        figura = new Figura();
        add(figura, c);

        pais = new Selector<>(Arrays.asList(Pais.values()));
        pais.seleccionar(inicial);
        pais.alCambiar(this::mostrarPais);
        add(pais, c);

        poder = etiqueta("", Estilo.NORMAL, Estilo.CIAN);
        add(poder, c);

        BotonJuego info = new BotonJuego("Info del poder", Estilo.AMARILLO);
        info.setFont(Estilo.BOTON.deriveFont(13f));
        info.setPreferredSize(new Dimension(170, 34));
        info.addActionListener(e -> new DialogoPoder(this, getPais(), color).setVisible(true));
        c.fill = GridBagConstraints.NONE;
        add(info, c);

        mostrarPais();
    }

    public Pais getPais() {
        return pais.getValor();
    }

    private void mostrarPais() {
        poder.setText("Poder: " + getPais().getPoder());
        figura.repaint();
    }

    private static JLabel etiqueta(String texto, Font fuente, Color color) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(fuente);
        l.setForeground(color);
        return l;
    }

    /** Dibuja la imagen del personaje elegido, centrada y sin deformarla. */
    private class Figura extends JComponent {
        Figura() {
            setPreferredSize(new Dimension(160, 170));
        }

        @Override
        protected void paintComponent(Graphics g) {
            BufferedImage imagen = Imagenes.get(getPais().getImagen());
            if (imagen == null) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int alto = getHeight();
            int ancho = alto * imagen.getWidth() / imagen.getHeight();
            g2.drawImage(imagen, (getWidth() - ancho) / 2, 0, ancho, alto, null);
            g2.dispose();
        }
    }
}