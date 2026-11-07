package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DOPOSoccerTest {
    private static final double MARGEN = 0.0001;

    private DOPOSoccer juego;

    @BeforeEach
    public void prepararJuego() throws DOPOSoccerException {
        juego = new DOPOSoccer();
        juego.nuevoPartidoPorGoles(3);
    }

    @Test
    public void deberiaCrearUnPartidoPorGoles() {
        assertFalse(juego.esPorTiempo());
        assertEquals(0, juego.getGoles(DOPOSoccer.IZQUIERDO));
        assertEquals(0, juego.getGoles(DOPOSoccer.DERECHO));
    }

    @Test
    public void deberiaCrearUnPartidoPorTiempo() throws DOPOSoccerException {
        juego.nuevoPartidoPorTiempo(2);
        assertTrue(juego.esPorTiempo());
        assertEquals(120, juego.getSegundosRestantes(), MARGEN);
    }

    @Test
    public void noDeberiaCrearUnPartidoConGolesInvalidos() {
        assertThrows(DOPOSoccerException.class, () -> juego.nuevoPartidoPorGoles(0));
    }

    @Test
    public void noDeberiaCrearUnPartidoConMinutosInvalidos() {
        assertThrows(DOPOSoccerException.class, () -> juego.nuevoPartidoPorTiempo(0));
    }

    @Test
    public void deberiaDarLasMedidasDeLaCancha() {
        assertEquals(1280, juego.getAnchoCancha(), MARGEN);
        assertEquals(720, juego.getAltoCancha(), MARGEN);
        assertEquals(640, juego.getSuelo(), MARGEN);
        assertEquals(90, juego.getAnchoArco(), MARGEN);
        assertEquals(220, juego.getAltoArco(), MARGEN);
    }

    @Test
    public void deberiaPonerElBalonEnElSaque() {
        assertEquals(640, juego.getXBalon(), MARGEN);
        assertEquals(100, juego.getYBalon(), MARGEN);
        assertEquals(22, juego.getRadioBalon(), MARGEN);
    }

    @Test
    public void deberiaColocarALosJugadores() {
        assertEquals(285, juego.getXJugador(DOPOSoccer.IZQUIERDO), MARGEN);
        assertEquals(925, juego.getXJugador(DOPOSoccer.DERECHO), MARGEN);
        assertEquals(530, juego.getYJugador(DOPOSoccer.IZQUIERDO), MARGEN);
        assertEquals(70, juego.getAnchoJugador(DOPOSoccer.IZQUIERDO), MARGEN);
        assertEquals(110, juego.getAltoJugador(DOPOSoccer.DERECHO), MARGEN);
    }

    @Test
    public void deberiaDarLaVidaYElPoder() {
        assertEquals(100, juego.getVida(DOPOSoccer.IZQUIERDO), MARGEN);
        assertEquals(100, juego.getVidaMaxima(DOPOSoccer.IZQUIERDO), MARGEN);
        juego.actualizar(1);
        assertEquals(0.1, juego.getProgresoPoder(DOPOSoccer.DERECHO), MARGEN);
        assertFalse(juego.estaAturdido(DOPOSoccer.DERECHO));
    }

    @Test
    public void deberiaMoverAlJugadorIzquierdo() {
        double inicial = juego.getXJugador(DOPOSoccer.IZQUIERDO);
        juego.moverDerecha(DOPOSoccer.IZQUIERDO);
        juego.actualizar(0.1);
        assertTrue(juego.getXJugador(DOPOSoccer.IZQUIERDO) > inicial);
    }

    @Test
    public void deberiaMoverAlJugadorDerecho() {
        double inicial = juego.getXJugador(DOPOSoccer.DERECHO);
        juego.moverIzquierda(DOPOSoccer.DERECHO);
        juego.actualizar(0.1);
        assertTrue(juego.getXJugador(DOPOSoccer.DERECHO) < inicial);
    }

    @Test
    public void deberiaDetenerAlJugador() {
        juego.moverDerecha(DOPOSoccer.IZQUIERDO);
        juego.detenerse(DOPOSoccer.IZQUIERDO);
        double inicial = juego.getXJugador(DOPOSoccer.IZQUIERDO);
        juego.actualizar(0.1);
        assertEquals(inicial, juego.getXJugador(DOPOSoccer.IZQUIERDO), MARGEN);
    }

    @Test
    public void deberiaHacerSaltarAlJugador() {
        juego.saltar(DOPOSoccer.DERECHO);
        juego.actualizar(0.1);
        assertTrue(juego.getYJugador(DOPOSoccer.DERECHO) < 530);
    }

    @Test
    public void deberiaHacerPatearAlJugador() {
        juego.patear(DOPOSoccer.IZQUIERDO);
        assertTrue(juego.estaPateando(DOPOSoccer.IZQUIERDO));
        assertFalse(juego.estaPateando(DOPOSoccer.DERECHO));
    }

    @Test
    public void deberiaPausarYReanudar() {
        juego.pausar();
        assertTrue(juego.estaPausado());
        juego.reanudar();
        assertFalse(juego.estaPausado());
    }

    @Test
    public void deberiaTerminarEnEmpateConPuntos() {
        juego.terminar();
        assertTrue(juego.haTerminado());
        assertEquals(DOPOSoccer.EMPATE, juego.getGanador());
        assertEquals(25, juego.getPuntos(DOPOSoccer.IZQUIERDO));
        assertEquals(25, juego.getPuntos(DOPOSoccer.DERECHO));
    }
}