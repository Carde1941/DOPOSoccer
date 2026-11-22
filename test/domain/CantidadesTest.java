package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CantidadesTest {
    private DOPOSoccer juego = new DOPOSoccer();

    @Test
    public void porTiempoDeberiaOfrecerSusMinutos() throws DOPOSoccerException {
        assertEquals(List.of(1, 2, 3, 5), juego.getCantidades("Por tiempo"));
    }

    @Test
    public void porGolesDeberiaOfrecerSusGoles() throws DOPOSoccerException {
        assertEquals(List.of(1, 3, 5, 7), juego.getCantidades("Por goles"));
    }

    @Test
    public void deberiaDescribirLaCantidadConSuUnidad() throws DOPOSoccerException {
        assertEquals("1 minuto", juego.describirCantidad("Por tiempo", 1));
        assertEquals("2 minutos", juego.describirCantidad("Por tiempo", 2));
        assertEquals("1 gol", juego.describirCantidad("Por goles", 1));
        assertEquals("3 goles", juego.describirCantidad("Por goles", 3));
    }

    @Test
    public void noDeberiaDarCantidadesDeUnTipoDesconocido() {
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class,
            () -> juego.getCantidades("Por penales"));
        assertEquals(DOPOSoccerException.TIPO_DESCONOCIDO, e.getMessage());
    }

    @Test
    public void cadaCantidadOfrecidaDeberiaCrearUnPartidoValido() throws DOPOSoccerException {
        for (String tipo : juego.getTiposPartido()) {
            for (int cantidad : juego.getCantidades(tipo)) {
                juego.nuevoPartido(tipo, cantidad);
            }
        }
    }
}
