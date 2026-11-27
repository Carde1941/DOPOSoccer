package domain;
import java.io.Serializable;

/** Cómo se va frenando el balón cuando nadie lo toca. */
public interface Friccion extends Serializable {
    /**
     * Calcula la velocidad que le queda al balón después de frenarse un poco.
     *
     * @param velocidad la velocidad que lleva ahora
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     * @return la velocidad nueva
     */
    double frenar(double velocidad, double segundos);
}
