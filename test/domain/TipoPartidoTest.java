package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TipoPartidoTest {
    private static final double MARGEN = 0.0001;

    @Test
    public void porGolesNoDeberiaTerminarAntesDeLaMeta() throws DOPOSoccerException {
        PorGoles tipo = new PorGoles(3);
        assertFalse(tipo.haTerminado(0, 0));
        assertFalse(tipo.haTerminado(2, 1));
    }

    @Test
    public void porGolesDeberiaTerminarCuandoAlguienLlegaALaMeta() throws DOPOSoccerException {
        PorGoles tipo = new PorGoles(3);
        assertTrue(tipo.haTerminado(3, 1));
        assertTrue(tipo.haTerminado(1, 3));
    }

    @Test
    public void porGolesNoDeberiaDependerDelTiempo() throws DOPOSoccerException {
        PorGoles tipo = new PorGoles(3);
        tipo.actualizar(10000);
        assertFalse(tipo.haTerminado(2, 2));
    }

    @Test
    public void porGolesNoDeberiaAceptarMetaMenorQueUno() {
        DOPOSoccerException excepcion = assertThrows(DOPOSoccerException.class, () -> new PorGoles(0));
        assertEquals(DOPOSoccerException.GOLES_INVALIDOS, excepcion.getMessage());
    }

    @Test
    public void porTiempoDeberiaConvertirMinutosASegundos() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(2);
        assertEquals(120, tipo.getSegundosRestantes(), MARGEN);
    }

    @Test
    public void porTiempoDeberiaDescontarElTiempo() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(30);
        assertEquals(30, tipo.getSegundosRestantes(), MARGEN);
        assertFalse(tipo.haTerminado(1, 0));
    }

    @Test
    public void porTiempoNoDeberiaBajarDeCero() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(90);
        assertEquals(0, tipo.getSegundosRestantes(), MARGEN);
    }

    @Test
    public void porTiempoDeberiaTerminarSiHayGanadorAlFinal() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(60);
        assertTrue(tipo.haTerminado(1, 0));
        assertFalse(tipo.enMuerteSubita(1, 0));
    }

    @Test
    public void porTiempoDeberiaEntrarEnMuerteSubitaSiHayEmpate() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(60);
        assertFalse(tipo.haTerminado(1, 1));
        assertTrue(tipo.enMuerteSubita(1, 1));
    }

    @Test
    public void porTiempoDeberiaTerminarConElGolDeMuerteSubita() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(60);
        assertTrue(tipo.haTerminado(2, 1));
    }

    @Test
    public void porTiempoNoDeberiaEstarEnMuerteSubitaAntesDelFinal() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(30);
        assertFalse(tipo.enMuerteSubita(1, 1));
    }

    @Test
    public void porTiempoNoDeberiaAceptarMinutosMenoresQueUno() {
        DOPOSoccerException excepcion = assertThrows(DOPOSoccerException.class, () -> new PorTiempo(0));
        assertEquals(DOPOSoccerException.MINUTOS_INVALIDOS, excepcion.getMessage());
    }
}