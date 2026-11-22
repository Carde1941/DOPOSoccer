package domain;
import java.io.Serializable;

/** Strategy: cómo una superficie frena una velocidad con el paso del tiempo. */
public interface Friccion extends Serializable {
    double frenar(double velocidad, double segundos);
}
