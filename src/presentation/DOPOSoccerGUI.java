package presentation;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.Partido;

public class DOPOSoccerGUI extends JFrame {

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

    public void iniciarJuego(Partido partido, int ancho, int alto) {
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DOPOSoccerGUI().setVisible(true));
    }
}