package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PartidoTest {
    private static final double MARGEN = 0.0001;
    private static final double PASO = 0.01;

    private Jugador izquierdo;
    private Jugador derecho;
    private Partido partido;

    @BeforeEach
    public void prepararPartido() {
        izquierdo = new Jugador(new Estadisticas());
        derecho = new Jugador(new Estadisticas());
        partido = new Partido(izquierdo, derecho);
    }

    @Test
    public void deberiaIniciarConBalonEnElCentroYSinGoles() {
        assertEquals(640, partido.getBalon().getX(), MARGEN);
        assertEquals(100, partido.getBalon().getY(), MARGEN);
        assertEquals(0, partido.getJugadorIzquierdo().getGoles());
        assertEquals(0, partido.getJugadorDerecho().getGoles());
    }

    @Test
    public void deberiaGuardarCadaJugadorEnSuLado() {
        assertSame(izquierdo, partido.getJugadorIzquierdo());
        assertSame(derecho, partido.getJugadorDerecho());
    }

    @Test
    public void deberiaDarGolAlDerechoSiEntraEnArcoIzquierdo() {
        partido.getBalon().reiniciar(60, 500);
        partido.actualizar(PASO);
        assertEquals(1, derecho.getGoles());
        assertEquals(0, izquierdo.getGoles());
    }

    @Test
    public void deberiaDarGolAlIzquierdoSiEntraEnArcoDerecho() {
        partido.getBalon().reiniciar(1220, 500);
        partido.actualizar(PASO);
        assertEquals(1, izquierdo.getGoles());
        assertEquals(0, derecho.getGoles());
    }

    @Test
    public void deberiaCargarLaBarraDelQueRecibeElGol() {
        partido.getBalon().reiniciar(60, 500);
        partido.actualizar(PASO);
        double progresoIzquierdo = izquierdo.getBarra().getProgreso();
        double progresoDerecho = derecho.getBarra().getProgreso();
        assertEquals(0.25, progresoIzquierdo - progresoDerecho, MARGEN);
    }

    @Test
    public void deberiaReiniciarElSaqueDespuesDelGol() {
        partido.getBalon().reiniciar(60, 500);
        partido.actualizar(PASO);
        assertEquals(640, partido.getBalon().getX(), MARGEN);
        assertEquals(100, partido.getBalon().getY(), MARGEN);
        assertEquals(0, partido.getBalon().getVy(), MARGEN);
    }

    @Test
    public void noDeberiaHaberGolConElBalonEnElCentro() {
        partido.getBalon().reiniciar(640, 300);
        partido.actualizar(PASO);
        assertEquals(0, izquierdo.getGoles());
        assertEquals(0, derecho.getGoles());
    }

    @Test
    public void deberiaMoverElBalonYCargarBarras() {
        partido.actualizar(1);
        assertNotEquals(100, partido.getBalon().getY(), MARGEN);
        assertEquals(0.1, izquierdo.getBarra().getProgreso(), MARGEN);
        assertEquals(0.1, derecho.getBarra().getProgreso(), MARGEN);
    }
}