package domain;
import java.io.*;

/** La barra que se va llenando con el tiempo. Cuando está llena, el jugador puede activar su poder. */
public class BarraPoder implements Serializable {
    private static final double TIEMPO_CARGA = 10;
    private static final double BONO_POR_GOL = TIEMPO_CARGA / 4;
    private double segundosCargados;

    /** Crea la barra vacía. */
    public BarraPoder(){
        this.segundosCargados = 0;
    }

    /**
     * Llena la barra según el tiempo que pasó, sin pasarse del tope.
     *
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
    public void actualizar(double segundos){
        segundosCargados = Math.min(segundosCargados + segundos, TIEMPO_CARGA);
    }

    /**
     * Le da un empujón a la barra. Se llama cuando al jugador le meten un gol, como ayuda para el que
     * va perdiendo.
     */
    public void recibirGol(){
        segundosCargados = Math.min(segundosCargados + BONO_POR_GOL, TIEMPO_CARGA);
    }

    /**
     * Dice si la barra ya está llena.
     *
     * @return true si ya se puede activar el poder
     */
    public boolean estaLlena(){
        return segundosCargados >= TIEMPO_CARGA;
    }

    /** Deja la barra en cero. Pasa cuando el jugador activa el poder. */
    public void vaciar(){
        segundosCargados = 0;
    }

    /**
     * Qué tan llena está la barra.
     *
     * @return un número de 0 (vacía) a 1 (llena)
     */
    public double getProgreso(){
        return segundosCargados / TIEMPO_CARGA;
    }

}
