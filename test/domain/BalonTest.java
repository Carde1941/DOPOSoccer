package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalonTest {
    private static final double MARGEN = 0.0001;
    private static final double PASO = 0.1;

    private Cancha cancha;

    @BeforeEach
    public void prepararCancha() {
        cancha = new Cancha();
    }

    @Test
    public void deberiaIniciarQuieto() {
        Balon balon = new Balon(640, 100);
        assertEquals(640, balon.getX(), MARGEN);
        assertEquals(100, balon.getY(), MARGEN);
        assertEquals(0, balon.getVx(), MARGEN);
        assertEquals(0, balon.getVy(), MARGEN);
    }

    @Test
    public void deberiaCaerPorGravedad() {
        Balon balon = new Balon(640, 100);
        balon.actualizar(PASO, cancha);
        assertEquals(115, balon.getY(), MARGEN);
        assertEquals(150, balon.getVy(), MARGEN);
    }

    @Test
    public void deberiaAvanzarSegunSuVelocidad() {
        Balon balon = new Balon(640, 100);
        balon.aplicarImpulso(300, 0);
        balon.actualizar(PASO, cancha);
        assertEquals(670, balon.getX(), MARGEN);
    }

    @Test
    public void deberiaAcumularElMovimiento() {
        Balon balon = new Balon(640, 100);
        balon.aplicarImpulso(300, 0);
        balon.actualizar(PASO, cancha);
        balon.actualizar(PASO, cancha);
        assertEquals(700, balon.getX(), MARGEN);
    }

    @Test
    public void deberiaRebotarEnElSuelo() {
        Balon balon = new Balon(640, 610);
        balon.aplicarImpulso(0, 500);
        balon.actualizar(PASO, cancha);
        assertEquals(618, balon.getY(), MARGEN);
        assertEquals(-487.5, balon.getVy(), MARGEN);
    }

    @Test
    public void deberiaRebotarEnElTecho() {
        Balon balon = new Balon(640, 30);
        balon.aplicarImpulso(0, -500);
        balon.actualizar(PASO, cancha);
        assertEquals(22, balon.getY(), MARGEN);
        assertEquals(262.5, balon.getVy(), MARGEN);
    }

    @Test
    public void deberiaRebotarEnLaParedIzquierda() {
        Balon balon = new Balon(30, 300);
        balon.aplicarImpulso(-200, 0);
        balon.actualizar(PASO, cancha);
        assertEquals(22, balon.getX(), MARGEN);
        assertEquals(150, balon.getVx(), MARGEN);
    }

    @Test
    public void deberiaRebotarEnLaParedDerecha() {
        Balon balon = new Balon(1250, 300);
        balon.aplicarImpulso(200, 0);
        balon.actualizar(PASO, cancha);
        assertEquals(1258, balon.getX(), MARGEN);
        assertEquals(-150, balon.getVx(), MARGEN);
    }

    @Test
    public void deberiaReiniciarseQuieto() {
        Balon balon = new Balon(100, 300);
        balon.aplicarImpulso(500, -300);
        balon.reiniciar(640, 100);
        assertEquals(640, balon.getX(), MARGEN);
        assertEquals(100, balon.getY(), MARGEN);
        assertEquals(0, balon.getVx(), MARGEN);
        assertEquals(0, balon.getVy(), MARGEN);
    }

    @Test
    public void noDeberiaAtravesarElSueloConElTiempo() {
        Balon balon = new Balon(640, 100);
        for (int cuadro = 0; cuadro < 1200; cuadro++) {
            balon.actualizar(1.0 / 60, cancha);
            assertTrue(balon.getY() + balon.getRadio() <= cancha.getSuelo() + MARGEN);
        }
    }
}