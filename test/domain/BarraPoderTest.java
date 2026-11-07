package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BarraPoderTest {
    private static final double MARGEN = 0.0001;

    private BarraPoder barra;

    @BeforeEach
    public void prepararBarra() {
        barra = new BarraPoder();
    }

    @Test
    public void deberiaIniciarVacia() {
        assertEquals(0.0, barra.getProgreso(), MARGEN);
        assertFalse(barra.estaLlena());
    }

    @Test
    public void deberiaCargarseConElTiempo() {
        barra.actualizar(5);
        assertEquals(0.5, barra.getProgreso(), MARGEN);
        assertFalse(barra.estaLlena());
    }

    @Test
    public void deberiaLlenarseEnDiezSegundos() {
        barra.actualizar(10);
        assertEquals(1.0, barra.getProgreso(), MARGEN);
        assertTrue(barra.estaLlena());
    }

    @Test
    public void noDeberiaPasarseDelMaximoConElTiempo() {
        barra.actualizar(15);
        assertEquals(1.0, barra.getProgreso(), MARGEN);
    }

    @Test
    public void deberiaLlenarseConPasosPequenos() {
        for (int cuadro = 0; cuadro < 601; cuadro++) {
            barra.actualizar(1.0 / 60);
        }
        assertTrue(barra.estaLlena());
    }

    @Test
    public void deberiaSubirUnCuartoAlRecibirGol() {
        barra.recibirGol();
        assertEquals(0.25, barra.getProgreso(), MARGEN);
    }

    @Test
    public void noDeberiaPasarseDelMaximoAlRecibirGol() {
        barra.actualizar(9);
        barra.recibirGol();
        assertEquals(1.0, barra.getProgreso(), MARGEN);
        assertTrue(barra.estaLlena());
    }

    @Test
    public void deberiaVaciarse() {
        barra.actualizar(10);
        barra.vaciar();
        assertEquals(0.0, barra.getProgreso(), MARGEN);
        assertFalse(barra.estaLlena());
    }

    @Test
    public void deberiaRecargarseDespuesDeVaciarse() {
        barra.actualizar(10);
        barra.vaciar();
        barra.actualizar(5);
        assertEquals(0.5, barra.getProgreso(), MARGEN);
    }
}
