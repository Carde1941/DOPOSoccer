package domain;

/** El efecto del Rayo de Camerún: el rival no puede saltar y camina a la mitad de su velocidad. */
public class Electrocutado extends Efecto{
    private static final double DURACION = 3;
    private static final double FACTOR_VELOCIDAD = 0.5;

    /** Crea el efecto con su duración. */
    public Electrocutado(){
        super(DURACION);
    }

    @Override
    public String getDescripcion(){
        return "No puede saltar y se mueve al " + (int) (FACTOR_VELOCIDAD * 100) + " % de su velocidad.";
    }

    @Override
    public String getNombre(){
        return "Electrocutado";
    }

    @Override
    public boolean puedeSaltar(){
        return false;
    }

    @Override
    public double getFactorVelocidad(){
        return FACTOR_VELOCIDAD;
    }
}
