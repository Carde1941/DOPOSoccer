package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class PersistenciaTest {
    private static final double MARGEN = 0.0001;

    @TempDir 
    Path carpeta;

    private DOPOSoccer juego;
    private File archivo;

    @BeforeEach 
    public void prepararJuego() throws DOPOSoccerException{
        juego = new DOPOSoccer();
        juego.nuevoPartido("Por goles", 3);
        archivo = carpeta.resolve("partida.dat").toFile();
    }

    @Test 
    public void deberiaConservarElPartidoAlGuardarYAbrir() throws DOPOSoccerException {
        juego.moverDerecha(DOPOSoccer.IZQUIERDO);
        juego.actualizar(0.5);
        double x = juego.getXJugador(DOPOSoccer.IZQUIERDO);
        juego.guardar(archivo);

        DOPOSoccer abierto = new DOPOSoccer();
        abierto.abrir(archivo);

        assertEquals(x, abierto.getXJugador(DOPOSoccer.IZQUIERDO), MARGEN);
        assertEquals("A 3 goles", abierto.getInformacion());
    }


    @Test 
    public void deberiaAbrirConLosJugadoresQuietos() throws DOPOSoccerException{
        juego.moverDerecha(DOPOSoccer.IZQUIERDO);
        juego.guardar(archivo);

        DOPOSoccer abierto = new DOPOSoccer();
        abierto.abrir(archivo);
        double x = abierto.getXJugador(DOPOSoccer.IZQUIERDO);
        abierto.actualizar(0.5);

        assertEquals(x, abierto.getXJugador(DOPOSoccer.IZQUIERDO), MARGEN);

    }
    @Test
    public void noDeberiaGuardarSinPartido() {
        DOPOSoccer vacio = new DOPOSoccer();
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class, () -> vacio.guardar(archivo));
        assertEquals(DOPOSoccerException.NO_HAY_PARTIDO, e.getMessage());
    }

    @Test
    public void noDeberiaAbrirUnArchivoQueNoEsPartida() throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
            salida.writeObject("esto no es un partido");
        }
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class, () -> juego.abrir(archivo));
        assertEquals(DOPOSoccerException.ARCHIVO_NO_ES_PARTIDA, e.getMessage());
    }

    @Test
    public void noDeberiaAbrirUnArchivoDanadoYDeberiaGuardarLaCausa() throws IOException {
        Files.writeString(archivo.toPath(), "texto cualquiera");
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class, () -> juego.abrir(archivo));
        assertEquals(DOPOSoccerException.ERROR_AL_ABRIR, e.getMessage());
        assertNotNull(e.getCause());
    }

    @Test
    public void deberiaConservarElPartidoActualSiAbrirFalla() throws IOException {
        Files.writeString(archivo.toPath(), "texto cualquiera");
        assertThrows(DOPOSoccerException.class, () -> juego.abrir(archivo));
        assertEquals("A 3 goles", juego.getInformacion());
    }

    @Test
    public void laMaquinaDeberiaSeguirAlAbrirLaPartida() throws DOPOSoccerException {
        juego.ponerMaquina(DOPOSoccer.DERECHO, "Torpe");
        juego.guardar(archivo);

        DOPOSoccer abierto = new DOPOSoccer();
        abierto.abrir(archivo);

        assertFalse(abierto.esHumano(DOPOSoccer.DERECHO));
    }
}
