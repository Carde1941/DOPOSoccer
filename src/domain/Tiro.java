package domain;

import java.io.Serializable;

/** La forma en que sale el balón cuando alguien lo patea. */
public interface Tiro extends Serializable{
    /**
     * Le da al balón la velocidad del tiro.
     *
     * @param balon el balón
     * @param sentido 1 si sale hacia la derecha y -1 si sale hacia la izquierda
     */
    void impulsar(Balon balon, int sentido);

    /**
     * Explica en una frase cómo sale el tiro.
     *
     * @return la descripción
     */
    String getDescripcion();

    /**
     * Cuánto le afecta la gravedad al balón mientras dura el tiro de poder.
     *
     * @return 1 si cae normal, 0 si va recto sin caer
     */
    default double getFactorGravedad(){
        return 1;
    }
}
