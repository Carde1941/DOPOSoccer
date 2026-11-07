package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PosicionPartidoTest {
    private static final double MARGEN = 0.0001;

    private Jugador izquierdo;
    private Jugador derecho;
    private Partido partido;

    @BeforeEach
    public void prepararPartido() throws DOPOSoccerException {
        izquierdo = new Jugador(new Estadisticas());
        derecho = new Jugador(new Estadisticas());
        partido = new Partido(izquierdo, derecho, new PorGoles(3));
    }

    @Test
    public void deberiaColocarALosJugadoresEnSuLado() {
        assertEquals(285, izquierdo.getX(), MARGEN);
        assertEquals(925, derecho.getX(), MARGEN);
        assertEquals(530, izquierdo.getY(), MARGEN);
        assertEquals(530, derecho.getY(), MARGEN);
    }

    @Test
    public void deberiaMoverALosJugadoresEnCadaCiclo() {
        izquierdo.moverDerecha();
        derecho.moverIzquierda();
        partido.actualizar(0.1);
        assertEquals(313, izquierdo.getX(), MARGEN);
        assertEquals(897, derecho.getX(), MARGEN);
    }

    @Test
    public void deberiaRecolocarALosJugadoresDespuesDeUnGol() {
        izquierdo.moverDerecha();
        partido.actualizar(0.1);
        partido.getBalon().reiniciar(60, 500);
        partido.actualizar(0.01);
        assertEquals(285, izquierdo.getX(), MARGEN);
        assertEquals(925, derecho.getX(), MARGEN);
    }
}