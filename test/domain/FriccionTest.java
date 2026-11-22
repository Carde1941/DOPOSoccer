package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FriccionTest {
    private static final double MARGEN = 0.0001;
    private static final double PASO = 0.1;
    private static final double SUELO_DEL_BALON = 618; // suelo 640 - radio 22

    @Test
    public void laFriccionConstanteDeberiaRestarLaDesaceleracion() {
        Friccion friccion = new FriccionConstante(400);
        assertEquals(260, friccion.frenar(300, PASO), MARGEN);
        assertEquals(-260, friccion.frenar(-300, PASO), MARGEN);
    }

    @Test
    public void laFriccionConstanteNoDeberiaCambiarElSentido() {
        Friccion friccion = new FriccionConstante(400);
        assertEquals(0, friccion.frenar(30, PASO), MARGEN);
        assertEquals(0, friccion.frenar(-30, PASO), MARGEN);
    }

    @Test
    public void sinFriccionNoDeberiaFrenar() {
        assertEquals(300, new SinFriccion().frenar(300, PASO), MARGEN);
    }

    @Test
    public void elBalonDeberiaFrenarAlRodarPorElSuelo() {
        Balon balon = new Balon(640, SUELO_DEL_BALON);
        balon.aplicarImpulso(300, 0);
        balon.actualizar(PASO, new Cancha(new FriccionConstante(400), new SinFriccion()));
        assertEquals(260, balon.getVx(), MARGEN);
    }

    @Test
    public void elBalonDeberiaQuedarQuietoSiRuedaLoSuficiente() {
        Cancha cancha = new Cancha();
        Balon balon = new Balon(640, SUELO_DEL_BALON);
        balon.aplicarImpulso(300, 0);
        for (int i = 0; i < 300; i++) {
            balon.actualizar(0.01, cancha);
        }
        assertEquals(0, balon.getVx(), MARGEN);
    }

    @Test
    public void elBalonNoDeberiaFrenarEnElAireSinFriccionDeAire() {
        Balon balon = new Balon(640, 100);
        balon.aplicarImpulso(300, 0);
        balon.actualizar(PASO, new Cancha());
        assertEquals(300, balon.getVx(), MARGEN);
    }

    @Test
    public void deberiaPoderUsarOtraFriccionDeAire() {
        Balon balon = new Balon(640, 100);
        balon.aplicarImpulso(300, 0);
        balon.actualizar(PASO, new Cancha(new SinFriccion(), new FriccionConstante(100)));
        assertEquals(290, balon.getVx(), MARGEN);
    }
}