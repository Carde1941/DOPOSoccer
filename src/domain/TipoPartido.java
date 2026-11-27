package domain;
import java.io.*;

/** La regla que decide cuándo termina el partido y qué se muestra debajo del marcador. */
public abstract class TipoPartido implements Serializable{
    /**
     * Avanza el tiempo del partido. Por defecto el tiempo no importa.
     *
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
    public void actualizar(double segundos){
        // por defecto el tiempo no afecta el partido
    };

    /**
     * Dice si el partido ya terminó con este marcador.
     *
     * @param golesIzquierdo los goles del jugador de la izquierda
     * @param golesDerecho los goles del jugador de la derecha
     * @return true si terminó
     */
    public abstract boolean haTerminado(int golesIzquierdo, int golesDerecho);

    /**
     * El texto que va debajo del marcador.
     *
     * @param golesIzquierdo los goles del jugador de la izquierda
     * @param golesDerecho los goles del jugador de la derecha
     * @return el texto
     */
    public abstract String getInformacion(int golesIzquierdo, int golesDerecho);
}