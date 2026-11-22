package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaquinaTest {
    private Jugador izquierdo;
    private Partido partido;

    @BeforeEach
    public void prepararPartido() throws DOPOSoccerException {
        izquierdo = new Jugador(new Estadisticas());
        partido = new Partido(izquierdo, new Jugador(new Estadisticas()), new PorGoles(3));
    }

    @Test
    public void elHumanoNoDeberiaMoverseSolo() {
        double x = izquierdo.getX();
        partido.actualizar(0.5);
        assertEquals(x, izquierdo.getX());
    }

    @Test
    public void laTorpeDeberiaHacerLaAccionQueSaleAlAzar() {
        Random siempreDerecha = new Random() {
            @Override
            public int nextInt(int limite) {
                return 1;
            }
        };
        izquierdo.setControlador(new Torpe(siempreDerecha));
        double x = izquierdo.getX();
        partido.actualizar(0.1);
        assertTrue(izquierdo.getX() > x);
    }

    @Test
    public void deberiaOfrecerLaMaquinaTorpe() {
        assertTrue(new DOPOSoccer().getMaquinas().contains("Torpe"));
    }

    @Test
    public void noDeberiaCrearUnaMaquinaQueNoExiste() throws DOPOSoccerException {
        DOPOSoccer juego = new DOPOSoccer();
        juego.nuevoPartidoPorGoles(3);
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class,
            () -> juego.ponerMaquina(DOPOSoccer.DERECHO, "Invencible"));
        assertEquals(DOPOSoccerException.MAQUINA_DESCONOCIDA, e.getMessage());
    }
}