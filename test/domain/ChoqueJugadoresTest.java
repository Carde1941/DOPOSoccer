package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChoqueJugadoresTest {
    private static final double MARGEN = 0.0001;
    private static final double SUELO_JUGADOR = 530; // suelo 640 - alto 110

    private Cancha cancha;
    private Jugador izquierdo;
    private Jugador derecho;

    @BeforeEach
    public void preparar() {
        cancha = new Cancha();
        izquierdo = new Jugador(new Estadisticas());
        derecho = new Jugador(new Estadisticas());
    }

    @Test
    public void noDeberiaHacerNadaSiNoSeTocan() {
        izquierdo.colocar(100, SUELO_JUGADOR);
        derecho.colocar(300, SUELO_JUGADOR);
        izquierdo.chocarCon(derecho, cancha);
        assertEquals(100, izquierdo.getX(), MARGEN);
        assertEquals(300, derecho.getX(), MARGEN);
    }

    @Test
    public void deberiaSepararAAmbosPorLaMitad() {
        izquierdo.colocar(500, SUELO_JUGADOR);
        derecho.colocar(560, SUELO_JUGADOR); 
        izquierdo.chocarCon(derecho, cancha);
        assertEquals(495, izquierdo.getX(), MARGEN);
        assertEquals(565, derecho.getX(), MARGEN);
    }

    @Test
    public void deberiaDarIgualQuienChoqueAQuien() {
        izquierdo.colocar(500, SUELO_JUGADOR);
        derecho.colocar(560, SUELO_JUGADOR);
        derecho.chocarCon(izquierdo, cancha);
        assertEquals(495, izquierdo.getX(), MARGEN);
        assertEquals(565, derecho.getX(), MARGEN);
    }

    @Test
    public void elQueEstaContraLaParedNoDeberiaSalirse() {
        izquierdo.colocar(0, SUELO_JUGADOR);
        derecho.colocar(50, SUELO_JUGADOR); // se montan 20 px
        izquierdo.chocarCon(derecho, cancha);
        assertEquals(0, izquierdo.getX(), MARGEN);
        assertEquals(70, derecho.getX(), MARGEN);
    }

    @Test
    public void deberiaQuedarParadoSobreLaCabezaDelOtro() {
        derecho.colocar(500, SUELO_JUGADOR);
        izquierdo.colocar(510, SUELO_JUGADOR - 105); // cae y se monta 5 px
        izquierdo.chocarCon(derecho, cancha);
        assertEquals(SUELO_JUGADOR - 110, izquierdo.getY(), MARGEN);
        assertEquals(510, izquierdo.getX(), MARGEN);
        assertTrue(izquierdo.estaEnSuelo());
    }

    @Test
    public void deberiaDejarDeEstarEnElSueloAlCaer() {
        izquierdo.colocar(500, 300);
        izquierdo.moverse(0.01, cancha);
        assertFalse(izquierdo.estaEnSuelo());
    }

    @Test
    public void enElPartidoNoDeberianAtravesarse() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(3));
        izquierdo.moverDerecha();
        derecho.moverIzquierda();
        for (int i = 0; i < 300; i++) {
            partido.actualizar(0.01);
        }
        assertTrue(izquierdo.getX() + izquierdo.getAncho() <= derecho.getX() + MARGEN);
    }
}