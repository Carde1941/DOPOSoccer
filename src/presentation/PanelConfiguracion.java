package presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import domain.DOPOSoccerException;

public class PanelConfiguracion extends JPanel {
    private static final int[][] RESOLUCIONES = {{800, 450}, {1024, 576}, {1280, 720}};

    private DOPOSoccerGUI ventana;
    private JComboBox<String> comboResolucion;
    private JComboBox<String> comboTipo;
    private JSpinner spinnerCantidad;
    private JLabel etiquetaCantidad;
    private JButton botonJugar;

    public PanelConfiguracion(DOPOSoccerGUI ventana) {
        this.ventana = ventana;
        prepararElementos();
        prepararAcciones();
    }

    private void prepararElementos() {
        comboResolucion = new JComboBox<>(new String[] {"800 x 450", "1024 x 576", "1280 x 720"});
        comboTipo = new JComboBox<>(new String[] {"Por goles", "Por tiempo"});
        spinnerCantidad = new JSpinner(new SpinnerNumberModel(3, 0, 99, 1));
        etiquetaCantidad = new JLabel("Goles para ganar:");
        botonJugar = new JButton("Jugar");

        JPanel campos = new JPanel(new GridLayout(0, 2, 10, 10));
        campos.add(new JLabel("Resolución:"));
        campos.add(comboResolucion);
        campos.add(new JLabel("Tipo de partido:"));
        campos.add(comboTipo);
        campos.add(etiquetaCantidad);
        campos.add(spinnerCantidad);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(new JLabel("DOPOSoccer - Configuración", JLabel.CENTER), BorderLayout.NORTH);
        add(campos, BorderLayout.CENTER);
        add(botonJugar, BorderLayout.SOUTH);
    }

    private void prepararAcciones() {
        comboTipo.addActionListener(e -> actualizarEtiqueta());
        botonJugar.addActionListener(e -> jugar());
    }

    private void actualizarEtiqueta() {
        if(comboTipo.getSelectedIndex() == 0){
            etiquetaCantidad.setText("Goles para ganar: ");
        } else {
            etiquetaCantidad.setText("Minutos: ");
        }
    }

    private void jugar() {
        try{
            int cantidad = (Integer) spinnerCantidad.getValue();
            if(comboTipo.getSelectedIndex() == 0){
                ventana.getJuego().nuevoPartidoPorGoles(cantidad);
            } else {
                ventana.getJuego().nuevoPartidoPorTiempo(cantidad);
            }
            int[] resolucion = RESOLUCIONES[comboResolucion.getSelectedIndex()];
            ventana.iniciarJuego(resolucion[0], resolucion[1]);
        } catch (DOPOSoccerException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}