package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InformacionPartidoTest {

    @Test
    public void porGolesDeberiaMostrarLaMeta() throws DOPOSoccerException {
        PorGoles tipo = new PorGoles(3);
        assertEquals("A 3 goles", tipo.getInformacion(0, 0));
        assertEquals("A 3 goles", tipo.getInformacion(2, 1));
    }

    @Test
    public void porTiempoDeberiaMostrarElTiempoCompleto() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(2);
        assertEquals("2:00", tipo.getInformacion(0, 0));
    }

    @Test
    public void porTiempoDeberiaMostrarMinutosYSegundos() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(2);
        tipo.actualizar(50);
        assertEquals("1:10", tipo.getInformacion(0, 0));
    }

    @Test
    public void porTiempoDeberiaPonerCeroALaIzquierda() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(55);
        assertEquals("0:05", tipo.getInformacion(0, 0));
    }

    @Test
    public void porTiempoDeberiaRedondearHaciaArriba() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(59.6);
        assertEquals("0:01", tipo.getInformacion(0, 0));
    }

    @Test
    public void deberiaMostrarMuerteSubitaConEmpateEnCero() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(60);
        assertEquals("Muerte súbita", tipo.getInformacion(1, 1));
    }

    @Test
    public void noDeberiaMostrarMuerteSubitaSiNoHayEmpate() throws DOPOSoccerException {
        PorTiempo tipo = new PorTiempo(1);
        tipo.actualizar(60);
        assertEquals("0:00", tipo.getInformacion(2, 1));
    }

    @Test
    public void laFachadaDeberiaDarLaInformacionDelPartido() throws DOPOSoccerException {
        DOPOSoccer juego = new DOPOSoccer();
        juego.nuevoPartidoPorGoles(3);
        assertEquals("A 3 goles", juego.getInformacion());
        juego.nuevoPartidoPorTiempo(2);
        assertEquals("2:00", juego.getInformacion());
    }
}