package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PatadaTest {
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
    public void deberiaImpulsarElBalonAlPatearlo() {
        partido.getBalon().reiniciar(380, 615);
        izquierdo.patear();
        partido.actualizar(0.001);
        assertEquals(700, partido.getBalon().getVx(), MARGEN);
        assertEquals(-450, partido.getBalon().getVy(), MARGEN);
    }

    @Test
    public void deberiaPatearHaciaLaIzquierdaElJugadorDerecho() {
        partido.getBalon().reiniciar(900, 615);
        derecho.patear();
        partido.actualizar(0.001);
        assertEquals(-700, partido.getBalon().getVx(), MARGEN);
    }

    @Test
    public void deberiaDanarAlRivalAlAlcance() {
        derecho.colocar(400, 530);
        izquierdo.patear();
        partido.actualizar(0.001);
        assertEquals(90, derecho.getVida(), MARGEN);
    }

    @Test
    public void noDeberiaDanarAlRivalLejos() {
        izquierdo.patear();
        partido.actualizar(0.001);
        assertEquals(100, derecho.getVida(), MARGEN);
    }

    @Test
    public void soloDeberiaImpulsarElBalonSiLoToca() {
        partido.getBalon().reiniciar(380, 615);
        derecho.colocar(400, 530);
        izquierdo.patear();
        partido.actualizar(0.001);
        assertEquals(700, partido.getBalon().getVx(), MARGEN);
        assertEquals(100, derecho.getVida(), MARGEN);
    }

    @Test
    public void deberiaDanarUnaSolaVezPorPatada() {
        derecho.colocar(400, 530);
        izquierdo.patear();
        partido.actualizar(0.001);
        partido.actualizar(0.001);
        assertEquals(90, derecho.getVida(), MARGEN);
    }

    @Test
    public void deberiaDanarMasConMasKick() throws DOPOSoccerException {
        izquierdo.getEstadisticas().setKick(5);
        derecho.colocar(400, 530);
        izquierdo.patear();
        partido.actualizar(0.001);
        assertEquals(86, derecho.getVida(), MARGEN);
    }

    @Test
    public void noDeberiaPatearSiEstaAturdido() {
        izquierdo.recibirDano(100);
        partido.getBalon().reiniciar(380, 615);
        izquierdo.patear();
        partido.actualizar(0.001);
        assertEquals(0, partido.getBalon().getVx(), MARGEN);
    }

    @Test
    public void deberiaIgnorarOtraPatadaMientrasPatea() {
        derecho.colocar(400, 530);
        izquierdo.patear();
        partido.actualizar(0.1);
        derecho.colocar(400, 530);
        izquierdo.patear();
        partido.actualizar(0.001);
        assertEquals(90, derecho.getVida(), MARGEN);
    }

    @Test
    public void deberiaPoderPatearOtraVezAlTerminarLaPatada() {
        izquierdo.patear();
        assertTrue(izquierdo.estaPateando());
        izquierdo.actualizar(0.3);
        assertFalse(izquierdo.estaPateando());
    }
}