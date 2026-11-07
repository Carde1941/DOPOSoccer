package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstadisticasTest {
    private static final double MARGEN = 0.0001;

    private Estadisticas estadisticas;

    @BeforeEach
    public void prepararEstadisticas() {
        estadisticas = new Estadisticas();
    }

    @Test
    public void deberiaIniciarTodasLasEstadisticasEnUno() {
        assertEquals(1, estadisticas.getSpeed());
        assertEquals(1, estadisticas.getKick());
        assertEquals(1, estadisticas.getJump());
        assertEquals(1, estadisticas.getSurvival());
    }

    @Test
    public void deberiaIniciarConMultiplicadorUno() {
        assertEquals(1.0, estadisticas.multiplicadorKick(), MARGEN);
    }

    @Test
    public void deberiaCalcularMultiplicadorConKickCinco() throws DOPOSoccerException {
        estadisticas.setKick(5);
        assertEquals(1.4, estadisticas.multiplicadorKick(), MARGEN);
    }

    @Test
    public void deberiaCalcularMultiplicadorConKickDiez() throws DOPOSoccerException {
        estadisticas.setKick(10);
        assertEquals(1.9, estadisticas.multiplicadorKick(), MARGEN);
    }

    @Test
    public void deberiaIniciarConVidaMaximaDeCien() {
        assertEquals(100, estadisticas.vidaMaxima(), MARGEN);
    }

    @Test
    public void deberiaCalcularVidaMaximaConSurvivalTres() throws DOPOSoccerException {
        estadisticas.setSurvival(3);
        assertEquals(120, estadisticas.vidaMaxima(), MARGEN);
    }

    @Test
    public void deberiaCalcularVidaMaximaConSurvivalDiez() throws DOPOSoccerException {
        estadisticas.setSurvival(10);
        assertEquals(190, estadisticas.vidaMaxima(), MARGEN);
    }

    @Test
    public void deberiaModificarSpeedYJump() throws DOPOSoccerException {
        estadisticas.setSpeed(4);
        estadisticas.setJump(7);
        assertEquals(4, estadisticas.getSpeed());
        assertEquals(7, estadisticas.getJump());
    }

    @Test
    public void deberiaAceptarLosLimites() throws DOPOSoccerException {
        estadisticas.setKick(1);
        assertEquals(1, estadisticas.getKick());
        estadisticas.setKick(10);
        assertEquals(10, estadisticas.getKick());
    }

    @Test
    public void noDeberiaAceptarValorMenorQueUno() {
        DOPOSoccerException excepcion = assertThrows(DOPOSoccerException.class, () -> estadisticas.setKick(0));
        assertEquals(DOPOSoccerException.ESTADISTICA_FUERA_DE_RANGO, excepcion.getMessage());
    }

    @Test
    public void noDeberiaAceptarValorMayorQueDiez() {
        assertThrows(DOPOSoccerException.class, () -> estadisticas.setKick(11));
    }

    @Test
    public void deberiaValidarTodasLasEstadisticas() {
        assertThrows(DOPOSoccerException.class, () -> estadisticas.setSpeed(0));
        assertThrows(DOPOSoccerException.class, () -> estadisticas.setJump(11));
        assertThrows(DOPOSoccerException.class, () -> estadisticas.setSurvival(-1));
    }

    @Test
    public void deberiaConservarElValorAnteriorSiElNuevoEsInvalido() throws DOPOSoccerException {
        estadisticas.setKick(5);
        assertThrows(DOPOSoccerException.class, () -> estadisticas.setKick(11));
        assertEquals(5, estadisticas.getKick());
    }
}