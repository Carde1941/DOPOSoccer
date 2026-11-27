package domain;

/** Tornado: el rival sale lanzado al aire y queda sin poder moverse, saltar ni patear. */
public class Inconsciente extends Efecto{
    private static final double DURACION = 3;
    private static final double IMPULSO_ARRIBA = 900;

    public Inconsciente(){
        super(DURACION);
    }

    @Override
    public String getDescripcion(){
        return "Sale lanzado al aire y no puede moverse, saltar ni patear.";
    }

    @Override
    public String getNombre(){
        return "Inconsciente";
    }

    @Override
    public void alEmpezar(Jugador afectado, Partido partido){
        afectado.lanzarArriba(IMPULSO_ARRIBA);
    }

    @Override
    public boolean puedeMoverse(){
        return false;
    }

    @Override
    public boolean puedeSaltar(){
        return false;
    }

    @Override
    public boolean puedePatear(){
        return false;
    }
}
