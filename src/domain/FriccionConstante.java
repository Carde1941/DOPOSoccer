package domain;

/** Resta siempre la misma desaceleración hasta dejar la velocidad en cero, sin cambiarle el sentido. */
public class FriccionConstante implements Friccion{
    private final double desaceleracion;

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
