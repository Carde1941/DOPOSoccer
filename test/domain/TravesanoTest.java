package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TravesanoTest {
    private static final double MARGEN = 0.0001;
    private static final double CUADRO = 0.016;


    @Test 
    public void deberiaRebotarHaciaArribaAlCaerSobreElTravesano(){
        Balon balon = new Balon(45, 400);
        balon.aplicarImpulso(0, 600);
        balon.rebotarEn(0, 415, 90, 10);
        assertEquals(45, balon.getX(), MARGEN);
        assertEquals(393, balon.getY(), MARGEN);
        assertEquals(-450, balon.getVy(), MARGEN);
    }

     @Test
    public void deberiaRebotarHaciaAbajoSiLoGolpeaPorDebajo() {
        Balon balon = new Balon(45, 440);
        balon.aplicarImpulso(0, -600);
        balon.rebotarEn(0, 415, 90, 10);
        assertEquals(45, balon.getX(), MARGEN);
        assertEquals(447, balon.getY(), MARGEN);
        assertEquals(450, balon.getVy(), MARGEN);
    }

    @Test
    public void noDeberiaCambiarSiNoTocaElTravesano() {
        Balon balon = new Balon(300, 300);
        balon.aplicarImpulso(100, 100);
        balon.rebotarEn(0, 415, 90, 10);
        assertEquals(300, balon.getX(), MARGEN);
        assertEquals(300, balon.getY(), MARGEN);
        assertEquals(100, balon.getVx(), MARGEN);
        assertEquals(100, balon.getVy(), MARGEN);
    }

    @Test
    public void deberiaDesviarseHaciaArribaEnLaPuntaDelTravesano() {
        Balon balon = new Balon(100, 405);
        balon.aplicarImpulso(-300, 0);
        balon.rebotarEn(0, 415, 90, 10);
        assertTrue(balon.getVy() < 0);
        assertTrue(balon.getVx() > -300);
    }

    @Test
    public void laCanchaDeberiaHacerRebotarEnElTravesanoDerecho() {
        Cancha cancha = new Cancha();
        Balon balon = new Balon(1235, 400);
        balon.aplicarImpulso(0, 600);
        cancha.rebotarEnTravesanos(balon);
        assertTrue(balon.getVy() < 0);
    }

    @Test
    public void noDeberiaContarGolSiElBalonCaeSobreElArco() throws DOPOSoccerException {
        Partido partido = new Partido(new Jugador(new Estadisticas()), new Jugador(new Estadisticas()), new PorGoles(1));
        partido.getBalon().reiniciar(45, 300);
        for (int i = 0; i < 200; i++) {
            partido.actualizar(CUADRO);
        }
        assertEquals(0, partido.getJugadorDerecho().getGoles());
        assertTrue(partido.getBalon().getY() < 415);
    }


}
