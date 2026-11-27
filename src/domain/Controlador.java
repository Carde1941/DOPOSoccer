package domain;

import java.io.Serializable;

/** Quién maneja a un jugador: una persona con el teclado o una máquina. */
public interface Controlador extends Serializable {
    /**
     * Se llama en cada paso del juego para que el controlador decida qué hace su jugador.
     *
     * @param propio el jugador que se está controlando
     * @param partido el partido que se está jugando
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
    void decidir(Jugador propio, Partido partido, double segundos);

    /**
     * Dice si el jugador se maneja con el teclado.
     *
     * @return true si lo maneja una persona
     */
    boolean usaTeclado();
}
