package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MejorasTest {
    private static final double MARGEN = 0.0001;

    @TempDir
    Path carpeta;

    private Perfil perfil;
    private DOPOSoccer juego;

    @BeforeEach
    public void preparar() {
        perfil = new Perfil();
        juego = new DOPOSoccer();
    }

    @Test
    public void laPrimeraMejoraDeberiaCostar500() throws DOPOSoccerException {
        assertEquals(500, perfil.precioMejora(Estadisticas.SPEED));
    }

    @Test
    public void elPrecioDeberiaDuplicarseConCadaNivel() throws DOPOSoccerException {
        perfil.sumarPuntos(500);
        perfil.mejorar(Estadisticas.SPEED);
        assertEquals(1000, perfil.precioMejora(Estadisticas.SPEED));
        assertEquals(500, perfil.precioMejora(Estadisticas.KICK));
    }

    @Test
    public void deberiaSubirElNivelYDescontarLosPuntos() throws DOPOSoccerException {
        perfil.sumarPuntos(600);
        perfil.mejorar(Estadisticas.SURVIVAL);
        assertEquals(2, perfil.getEstadisticas().getSurvival());
        assertEquals(100, perfil.getPuntos());
        assertEquals(110, perfil.getEstadisticas().vidaMaxima(), MARGEN);
    }

    @Test
    public void noDeberiaMejorarSinPuntosSuficientes() {
        perfil.sumarPuntos(499);
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class, () -> perfil.mejorar(Estadisticas.KICK));
        assertEquals(DOPOSoccerException.PUNTOS_INSUFICIENTES, e.getMessage());
        assertEquals(499, perfil.getPuntos());
    }

    @Test
    public void noDeberiaPasarDelNivelDiez() throws DOPOSoccerException {
        perfil.getEstadisticas().setJump(10);
        perfil.sumarPuntos(1000000);
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class, () -> perfil.mejorar(Estadisticas.JUMP));
        assertEquals(DOPOSoccerException.ESTADISTICA_AL_MAXIMO, e.getMessage());
    }

    @Test
    public void noDeberiaMejorarUnaEstadisticaQueNoExiste() {
        perfil.sumarPuntos(500);
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class, () -> perfil.mejorar("Defense"));
        assertEquals(DOPOSoccerException.ESTADISTICA_DESCONOCIDA, e.getMessage());
    }

    @Test
    public void alTerminarDeberiaAcumularLosPuntosUnaSolaVez() throws DOPOSoccerException {
        juego.nuevoPartido("Por goles", 3);
        juego.terminar();
        juego.terminar();
        juego.actualizar(0.01);
        assertEquals(25, juego.getPuntosAcumulados(DOPOSoccer.IZQUIERDO));
        assertEquals(25, juego.getPuntosAcumulados(DOPOSoccer.DERECHO));
    }

    @Test
    public void elProgresoDeberiaSeguirEnElSiguientePartido() throws DOPOSoccerException {
        juego.nuevoPartido("Por goles", 3);
        juego.terminar();
        juego.nuevoPartido("Por tiempo", 2);
        assertEquals(25, juego.getPuntosAcumulados(DOPOSoccer.IZQUIERDO));
    }

    @Test
    public void lasMejorasDeberianAplicarseEnElSiguientePartido() throws DOPOSoccerException {
        for (int i = 0; i < 20; i++) {
            juego.nuevoPartido("Por goles", 3);
            juego.terminar();
        }
        juego.mejorar(DOPOSoccer.DERECHO, Estadisticas.SURVIVAL);
        juego.nuevoPartido("Por goles", 3);
        assertEquals(110, juego.getVidaMaxima(DOPOSoccer.DERECHO), MARGEN);
        assertEquals(100, juego.getVidaMaxima(DOPOSoccer.IZQUIERDO), MARGEN);
        assertEquals(0, juego.getPuntosAcumulados(DOPOSoccer.DERECHO));
    }

    @Test
    public void deberiaGuardarYAbrirElProgreso() throws DOPOSoccerException {
        File archivo = carpeta.resolve("progreso.dat").toFile();
        juego.nuevoPartido("Por goles", 3);
        juego.terminar();
        juego.guardarProgreso(archivo);

        DOPOSoccer otro = new DOPOSoccer();
        otro.abrirProgreso(archivo);

        assertEquals(25, otro.getPuntosAcumulados(DOPOSoccer.IZQUIERDO));
    }

    @Test
    public void noDeberiaAbrirComoProgresoUnaPartida() throws DOPOSoccerException {
        File archivo = carpeta.resolve("partida.dat").toFile();
        juego.nuevoPartido("Por goles", 3);
        juego.guardar(archivo);
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class, () -> juego.abrirProgreso(archivo));
        assertEquals(DOPOSoccerException.ARCHIVO_NO_ES_PROGRESO, e.getMessage());
    }
}
