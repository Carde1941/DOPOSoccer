package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TiroTest {
    private static final double MARGEN = 0.0001;

    @Test
    public void deberiaLanzarElBalonHaciaAdelanteYHaciaArriba() {
        Balon balon = new Balon(640, 300);
        new TiroParabolico(650, 750).impulsar(balon, 1);
        assertEquals(650, balon.getVx(), MARGEN);
        assertEquals(-750, balon.getVy(), MARGEN);
    }

    @Test
    public void deberiaLanzarHaciaLaIzquierdaConSentidoNegativo() {
        Balon balon = new Balon(640, 300);
        new TiroParabolico(650, 750).impulsar(balon, -1);
        assertEquals(-650, balon.getVx(), MARGEN);
        assertEquals(-750, balon.getVy(), MARGEN);
    }

    @Test
    public void elBalonPateadoDeberiaSubirMasDeCientoCincuentaPixeles() {
        Cancha cancha = new Cancha();
        Balon balon = new Balon(300, 618);
        new TiroParabolico(650, 750).impulsar(balon, 1);
        double masAlto = balon.getY();
        for (int i = 0; i < 100; i++) {
            balon.actualizar(0.01, cancha);
            masAlto = Math.min(masAlto, balon.getY());
        }
        assertTrue(618 - masAlto > 150);
    }
}