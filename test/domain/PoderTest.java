package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PoderTest {
    private static final double MARGEN = 0.0001;

    private Jugador izquierdo;
    private Jugador derecho;
    private Partido partido;

    @BeforeEach
    public void prepararPartido() throws DOPOSoccerException {
        izquierdo = new Jugador(new Estadisticas(), new Corea());
        derecho = new Jugador(new Estadisticas(), new Nigeria());
        partido = new Partido(izquierdo, derecho, new PorGoles(3));
    }

    @Test
    public void noDeberiaActivarElPoderSinLaBarraLlena() {
        izquierdo.actualizar(5);
        izquierdo.activarPoder();
        assertFalse(izquierdo.tieneAura());
    }

    @Test
    public void deberiaActivarElAuraYVaciarLaBarra() {
        izquierdo.actualizar(10);
        izquierdo.activarPoder();
        assertTrue(izquierdo.tieneAura());
        assertEquals(0, izquierdo.getBarra().getProgreso(), MARGEN);
    }

    @Test
    public void laBarraDeberiaRecargarseMientrasTieneElAura() {
        izquierdo.actualizar(10);
        izquierdo.activarPoder();
        izquierdo.actualizar(5);
        assertTrue(izquierdo.tieneAura());
        assertEquals(0.5, izquierdo.getBarra().getProgreso(), MARGEN);
    }

    @Test
    public void noDeberiaActivarOtroPoderMientrasTieneElAura() {
        izquierdo.actualizar(10);
        izquierdo.activarPoder();
        izquierdo.actualizar(10);
        izquierdo.activarPoder();
        assertTrue(izquierdo.tieneAura());
        assertEquals(1, izquierdo.getBarra().getProgreso(), MARGEN);
    }

    @Test
    public void noDeberiaActivarElPoderAturdido() {
        izquierdo.actualizar(10);
        izquierdo.recibirDano(100);
        izquierdo.activarPoder();
        assertFalse(izquierdo.tieneAura());
    }

    @Test
    public void deberiaLanzarElTiroDePoderAlTocarElBalonConAura() {
        izquierdo.actualizar(10);
        izquierdo.activarPoder();
        partido.getBalon().reiniciar(320, 560);
        partido.actualizar(0.001);
        assertFalse(izquierdo.tieneAura());
        assertTrue(partido.getBalon().tienePoder());
        assertEquals(1300, partido.getBalon().getVx(), MARGEN);
        assertEquals(0, partido.getBalon().getVy(), MARGEN);
    }

    @Test
    public void deberiaLanzarElTiroDePoderAlPatearConAura() {
        izquierdo.actualizar(10);
        izquierdo.activarPoder();
        partido.getBalon().reiniciar(380, 615);
        izquierdo.patear();
        partido.actualizar(0.001);
        assertFalse(izquierdo.tieneAura());
        assertEquals(1300, partido.getBalon().getVx(), MARGEN);
    }

    @Test
    public void deberiaDanarConElEjemploDelEnunciado() throws DOPOSoccerException {
        Estadisticas fuerte = new Estadisticas();
        fuerte.setKick(5);
        Estadisticas resistente = new Estadisticas();
        resistente.setSurvival(3);
        Jugador camerun = new Jugador(fuerte, new Camerun());
        Jugador rival = new Jugador(resistente);
        Partido conCamerun = new Partido(camerun, rival, new PorGoles(3));

        conCamerun.getBalon().reiniciar(960, 585);
        camerun.lanzarPoder(conCamerun.getBalon(), 1);
        conCamerun.actualizar(0.001);

        assertEquals(36, rival.getVida(), MARGEN);
        assertEquals("Electrocutado", rival.getEfecto().getNombre());
        assertFalse(conCamerun.getBalon().tienePoder());
    }

    @Test
    public void elTiroDePoderNoDeberiaDanarAlQueLoLanzo() {
        partido.getBalon().reiniciar(320, 560);
        izquierdo.lanzarPoder(partido.getBalon(), 1);
        partido.actualizar(0.001);
        assertEquals(100, izquierdo.getVida(), MARGEN);
        assertEquals("Ninguno", izquierdo.getEfecto().getNombre());
    }

    @Test
    public void elGolConPoderDeberiaContarSinDanoNiEfecto() {
        partido.getBalon().reiniciar(1250, 600);
        izquierdo.lanzarPoder(partido.getBalon(), 1);
        partido.actualizar(0.01);
        assertEquals(1, izquierdo.getGoles());
        assertEquals(100, derecho.getVida(), MARGEN);
        assertEquals("Ninguno", derecho.getEfecto().getNombre());
        assertFalse(partido.getBalon().tienePoder());
    }

    @Test
    public void unEfectoNuevoDeberiaReemplazarAlAnterior() {
        derecho.aplicarEfecto(new Electrocutado(), partido);
        derecho.aplicarEfecto(new Inconsciente(), partido);
        assertEquals("Inconsciente", derecho.getEfecto().getNombre());
    }

    @Test
    public void elEfectoDeberiaTerminarConElTiempo() {
        derecho.aplicarEfecto(new Electrocutado(), partido);
        derecho.actualizar(2.9);
        assertEquals("Electrocutado", derecho.getEfecto().getNombre());
        derecho.actualizar(0.2);
        assertEquals("Ninguno", derecho.getEfecto().getNombre());
    }

    @Test
    public void electrocutadoNoDeberiaSaltarYDeberiaIrALaMitad() {
        derecho.aplicarEfecto(new Electrocutado(), partido);
        derecho.saltar();
        assertTrue(derecho.estaEnSuelo());
        double antes = derecho.getX();
        derecho.moverIzquierda();
        derecho.moverse(0.1, partido.getCancha());
        assertEquals(antes - 14, derecho.getX(), MARGEN);
    }

    @Test
    public void inconscienteDeberiaSalirAlAireSinMoversePatearNiSaltar() {
        derecho.aplicarEfecto(new Inconsciente(), partido);
        assertFalse(derecho.estaEnSuelo());
        derecho.patear();
        assertFalse(derecho.estaPateando());
        double antes = derecho.getX();
        derecho.moverIzquierda();
        derecho.moverse(0.1, partido.getCancha());
        assertEquals(antes, derecho.getX(), MARGEN);
    }

    @Test
    public void empujeDeberiaLanzarAlRivalHaciaSuArco() {
        double antes = derecho.getX();
        derecho.aplicarEfecto(new Empuje(), partido);
        derecho.moverIzquierda();
        derecho.moverse(0.1, partido.getCancha());
        assertEquals(antes + 45, derecho.getX(), MARGEN);
    }

    @Test
    public void confusionDeberiaDividirElBalonYLuegoQuitarLosFalsos() {
        derecho.aplicarEfecto(new Confusion(), partido);
        assertEquals(7, partido.getBalonesFalsos().size());
        partido.actualizar(2.4);
        assertEquals(7, partido.getBalonesFalsos().size());
        partido.actualizar(0.2);
        assertEquals(0, partido.getBalonesFalsos().size());
    }

    @Test
    public void otroEfectoDeberiaQuitarLosBalonesFalsos() {
        derecho.aplicarEfecto(new Confusion(), partido);
        partido.getBalon().reiniciar(960, 585);
        izquierdo.lanzarPoder(partido.getBalon(), 1);
        partido.actualizar(0.001);
        assertEquals("Empuje", derecho.getEfecto().getNombre());
        assertEquals(0, partido.getBalonesFalsos().size());
    }

    @Test
    public void deberiaOfrecerLosCuatroPersonajes() {
        DOPOSoccer juego = new DOPOSoccer();
        assertEquals(4, juego.getPersonajes().size());
    }

    @Test
    public void deberiaElegirElPersonajeDeCadaJugador() throws DOPOSoccerException {
        DOPOSoccer juego = new DOPOSoccer();
        juego.nuevoPartido("Por goles", 3);
        juego.elegirPersonaje(DOPOSoccer.IZQUIERDO, "Estados Unidos");
        assertEquals("Estados Unidos", juego.getPersonaje(DOPOSoccer.IZQUIERDO));
    }

    @Test
    public void noDeberiaElegirUnPersonajeQueNoExiste() throws DOPOSoccerException {
        DOPOSoccer juego = new DOPOSoccer();
        juego.nuevoPartido("Por goles", 3);
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class,
            () -> juego.elegirPersonaje(DOPOSoccer.IZQUIERDO, "Brasil"));
        assertEquals(DOPOSoccerException.PERSONAJE_DESCONOCIDO, e.getMessage());
    }
}
