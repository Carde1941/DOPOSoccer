package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinPartidoTest {
    private static final double MARGEN = 0.0001;
    private static final double PASO = 0.01;

    private Jugador izquierdo;
    private Jugador derecho;

    @BeforeEach
    public void prepararJugadores() {
        izquierdo = new Jugador(new Estadisticas());
        derecho = new Jugador(new Estadisticas());
    }

    private void golEnArcoIzquierdo(Partido partido) {
        partido.getBalon().reiniciar(60, 500);
        partido.actualizar(PASO);
    }

    @Test
    public void noDeberiaIniciarTerminadoNiPausado() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(3));
        assertFalse(partido.haTerminado());
        assertFalse(partido.estaPausado());
        assertNull(partido.getGanador());
    }

    @Test
    public void deberiaTerminarAlLlegarALosGoles() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(1));
        golEnArcoIzquierdo(partido);
        assertTrue(partido.haTerminado());
        assertSame(derecho, partido.getGanador());
    }

    @Test
    public void deberiaDarPuntosAlGanadorYAlPerdedor() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(1));
        golEnArcoIzquierdo(partido);
        assertEquals(100, derecho.getPuntos());
        assertEquals(25, izquierdo.getPuntos());
    }

    @Test
    public void noDeberiaTerminarAntesDeLaMeta() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(2));
        golEnArcoIzquierdo(partido);
        assertFalse(partido.haTerminado());
        assertEquals(0, derecho.getPuntos());
    }

    @Test
    public void noDeberiaMoverseDespuesDeTerminar() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(1));
        golEnArcoIzquierdo(partido);
        double y = partido.getBalon().getY();
        partido.actualizar(1);
        assertEquals(y, partido.getBalon().getY(), MARGEN);
    }

    @Test
    public void noDeberiaDarPuntosDosVeces() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(1));
        golEnArcoIzquierdo(partido);
        partido.terminar();
        assertEquals(100, derecho.getPuntos());
        assertEquals(25, izquierdo.getPuntos());
    }

    @Test
    public void noDeberiaMoverseEnPausa() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(3));
        partido.pausar();
        partido.actualizar(1);
        assertTrue(partido.estaPausado());
        assertEquals(100, partido.getBalon().getY(), MARGEN);
    }

    @Test
    public void deberiaMoverseAlReanudar() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(3));
        partido.pausar();
        partido.reanudar();
        partido.actualizar(0.1);
        assertFalse(partido.estaPausado());
        assertNotEquals(100, partido.getBalon().getY(), MARGEN);
    }

    @Test
    public void deberiaTerminarEmpatadoSiLosJugadoresAbandonan() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorGoles(3));
        partido.terminar();
        assertTrue(partido.haTerminado());
        assertNull(partido.getGanador());
        assertEquals(25, izquierdo.getPuntos());
        assertEquals(25, derecho.getPuntos());
    }

    @Test
    public void deberiaSeguirEnMuerteSubitaSiHayEmpate() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        Partido partido = new Partido(izquierdo, derecho, tipo);
        partido.getBalon().reiniciar(640, 300);
        for (int segundo = 0; segundo < 60; segundo++) {
            partido.actualizar(1);
        }
        assertFalse(partido.haTerminado());
        assertTrue(tipo.enMuerteSubita(izquierdo.getGoles(), derecho.getGoles()));
    }

    @Test
    public void deberiaTerminarConElGolDeMuerteSubita() throws DOPOSoccerException {
        Partido partido = new Partido(izquierdo, derecho, new PorTiempo(1));
        partido.getBalon().reiniciar(640, 300);
        for (int segundo = 0; segundo < 60; segundo++) {
            partido.actualizar(1);
        }
        golEnArcoIzquierdo(partido);
        assertTrue(partido.haTerminado());
        assertSame(derecho, partido.getGanador());
    }

    @Test
    public void deberiaGuardarElTipoDePartido() throws DOPOSoccerException {
        PorGoles tipo = new PorGoles(3);
        Partido partido = new Partido(izquierdo, derecho, tipo);
        assertSame(tipo, partido.getTipoPartido());
    }
}