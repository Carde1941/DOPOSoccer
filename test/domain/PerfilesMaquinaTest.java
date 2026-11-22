package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerfilesMaquinaTest {
    private Jugador izquierdo;
    private Jugador derecho;
    private Partido partido;

    @BeforeEach
    public void prepararPartido() throws DOPOSoccerException {
        izquierdo = new Jugador(new Estadisticas());
        derecho = new Jugador(new Estadisticas());
        partido = new Partido(izquierdo, derecho, new PorGoles(3));
    }

    private double desplazamientoAlJugar(Controlador maquina) {
        double antes = izquierdo.getX();
        maquina.decidir(izquierdo, partido, 1);
        izquierdo.moverse(0.1, partido.getCancha());
        return izquierdo.getX() - antes;
    }

    @Test
    public void deberiaOfrecerLosCuatroPerfiles() {
        assertEquals(List.of("Torpe", "Ofensiva", "Defensiva", "Experta"), new DOPOSoccer().getMaquinas());
    }

    @Test
    public void laOfensivaDeberiaIrPorElBalon() {
        partido.getBalon().reiniciar(800, 600);
        assertTrue(desplazamientoAlJugar(new Ofensiva()) > 0);
    }

    @Test
    public void laOfensivaDeberiaPatearConElBalonCerca() {
        partido.getBalon().reiniciar(380, 615);
        new Ofensiva().decidir(izquierdo, partido, 1);
        assertTrue(izquierdo.estaPateando());
    }

    @Test
    public void laOfensivaDeberiaSaltarConElBalonEncima() {
        partido.getBalon().reiniciar(330, 480);
        new Ofensiva().decidir(izquierdo, partido, 1);
        assertFalse(izquierdo.estaEnSuelo());
    }

    @Test
    public void laOfensivaDeberiaUsarElPoderApenasLoTiene() {
        izquierdo.actualizar(10);
        partido.getBalon().reiniciar(500, 600);
        new Ofensiva().decidir(izquierdo, partido, 1);
        assertTrue(izquierdo.tieneAura());
    }

    @Test
    public void laDefensivaDeberiaVolverASuArcoConElBalonLejos() {
        partido.getBalon().reiniciar(1000, 600);
        assertTrue(desplazamientoAlJugar(new Defensiva()) < 0);
    }

    @Test
    public void laDefensivaDeberiaSalirConElBalonEnSuMitad() {
        partido.getBalon().reiniciar(500, 600);
        assertTrue(desplazamientoAlJugar(new Defensiva()) > 0);
    }

    @Test
    public void laDefensivaNoDeberiaUsarElPoderConElBalonLejos() {
        izquierdo.actualizar(10);
        partido.getBalon().reiniciar(1000, 600);
        new Defensiva().decidir(izquierdo, partido, 1);
        assertFalse(izquierdo.tieneAura());
    }

    @Test
    public void laExpertaDeberiaAtacarSiNoVaGanando() {
        partido.getBalon().reiniciar(1000, 600);
        assertTrue(desplazamientoAlJugar(new Experta()) > 0);
    }

    @Test
    public void laExpertaDeberiaDefenderSiVaGanandoPorDos() {
        izquierdo.anotarGol();
        izquierdo.anotarGol();
        partido.getBalon().reiniciar(1000, 600);
        assertTrue(desplazamientoAlJugar(new Experta()) < 0);
    }

    @Test
    public void laExpertaDeberiaSeguirAtacandoConUnGolDeVentaja() {
        izquierdo.anotarGol();
        partido.getBalon().reiniciar(1000, 600);
        assertTrue(desplazamientoAlJugar(new Experta()) > 0);
    }

    @Test
    public void laExpertaDeberiaDefenderSiElRivalTieneAura() {
        derecho.actualizar(10);
        derecho.activarPoder();
        partido.getBalon().reiniciar(1000, 600);
        assertTrue(desplazamientoAlJugar(new Experta()) < 0);
    }

    @Test
    public void laExpertaDeberiaUsarElPoderEnLaMitadRival() {
        izquierdo.actualizar(10);
        partido.getBalon().reiniciar(1000, 600);
        new Experta().decidir(izquierdo, partido, 1);
        assertTrue(izquierdo.tieneAura());
    }

    @Test
    public void laExpertaDeberiaGuardarElPoderEnSuMitad() {
        izquierdo.actualizar(10);
        partido.getBalon().reiniciar(500, 600);
        new Experta().decidir(izquierdo, partido, 1);
        assertFalse(izquierdo.tieneAura());
    }

    @Test
    public void laDerechaDeberiaAtacarHaciaLaIzquierda() {
        partido.getBalon().reiniciar(300, 600);
        double antes = derecho.getX();
        new Ofensiva().decidir(derecho, partido, 1);
        derecho.moverse(0.1, partido.getCancha());
        assertTrue(derecho.getX() < antes);
    }

    @Test
    public void laTorpeDeberiaPoderActivarElPoder() {
        Random siemprePoder = new Random() {
            @Override
            public int nextInt(int limite) {
                return 4;
            }
        };
        izquierdo.actualizar(10);
        new Torpe(siemprePoder).decidir(izquierdo, partido, 1);
        assertTrue(izquierdo.tieneAura());
    }

    @Test
    public void ningunPerfilDeberiaUsarElTeclado() throws DOPOSoccerException {
        DOPOSoccer juego = new DOPOSoccer();
        juego.nuevoPartido("Por goles", 3);
        for (String perfil : juego.getMaquinas()) {
            juego.ponerMaquina(DOPOSoccer.DERECHO, perfil);
            assertFalse(juego.usaTeclado(DOPOSoccer.DERECHO));
        }
    }
}
