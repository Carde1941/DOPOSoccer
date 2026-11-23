package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import domain.DOPOSoccer;
import domain.DOPOSoccerException;

public class DialogoMejoras extends JDialog {
    private DOPOSoccerGUI ventana;
    private int lado;
    private JLabel puntos;
    private JLabel aviso;
    private Map<String, JLabel> niveles = new LinkedHashMap<>();
    private Map<String, BotonJuego> botones = new LinkedHashMap<>();

    public DialogoMejoras(Component origen, DOPOSoccerGUI ventana, int lado, String titulo, Color color) {
        super(SwingUtilities.getWindowAncestor(origen), "Mejoras", Dialog.ModalityType.APPLICATION_MODAL);
        this.ventana = ventana;
        this.lado = lado;
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        PanelNeon panel = new PanelNeon(color);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 8, 4, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        panel.add(texto("MEJORAS · " + titulo.toUpperCase(), Estilo.BOTON.deriveFont(22f), Estilo.AMARILLO), c);
        c.gridy++;
        puntos = texto("", Estilo.NORMAL, Estilo.TEXTO);
        panel.add(puntos, c);

        c.gridwidth = 1;
        for (String estadistica : ventana.getJuego().getEstadisticas()) {
            c.gridy++;
            c.gridx = 0;
            panel.add(texto(estadistica.toUpperCase(), Estilo.NORMAL, Estilo.CIAN), c);
            c.gridx = 1;
            JLabel nivel = texto("", Estilo.NORMAL, Estilo.TEXTO);
            niveles.put(estadistica, nivel);
            panel.add(nivel, c);
            c.gridx = 2;
            BotonJuego mejorar = new BotonJuego("", color);
            mejorar.setFont(Estilo.BOTON.deriveFont(13f));
            mejorar.setPreferredSize(new Dimension(170, 34));
            mejorar.addActionListener(e -> mejorar(estadistica));
            botones.put(estadistica, mejorar);
            panel.add(mejorar, c);
        }

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 3;
        aviso = texto(" ", Estilo.NORMAL.deriveFont(12f), new Color(255, 140, 140));
        panel.add(aviso, c);
        c.gridy++;
        c.fill = GridBagConstraints.NONE;
        BotonJuego listo = new BotonJuego("Listo", color);
        listo.setFont(Estilo.BOTON.deriveFont(14f));
        listo.setPreferredSize(new Dimension(150, 36));
        listo.addActionListener(e -> dispose());
        panel.add(listo, c);

        getContentPane().add(panel, BorderLayout.CENTER);
        actualizar();
        pack();
        setLocationRelativeTo(origen);
    }

    private void mejorar(String estadistica) {
        try {
            ventana.getJuego().mejorar(lado, estadistica);
            ventana.guardarProgreso();
            aviso.setText(" ");
        } catch (DOPOSoccerException e) {
            aviso.setText(e.getMessage());
            Log.record(e);
        }
        actualizar();
    }

    private void actualizar() {
        DOPOSoccer juego = ventana.getJuego();
        puntos.setText("Puntos disponibles: " + juego.getPuntosAcumulados(lado));
        try {
            for (String estadistica : niveles.keySet()) {
                niveles.get(estadistica).setText("Nivel " + juego.getNivel(lado, estadistica) + " / 10");
                BotonJuego boton = botones.get(estadistica);
                if (juego.estaAlMaximo(lado, estadistica)) {
                    boton.setText("MÁXIMO");
                    boton.setEnabled(false);
                } else {
                    boton.setText("+1 · " + juego.getPrecioMejora(lado, estadistica) + " PTS");
                }
            }
        } catch (DOPOSoccerException e) {
            aviso.setText(e.getMessage());
            Log.record(e);
        }
    }

    private static JLabel texto(String contenido, Font fuente, Color color) {
        JLabel l = new JLabel(contenido, SwingConstants.CENTER);
        l.setFont(fuente);
        l.setForeground(color);
        return l;
    }
}
