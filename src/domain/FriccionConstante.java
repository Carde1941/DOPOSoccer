package domain;

/** Frena siempre lo mismo cada segundo, hasta dejar el balón quieto. Así se comporta el césped. */
public class FriccionConstante implements Friccion{
    private final double desaceleracion;

    /**
     * Crea la fricción.
     *
     * @param desaceleracion cuánta velocidad le quita al balón cada segundo
     */
    public FriccionConstante(double desaceleracion){
        this.desaceleracion = desaceleracion;
    }

    @Override
    public double frenar(double velocidad, double segundos){
        double frenado = desaceleracion * segundos;
        if(Math.abs(velocidad) <= frenado){
            return 0;
        }
        return velocidad - Math.signum(velocidad) * frenado;
    }
}
