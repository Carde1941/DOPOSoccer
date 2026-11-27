package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
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
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import domain.DOPOSoccer;

public class DialogoControles extends JDialog {

    public DialogoControles(Component origen, DOPOSoccer juego) {
        super(SwingUtilities.getWindowAncestor(origen), "Controles", Dialog.ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        PanelNeon panel = new PanelNeon(Estilo.AMARILLO);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 10, 4, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(texto("CONTROLES", Estilo.BOTON.deriveFont(24f), Estilo.AMARILLO), c);

        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.NORTH;
        List<String> personajes = juego.getPersonajes();
        panel.add(columna("Jugador 1", Estilo.CIAN, personajes.get(0), true, Controles.izquierdo()), c);
        c.gridx = 1;
        panel.add(columna("Jugador 2", Estilo.ROSADO, personajes.get(1 % personajes.size()), false, Controles.derecho()), c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        panel.add(texto("DURANTE EL PARTIDO", Estilo.NORMAL.deriveFont(12f), Estilo.CIAN), c);
        c.gridy = 3;
        String generales = Controles.nombre(Controles.PAUSA) + ": pausa   ·   "
            + Controles.nombre(Controles.GUARDAR) + ": guardar (en pausa)   ·   "
            + Controles.nombre(Controles.TERMINAR) + ": terminar";
        panel.add(texto(generales, Estilo.NORMAL.deriveFont(14f), Estilo.TEXTO), c);

        c.gridy = 4;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(12, 10, 2, 10);
        BotonJuego cerrar = new BotonJuego("Entendido", Estilo.AMARILLO);
        cerrar.setFont(Estilo.BOTON.deriveFont(14f));
        cerrar.setPreferredSize(new Dimension(150, 36));
        cerrar.addActionListener(e -> dispose());
        panel.add(cerrar, c);

        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(origen);
    }

    private JPanel columna(String titulo, Color color, String personaje, boolean mirandoDerecha, Controles controles) {
        JPanel columna = new JPanel(new GridBagLayout());
        columna.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(2, 6, 2, 6);
        columna.add(texto(titulo.toUpperCase(), Estilo.BOTON, color), c);
        c.gridy++;
        columna.add(new Figura(personaje, mirandoDerecha), c);
        c.gridwidth = 1;
        for (Map.Entry<String, String> tecla : controles.getTeclas().entrySet()) {
            c.gridy++;
            c.gridx = 0;
            c.anchor = GridBagConstraints.EAST;
            columna.add(texto(tecla.getKey(), Estilo.NORMAL.deriveFont(13f), Estilo.TEXTO), c);
            c.gridx = 1;
            c.anchor = GridBagConstraints.WEST;
            columna.add(texto(tecla.getValue().toUpperCase(), Estilo.NORMAL, color), c);
        }
        return columna;
    }

    private static JLabel texto(String contenido, Font fuente, Color color) {
        JLabel l = new JLabel(contenido, SwingConstants.CENTER);
        l.setFont(fuente);
        l.setForeground(color);
        return l;
    }

    private static class Figura extends JComponent {
        private String personaje;
        private boolean mirandoDerecha;

        Figura(String personaje, boolean mirandoDerecha) {
            this.personaje = personaje;
            this.mirandoDerecha = mirandoDerecha;
            setPreferredSize(new Dimension(130, 140));
        }

        @Override
        protected void paintComponent(Graphics g) {
            BufferedImage imagen = Apariencia.cuerpo(personaje);
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
