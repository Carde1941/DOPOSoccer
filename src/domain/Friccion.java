package domain;
import java.io.Serializable;

public interface Friccion extends Serializable {
    double frenar(double velocidad, double segundos);
}
