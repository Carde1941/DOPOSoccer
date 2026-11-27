package presentation;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import domain.DOPOSoccer;

/** Un grupo de cosas para dibujar, en orden, de atrás hacia adelante. */
public class VistaCompuesta implements Vista {
    private List<Vista> vistas = new ArrayList<>();

    /**
     * Agrega algo al grupo. Se dibuja encima de lo que ya estaba.
     *
     * @param vista lo que se agrega
     * @return este mismo grupo, para seguir agregando
     */
    public VistaCompuesta agregar(Vista vista) {
        vistas.add(vista);
        return this;
    }

    @Override
    public void dibujar(Graphics2D g2, DOPOSoccer juego) {
        for (Vista vista : vistas) {
            vista.dibujar(g2, juego);
        }
    }
}