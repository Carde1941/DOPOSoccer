package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JugadorTest {
    private static final double MARGEN = 0.0001;

    private Estadisticas estadisticas;
    private Jugador jugador;

    @BeforeEach
    public void prepararJugador() {
        estadisticas = new Estadisticas();
        jugador = new Jugador(estadisticas);
    }

    @Test
    public void deberiaIniciarConVidaMaximaYSinGoles() {
        assertEquals(100, jugador.getVida(), MARGEN);
        assertEquals(0, jugador.getGoles());
        assertEquals(0, jugador.getPuntos());
        assertFalse(jugador.estaAturdido());
    }

    @Test
    public void deberiaIniciarConVidaSegunSurvival() throws DOPOSoccerException {
        estadisticas.setSurvival(3);
        Jugador resistente = new Jugador(estadisticas);
        assertEquals(120, resistente.getVida(), MARGEN);
    }

    @Test
    public void deberiaCalcularDanoPatadaBase() {
        assertEquals(10, jugador.danoPatada(), MARGEN);
    }

    @Test
    public void deberiaCalcularDanoPatadaConKickCinco() throws DOPOSoccerException {
        estadisticas.setKick(5);
        assertEquals(14, jugador.danoPatada(), MARGEN);
    }

    @Test
    public void deberiaQuedarConVidaDelEjemploTrasPatada() throws DOPOSoccerException {
        estadisticas.setSurvival(3);
        Jugador rival = new Jugador(estadisticas);
        rival.recibirDano(14);
        assertEquals(106, rival.getVida(), MARGEN);
    }

    @Test
    public void deberiaQuedarConVidaDelEjemploTrasTiroDePoder() throws DOPOSoccerException {
        estadisticas.setSurvival(3);
        Jugador rival = new Jugador(estadisticas);
        rival.recibirDano(84);
        assertEquals(36, rival.getVida(), MARGEN);
    }

    @Test
    public void noDeberiaBajarDeCeroYQuedarAturdido() {
        jugador.recibirDano(150);
        assertEquals(0, jugador.getVida(), MARGEN);
        assertTrue(jugador.estaAturdido());
    }

    @Test
    public void noDeberiaAturdirseSiConservaVida() {
        jugador.recibirDano(30);
        assertEquals(70, jugador.getVida(), MARGEN);
        assertFalse(jugador.estaAturdido());
    }

    @Test
    public void deberiaSeguirAturdidoAntesDeTresSegundos() {
        jugador.recibirDano(100);
        jugador.actualizar(1);
        assertTrue(jugador.estaAturdido());
        assertEquals(0, jugador.getVida(), MARGEN);
    }

    @Test
    public void deberiaRecuperarseALosTresSegundos() {
        jugador.recibirDano(100);
        jugador.actualizar(3);
        assertFalse(jugador.estaAturdido());
        assertEquals(100, jugador.getVida(), MARGEN);
    }

    @Test
    public void noDeberiaRecibirDanoMientrasEstaAturdido() {
        jugador.recibirDano(100);
        jugador.recibirDano(10);
        jugador.actualizar(1);
        jugador.actualizar(2);
        assertEquals(100, jugador.getVida(), MARGEN);
    }

    @Test
    public void noDeberiaCurarseConElTiempoSinEstarAturdido() {
        jugador.recibirDano(30);
        jugador.actualizar(5);
        assertEquals(70, jugador.getVida(), MARGEN);
    }

    @Test
    public void deberiaAcumularGoles() {
        jugador.anotarGol();
        jugador.anotarGol();
        jugador.anotarGol();
        assertEquals(3, jugador.getGoles());
    }

    @Test
    public void deberiaCargarLaBarraConElTiempo() {
        jugador.actualizar(5);
        assertEquals(0.5, jugador.getBarra().getProgreso(), MARGEN);
    }

    @Test
    public void deberiaCargarLaBarraAlRecibirGol() {
        jugador.recibirGol();
        assertEquals(0.25, jugador.getBarra().getProgreso(), MARGEN);
    }
}