package domain;

/** Aura Azul: el rival sale lanzado hacia su propio arco y no se controla mientras dura. */
public class Empuje extends Efecto{
    private static final double DURACION = 1.5;
    private static final double VELOCIDAD = 450;
    private static final double IMPULSO_ARRIBA = 300;
    private int sentido;

    public Empuje(){
        super(DURACION);
    }

    @Override
    public String getNombre(){
        return "Empuje";
    }

    @Override
    public void alEmpezar(Jugador afectado, Partido partido){
        sentido = partido.sentidoHaciaSuArco(afectado);
        afectado.lanzarArriba(IMPULSO_ARRIBA);
    }

    @Override
    public boolean puedeMoverse(){
        return false;
    }

    @Override
    public double getArrastre(){
        return sentido * VELOCIDAD;
    }
}
