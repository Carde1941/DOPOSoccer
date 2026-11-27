package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FichaPersonajeTest {
    private static final double MARGEN = 0.0001;

    @Test
    public void deberiaDarLaFichaSinNecesitarUnPartido() throws DOPOSoccerException {
        DOPOSoccer juego = new DOPOSoccer();
        assertEquals("Rayo", juego.getNombrePoder("Camerún"));
        assertEquals(60, juego.getDanoPoder("Camerún"), MARGEN);
        assertEquals(3, juego.getDuracionEfecto("Camerún"), MARGEN);
    }

    @Test
    public void laDescripcionDelEfectoDeberiaSalirDeSuRegla() throws DOPOSoccerException {
        DOPOSoccer juego = new DOPOSoccer();
        assertTrue(juego.getDescripcionEfecto("Camerún").contains("50 %"));
    }

    @Test
    public void todosLosPersonajesDeberianTenerFichaCompleta() throws DOPOSoccerException {
        DOPOSoccer juego = new DOPOSoccer();
        for (String personaje : juego.getPersonajes()) {
            assertFalse(juego.getNombrePoder(personaje).isEmpty());
            assertFalse(juego.getDescripcionTiro(personaje).isEmpty());
            assertFalse(juego.getDescripcionEfecto(personaje).isEmpty());
            assertTrue(juego.getDanoPoder(personaje) > 0);
            assertTrue(juego.getDuracionEfecto(personaje) > 0);
        }
    }

    @Test
    public void noDeberiaDarLaFichaDeUnPersonajeQueNoExiste() {
        DOPOSoccer juego = new DOPOSoccer();
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class, () -> juego.getNombrePoder("Brasil"));
        assertEquals(DOPOSoccerException.PERSONAJE_DESCONOCIDO, e.getMessage());
    }

    @Test
    public void laDuracionNoDeberiaCambiarAunqueElEfectoAvance() {
        Efecto efecto = new Electrocutado();
        efecto.actualizar(1);
        assertEquals(3, efecto.getDuracion(), MARGEN);
        assertEquals(2, efecto.getSegundosRestantes(), MARGEN);
    }
}
