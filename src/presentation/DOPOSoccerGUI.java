package presentation;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.DOPOSoccer;

public class DOPOSoccerGUI extends JFrame {
    private DOPOSoccer juego = new DOPOSoccer();

    public DOPOSoccerGUI() {
        super("DOPOSoccer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mostrarConfiguracion();
    }

    public void mostrarConfiguracion() {
        setContentPane(new PanelConfiguracion(this));
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

    public DOPOSoccer getJuego(){
        return juego;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DOPOSoccerGUI().setVisible(true));
    }
}