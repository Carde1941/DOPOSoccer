package domain;

/** Null Object: una superficie que no frena nada. */
public class SinFriccion implements Friccion {
    @Override
    public double frenar(double velocidad, double segundos) {
        return velocidad;
    }
}
