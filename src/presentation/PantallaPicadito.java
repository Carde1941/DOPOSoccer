package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import domain.DOPOSoccerException;

/** "Arma el picadito": cada jugador elige país y en el centro se definen las reglas. */
public class PantallaPicadito extends PanelConFondo {
    private static final String JUGADOR_VS_JUGADOR = "Jugador vs Jugador";
    private static final String JUGADOR_VS_MAQUINA = "Jugador vs Máquina";
    private static final List<String> MODALIDADES = List.of(JUGADOR_VS_JUGADOR, JUGADOR_VS_MAQUINA);
    private static final List<Cantidad> MINUTOS = List.of(new Cantidad(1, "minuto"), new Cantidad(2, "minutos"),
        new Cantidad(3, "minutos"), new Cantidad(5, "minutos"));
    private static final List<Cantidad> GOLES = List.of(new Cantidad(1, "gol"), new Cantidad(3, "goles"),
        new Cantidad(5, "goles"), new Cantidad(7, "goles"));
    private static final List<Cantidad> POR_DEFECTO = List.of(new Cantidad(1, ""), new Cantidad(3, ""),
        new Cantidad(5, ""), new Cantidad(7, ""));
    private static final Map<String, List<Cantidad>> CANTIDADES = Map.of("Por tiempo", MINUTOS, "Por goles", GOLES);
    private static final List<Resolucion> RESOLUCIONES = List.of(new Resolucion(800, 450),
        new Resolucion(1024, 576), new Resolucion(1280, 720));

    private DOPOSoccerGUI ventana;
    private TarjetaJugador jugador1;
    private TarjetaJugador jugador2;
    private Selector<String> modalidad;
    private Selector<String> maquina;
    private JLabel etiquetaMaquina;
    private Selector<String> tipo;
    private Selector<Cantidad> cantidad;
    private Selector<Resolucion> resolucion;
    private BotonJuego aLaCancha;
    private JLabel aviso;

    public PantallaPicadito(DOPOSoccerGUI ventana) {
        super("DSFondo.png", true);
        this.ventana = ventana;
        setLayout(new BorderLayout(0, 6));
        setBorder(BorderFactory.createEmptyBorder(10, 16, 16, 16));
        add(new Titulo("Arma el picadito"), BorderLayout.NORTH);

        JPanel columnas = new JPanel(new GridBagLayout());
        columnas.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.insets = new Insets(0, 6, 0, 6);
        jugador1 = new TarjetaJugador("Jugador 1", Pais.NIGERIA, Estilo.CIAN, true);
        jugador2 = new TarjetaJugador("Jugador 2", Pais.CAMERUN, Estilo.ROSADO, false);
        c.weightx = 1;
        columnas.add(jugador1, c);
        c.weightx = 1.25;
        columnas.add(prepararReglas(), c);
        c.weightx = 1;
        columnas.add(jugador2, c);
        add(columnas, BorderLayout.CENTER);
    }

    private JPanel prepararReglas() {
        PanelNeon reglas = new PanelNeon(Estilo.AMARILLO);
        reglas.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(2, 0, 2, 0);

        modalidad = new Selector<>(MODALIDADES);
        maquina = new Selector<>(ventana.getJuego().getMaquinas());
        tipo = new Selector<>(ventana.getJuego().getTiposPartido());
        cantidad = new Selector<>(cantidadesDe(tipo.getValor()));
        resolucion = new Selector<>(RESOLUCIONES);
        cantidad.seleccionar(MINUTOS.get(1));
        resolucion.seleccionar(RESOLUCIONES.get(2));
        modalidad.alCambiar(this::revisarModalidad);
        tipo.alCambiar(() -> cantidad.setOpciones(cantidadesDe(tipo.getValor())));

        agregar(reglas, c, "Modalidad", modalidad);
        etiquetaMaquina = agregar(reglas, c, "Máquina", maquina);
        agregar(reglas, c, "Tipo de partido", tipo);
        agregar(reglas, c, "¿Cuánto dura?", cantidad);
        agregar(reglas, c, "Resolución", resolucion);

        aviso = new JLabel(" ", SwingConstants.CENTER);
        aviso.setFont(Estilo.NORMAL.deriveFont(12f));
        aviso.setForeground(new Color(255, 140, 140));
        reglas.add(aviso, c);

        aLaCancha = new BotonJuego("¡A la cancha!", Estilo.AMARILLO);
        aLaCancha.setPreferredSize(new Dimension(240, 50));
        aLaCancha.addActionListener(e -> jugar());
        c.fill = GridBagConstraints.NONE;
        reglas.add(aLaCancha, c);

        BotonJuego volver = new BotonJuego("Volver", Estilo.CIAN);
        volver.setPreferredSize(new Dimension(130, 34));
        volver.setFont(Estilo.BOTON.deriveFont(14f));
        volver.addActionListener(e -> ventana.mostrar(DOPOSoccerGUI.MENU));
        reglas.add(volver, c);
        revisarModalidad();
        return reglas;
    }

    private JLabel agregar(JPanel panel, GridBagConstraints c, String nombre, Selector<?> selector) {
        JLabel etiqueta = new JLabel(nombre.toUpperCase(), SwingConstants.CENTER);
        etiqueta.setFont(Estilo.NORMAL.deriveFont(12f));
        etiqueta.setForeground(Estilo.CIAN);
        panel.add(etiqueta, c);
        panel.add(selector, c);
        return etiqueta;
    }

    private void revisarModalidad() {
        boolean contraMaquina = esContraMaquina();
        etiquetaMaquina.setVisible(contraMaquina);
        maquina.setVisible(contraMaquina);
        revalidate();
        repaint();
    }

    private boolean esContraMaquina() {
        return modalidad.getValor().equals(JUGADOR_VS_MAQUINA);
    }

    private List<Cantidad> cantidadesDe(String tipoPartido) {
        return CANTIDADES.getOrDefault(tipoPartido, POR_DEFECTO);
    }

    private void jugar() {
        ConfiguracionPartido configuracion = ventana.getConfiguracion();
        configuracion.conPaises(jugador1.getPais(), jugador2.getPais()).conResolucion(resolucion.getValor().ancho(), resolucion.getValor().alto());
        configuracion.conTipo(tipo.getValor(), cantidad.getValor().valor());
        if (esContraMaquina()) {
            configuracion.conMaquina(maquina.getValor());
        } else {
            configuracion.sinMaquina();
        }
        try {
            ventana.iniciarPartido();
        } catch (DOPOSoccerException e) {
            aviso.setText(e.getMessage());
            Log.record(e);
        }
    }

    /** Una cantidad con su unidad, por ejemplo "2 minutos". */
    private record Cantidad(int valor, String unidad) {
        @Override
        public String toString() {
            return (valor + " " + unidad).trim();
        }
    }

    private record Resolucion(int ancho, int alto) {
        @Override
        public String toString() {
            return ancho + " x " + alto;
        }
    }
}