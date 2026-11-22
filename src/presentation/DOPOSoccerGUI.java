package presentation;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import domain.DOPOSoccer;
import domain.DOPOSoccerException;

public class DOPOSoccerGUI extends JFrame {
    public static final String MENU = "menu";
    public static final String PICADITO = "picadito";

    private DOPOSoccer juego = new DOPOSoccer();
    private CardLayout tarjetas = new CardLayout();
    private JPanel pantallas = new JPanel(tarjetas);
    private ConfiguracionPartido configuracion = new ConfiguracionPartido();

    public DOPOSoccerGUI() {
        super("DOPOSoccer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pantallas.setPreferredSize(new Dimension(960, 540));
        pantallas.add(new PantallaMenu(this), MENU);
        pantallas.add(new PantallaPicadito(this), PICADITO);
        mostrar(MENU);
    }

    public void mostrar(String pantalla) {
        setContentPane(pantallas);
        tarjetas.show(pantallas, pantalla);
        pack();
        setLocationRelativeTo(null);
    }

    public void iniciarPartido() throws DOPOSoccerException {
    configuracion.construir(juego);
    mostrarJuego();
    }

    public void abrirPartido(){
        JFileChooser selector = new JFileChooser();
        if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION ){
            return;
        }
        try {
            juego.abrir(selector.getSelectedFile());
            mostrarJuego();
        } catch (DOPOSoccerException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Abrir partida", JOptionPane.ERROR_MESSAGE);
            Log.record(e);
        }
    }


    private void mostrarJuego(){
        PanelJuego panel = new PanelJuego(this, juego, configuracion);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        panel.iniciar();
    }

    public ConfiguracionPartido getConfiguracion() {
    return configuracion;
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