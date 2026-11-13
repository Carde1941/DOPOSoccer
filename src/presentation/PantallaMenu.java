package presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;

public class PantallaMenu extends PanelConFondo {
    private DOPOSoccerGUI ventana;

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
        c.insets = new Insets(5, 0, 5, 0);

        JButton jugar = new BotonJuego("Jugar", Estilo.PRIMARIO);
        jugar.addActionListener(e -> ventana.mostrar(DOPOSoccerGUI.CONFIGURACION));
        add(jugar, c);

        JButton abrir = new BotonJuego("Abrir Partida", Estilo.SECUNDARIO);
        abrir.setEnabled(false);
        abrir.setToolTipText("En Construcción");
        add(abrir,c);

        JButton mejorarJugadores = new BotonJuego("Mejorar Jugadores", Estilo.SECUNDARIO);
        mejorarJugadores.setEnabled(false);
        mejorarJugadores.setToolTipText("En Construcción");
        add(mejorarJugadores, c);

        JButton salir = new BotonJuego("Salir", Estilo.PELIGRO);
        salir.addActionListener(e -> ventana.salir());
        add(salir, c);
        c.weighty = 0.08;
        add(Box.createVerticalGlue(), c);
    }
}