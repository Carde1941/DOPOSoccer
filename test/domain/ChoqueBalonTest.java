package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChoqueBalonTest {
    private static final double MARGEN = 0.0001;

    private Jugador jugador;

    @BeforeEach
    public void prepararJugador() {
        jugador = new Jugador(new Estadisticas());
        jugador.colocar(300, 530);
    }

    @Test
    public void deberiaRebotarHaciaArribaEnLaCabeza() {
        Balon balon = new Balon(335, 520);
        balon.aplicarImpulso(0, 200);
        balon.chocarCon(jugador);
        assertEquals(508, balon.getY(), MARGEN);
        assertEquals(-400, balon.getVy(), MARGEN);
    }

    @Test
    public void deberiaSalirPorLaIzquierda() {
        Balon balon = new Balon(290, 600);
        balon.aplicarImpulso(100, 0);
        balon.chocarCon(jugador);
        assertEquals(278, balon.getX(), MARGEN);
        assertEquals(-225, balon.getVx(), MARGEN);
    }

    @Test
    public void deberiaSalirPorLaDerecha() {
        Balon balon = new Balon(380, 600);
        balon.aplicarImpulso(-100, 0);
        balon.chocarCon(jugador);
        assertEquals(392, balon.getX(), MARGEN);
        assertEquals(225, balon.getVx(), MARGEN);
    }

    @Test
    public void noDeberiaCambiarSiNoLoToca() {
        Balon balon = new Balon(500, 600);
        balon.aplicarImpulso(100, 0);
        balon.chocarCon(jugador);
        assertEquals(500, balon.getX(), MARGEN);
        assertEquals(100, balon.getVx(), MARGEN);
    }

    @Test
    public void noDeberiaAtravesarAlJugadorEnElPartido() throws DOPOSoccerException {
        Jugador izquierdo = new Jugador(new Estadisticas());
        Jugador derecho = new Jugador(new Estadisticas());
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(3));
        partido.getBalon().reiniciar(200, 600);
        partido.getBalon().aplicarImpulso(400, 0);
        for (int cuadro = 0; cuadro < 30; cuadro++) {
            partido.actualizar(1.0 / 60);
        }
        assertTrue(partido.getBalon().getX() < izquierdo.getX());
    }
}