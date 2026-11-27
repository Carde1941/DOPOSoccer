package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/** El menú principal: jugar, abrir una partida, ver los controles o salir. */
public class PantallaMenu extends PanelConFondo {
    private DOPOSoccerGUI ventana;

    /**
     * Crea el menú.
     *
     * @param ventana la ventana principal del juego
     */
    public PantallaMenu(DOPOSoccerGUI ventana) {
        super("DSMenu.png", false);
        this.ventana = ventana;
        setLayout(new GridBagLayout());
        prepararElementos();
    }

    private void prepararElementos() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weighty = 1;
        add(Box.createVerticalGlue(), c);
        c.weighty = 0;
        c.insets = new Insets(4, 0, 4, 0);

        JButton jugar = new BotonJuego("¡Jugar!", Estilo.ROSADO);
        jugar.setPreferredSize(new Dimension(300, 58));
        jugar.addActionListener(e -> ventana.mostrar(DOPOSoccerGUI.PICADITO));
        add(jugar, c);

        JButton abrir = new BotonJuego("Abrir partida", Estilo.CIAN);
        abrir.setPreferredSize(new Dimension(260, 44));
        abrir.addActionListener(e -> ventana.abrirPartido());
        add(abrir, c);

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        fila.setOpaque(false);
        JButton controles = new BotonJuego("Controles", Estilo.AMARILLO);
        controles.setPreferredSize(new Dimension(125, 38));
        controles.addActionListener(e -> new DialogoControles(this, ventana.getJuego()).setVisible(true));
        JButton salir = new BotonJuego("Salir", Estilo.AMARILLO);
        salir.setPreferredSize(new Dimension(125, 38));
        salir.addActionListener(e -> ventana.salir());
        fila.add(controles);
        fila.add(salir);
        add(fila, c);

        c.weighty = 0.08;
        add(Box.createVerticalGlue(), c);
    }
}