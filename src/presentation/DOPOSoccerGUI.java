package presentation;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.DOPOSoccer;

public class DOPOSoccerGUI extends JFrame {
    public static final String MENU = "menu";
    public static final String CONFIGURACION = "configuracion";

    private DOPOSoccer juego = new DOPOSoccer();
    private CardLayout tarjetas = new CardLayout();
    private JPanel pantallas = new JPanel(tarjetas);

    public DOPOSoccerGUI() {
        super("DOPOSoccer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pantallas.setPreferredSize(new Dimension(800, 520));
        pantallas.add(new PantallaMenu(this), MENU);
        pantallas.add(new PanelConfiguracion(this), CONFIGURACION);
        mostrar(MENU);
    }

    public void mostrar(String pantalla) {
        setContentPane(pantallas);
        tarjetas.show(pantallas, pantalla);
        pack();
        setLocationRelativeTo(null);
    }

    public void iniciarJuego(int ancho, int alto) {
        PanelJuego panel = new PanelJuego(this, juego, ancho, alto);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        panel.iniciar();
    }

    public void salir() {
        dispose();
    }

    public DOPOSoccer getJuego() {
        return juego;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DOPOSoccerGUI().setVisible(true));
    }
}