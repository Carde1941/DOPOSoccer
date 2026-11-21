package presentation;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import domain.DOPOSoccer;

/** Composite: un grupo de vistas que se dibujan en orden, de atrás hacia adelante. */
public class VistaCompuesta implements Vista {
    private List<Vista> vistas = new ArrayList<>();

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