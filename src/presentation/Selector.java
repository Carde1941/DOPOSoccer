package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Selector con flechas a los lados para pasar de una opción a otra.
 *
 * @param <T> el tipo de las opciones
 */
public class Selector<T> extends JPanel {
    private List<T> opciones;
    private int indice;
    private JLabel valor;
    private Runnable alCambiar = () -> { };

    /**
     * Crea el selector con la primera opción escogida.
     *
     * @param opciones las opciones
     */
    public Selector(List<T> opciones) {
        this.opciones = opciones;
        setOpaque(false);
        setLayout(new BorderLayout(6, 0));
        BotonJuego anterior = new BotonJuego("◀", Estilo.CIAN);
        BotonJuego siguiente = new BotonJuego("▶", Estilo.CIAN);
        anterior.setFont(Estilo.NORMAL);
        siguiente.setFont(Estilo.NORMAL);
        anterior.setPreferredSize(new Dimension(38, 34));
        siguiente.setPreferredSize(new Dimension(38, 34));
        anterior.addActionListener(e -> mover(-1));
        siguiente.addActionListener(e -> mover(1));
        valor = new JLabel("", SwingConstants.CENTER);
        valor.setFont(Estilo.BOTON.deriveFont(14f));
        valor.setForeground(Estilo.TEXTO);
        add(anterior, BorderLayout.WEST);
        add(valor, BorderLayout.CENTER);
        add(siguiente, BorderLayout.EAST);
        mostrarValor();
    }

    /**
     * La opción escogida.
     *
     * @return la opción
     */
    public T getValor() {
        return opciones.get(indice);
    }

    /**
     * Escoge una opción. Si no está en la lista, no cambia nada.
     *
     * @param opcion la opción
     */
    public void seleccionar(T opcion) {
        int posicion = opciones.indexOf(opcion);
        if (posicion >= 0) {
            indice = posicion;
            mostrarValor();
        }
    }

    /**
     * Cambia las opciones del selector.
     *
     * @param nuevas las opciones nuevas
     */
    public void setOpciones(List<T> nuevas) {
        opciones = nuevas;
        indice = Math.min(indice, opciones.size() - 1);
        mostrarValor();
    }

    /**
     * Lo que hay que hacer cada vez que el usuario cambia la opción.
     *
     * @param accion lo que se hace
     */
    public void alCambiar(Runnable accion) {
        this.alCambiar = accion;
    }

    private void mover(int paso) {
        indice = (indice + paso + opciones.size()) % opciones.size();
        mostrarValor();
        alCambiar.run();
    }

    private void mostrarValor() {
        valor.setText(getValor().toString().toUpperCase());
    }
}