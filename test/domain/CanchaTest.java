package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CanchaTest {
    private Cancha cancha;

    @BeforeEach
    public void prepararCancha() {
        cancha = new Cancha();
    }

    @Test
    public void deberiaDetectarGolEnArcoIzquierdo() {
        assertTrue(cancha.entroEnArcoIzquierdo(new Balon(60, 500)));
    }

    @Test
    public void noDeberiaContarSiNoCruzoLaLineaIzquierda() {
        assertFalse(cancha.entroEnArcoIzquierdo(new Balon(80, 500)));
    }

    @Test
    public void noDeberiaContarSiPasoSobreElArcoIzquierdo() {
        assertFalse(cancha.entroEnArcoIzquierdo(new Balon(60, 300)));
    }

    @Test
    public void deberiaDetectarGolEnArcoDerecho() {
        assertTrue(cancha.entroEnArcoDerecho(new Balon(1220, 500)));
    }

    @Test
    public void noDeberiaContarSiNoCruzoLaLineaDerecha() {
        assertFalse(cancha.entroEnArcoDerecho(new Balon(1200, 500)));
    }

    @Test
    public void noDeberiaContarSiPasoSobreElArcoDerecho() {
        assertFalse(cancha.entroEnArcoDerecho(new Balon(1220, 300)));
    }

    @Test
    public void noDeberiaHaberGolEnElCentro() {
        Balon balon = new Balon(640, 500);
        assertFalse(cancha.entroEnArcoIzquierdo(balon));
        assertFalse(cancha.entroEnArcoDerecho(balon));
    }
}