package domain;

import java.io.Serializable;

public interface Tiro extends Serializable{
    void impulsar(Balon balon, int sentido);

    String getDescripcion();

    /** Cuánto le afecta la gravedad al balón mientras dura el tiro de poder: 1 normal, 0 sale recto. */
    default double getFactorGravedad(){
        return 1;
    }
}
