package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovimientoJugadorTest {
    private static final double MARGEN = 0.0001;
    private static final double PASO = 0.1;

    private Cancha cancha;
    private Estadisticas estadisticas;
    private Jugador jugador;

    @BeforeEach
    public void prepararJugador() {
        cancha = new Cancha();
        estadisticas = new Estadisticas();
        jugador = new Jugador(estadisticas);
        jugador.colocar(300, 530);
    }

    @Test
    public void deberiaQuedarEnElSueloAlColocarse() {
        assertEquals(300, jugador.getX(), MARGEN);
        assertEquals(530, jugador.getY(), MARGEN);
        assertTrue(jugador.estaEnSuelo());
    }

    @Test
    public void deberiaCaminarALaDerecha() {
        jugador.moverDerecha();
        jugador.moverse(PASO, cancha);
        assertEquals(328, jugador.getX(), MARGEN);
        assertEquals(530, jugador.getY(), MARGEN);
    }

    @Test
    public void deberiaCaminarALaIzquierda() {
        jugador.moverIzquierda();
        jugador.moverse(PASO, cancha);
        assertEquals(272, jugador.getX(), MARGEN);
    }

    @Test
    public void deberiaCaminarMasRapidoConSpeedCinco() throws DOPOSoccerException {
        estadisticas.setSpeed(5);
        jugador.moverDerecha();
        jugador.moverse(PASO, cancha);
        assertEquals(339.2, jugador.getX(), MARGEN);
    }

    @Test
    public void deberiaQuedarseQuietoAlDetenerse() {
        jugador.moverDerecha();
        jugador.moverse(PASO, cancha);
        jugador.detenerse();
        jugador.moverse(PASO, cancha);
        assertEquals(328, jugador.getX(), MARGEN);
    }

    @Test
    public void deberiaSubirAlSaltar() {
        jugador.saltar();
        jugador.moverse(PASO, cancha);
        assertEquals(473, jugador.getY(), MARGEN);
        assertFalse(jugador.estaEnSuelo());
    }

    @Test
    public void deberiaSaltarMasAltoConJumpCinco() throws DOPOSoccerException {
        estadisticas.setJump(5);
        jugador.saltar();
        jugador.moverse(PASO, cancha);
        assertEquals(444.2, jugador.getY(), MARGEN);
    }

    @Test
    public void noDeberiaSaltarEnElAire() {
        jugador.saltar();
        jugador.moverse(PASO, cancha);
        jugador.saltar();
        jugador.moverse(PASO, cancha);
        assertEquals(431, jugador.getY(), MARGEN);
    }

    @Test
    public void deberiaAterrizarDespuesDeSaltar() {
        jugador.saltar();
        for (int cuadro = 0; cuadro < 120; cuadro++) {
            jugador.moverse(1.0 / 60, cancha);
        }
        assertEquals(530, jugador.getY(), MARGEN);
        assertTrue(jugador.estaEnSuelo());
    }

    @Test
    public void noDeberiaSalirPorLaIzquierda() {
        jugador.colocar(5, 530);
        jugador.moverIzquierda();
        jugador.moverse(PASO, cancha);
        assertEquals(0, jugador.getX(), MARGEN);
    }

    @Test
    public void noDeberiaSalirPorLaDerecha() {
        jugador.colocar(1205, 530);
        jugador.moverDerecha();
        jugador.moverse(PASO, cancha);
        assertEquals(1210, jugador.getX(), MARGEN);
    }

    @Test
    public void noDeberiaMoverseNiSaltarAturdido() {
        jugador.recibirDano(1000);
        jugador.moverDerecha();
        jugador.saltar();
        jugador.moverse(PASO, cancha);
        assertEquals(300, jugador.getX(), MARGEN);
        assertEquals(530, jugador.getY(), MARGEN);
    }
}