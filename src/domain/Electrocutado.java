package domain;

/** Rayo: el rival no puede saltar y se mueve a la mitad de su velocidad. */
public class Electrocutado extends Efecto{
    private static final double DURACION = 3;
    private static final double FACTOR_VELOCIDAD = 0.5;

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
