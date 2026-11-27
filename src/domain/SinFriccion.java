package domain;

/** Una superficie que no frena nada, como el aire. */
public class SinFriccion implements Friccion {
    @Override
    public double frenar(double velocidad, double segundos) {
        return velocidad;
    }
}
