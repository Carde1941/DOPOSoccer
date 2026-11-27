package presentation;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import domain.DOPOSoccer;
import domain.DOPOSoccerException;

/**
 * La ventana del juego. Cambia entre el menú, la pantalla de configuración y el partido, y guarda el
 * progreso de los jugadores.
 */
public class DOPOSoccerGUI extends JFrame {
    /** El nombre de la pantalla del menú. */
    public static final String MENU = "menu";
    /** El nombre de la pantalla "Arma el picadito". */
    public static final String PICADITO = "picadito";
    private static final File PROGRESO = new File(System.getProperty("user.home"), "DOPOSoccer.progreso");

    private DOPOSoccer juego = new DOPOSoccer();
    private CardLayout tarjetas = new CardLayout();
    private JPanel pantallas = new JPanel(tarjetas);
    private ConfiguracionPartido configuracion = new ConfiguracionPartido();

    /** Crea la ventana, carga el progreso guardado y muestra el menú. */
    public DOPOSoccerGUI() {
        super("DOPOSoccer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        abrirProgreso();
        pantallas.setPreferredSize(new Dimension(960, 540));
        pantallas.add(new PantallaMenu(this), MENU);
        pantallas.add(new PantallaPicadito(this), PICADITO);
        mostrar(MENU);
    }

    /**
     * Muestra una de las pantallas.
     *
     * @param pantalla MENU o PICADITO
     */
    public void mostrar(String pantalla) {
        setContentPane(pantallas);
        tarjetas.show(pantallas, pantalla);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Crea el partido con lo que se escogió y lo empieza.
     *
     * @throws DOPOSoccerException si algo de lo escogido no existe
     */
    public void iniciarPartido() throws DOPOSoccerException {
    configuracion.construir(juego);
    mostrarJuego();
    }

    /** Pide un archivo y abre la partida guardada. Si falla, muestra el error. */
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

    /** Guarda los puntos y las mejoras de los jugadores. Si falla, muestra el error. */
    public void guardarProgreso() {
        try {
            juego.guardarProgreso(PROGRESO);
        } catch (DOPOSoccerException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Progreso", JOptionPane.ERROR_MESSAGE);
            Log.record(e);
        }
    }

    private void abrirProgreso() {
        if (!PROGRESO.exists()) {
            return;
        }
        try {
            juego.abrirProgreso(PROGRESO);
        } catch (DOPOSoccerException e) {
            Log.record(e);
        }
    }

    /**
     * Lo que se escogió en la pantalla de configuración.
     *
     * @return la configuración
     */
    public ConfiguracionPartido getConfiguracion() {
    return configuracion;
    }

    /** Cierra el juego. */
    public void salir() {
        dispose();
    }

    /**
     * El juego.
     *
     * @return el juego
     */
    public DOPOSoccer getJuego() {
        return juego;
    }

    /**
     * Abre el juego.
     *
     * @param args no se usan
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DOPOSoccerGUI().setVisible(true));
    }
}