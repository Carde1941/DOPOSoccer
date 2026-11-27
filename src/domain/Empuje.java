package domain;

/**
 * El efecto del Aura Azul de Corea: el rival sale volando hacia su propio arco y no se controla
 * mientras dura.
 */
public class Empuje extends Efecto{
    private static final double DURACION = 1.5;
    private static final double VELOCIDAD = 450;
    private static final double IMPULSO_ARRIBA = 300;
    private int sentido;

    /** Crea el efecto con su duración. */
    public Empuje(){
        super(DURACION);
    }

    @Override
    public String getDescripcion(){
        return "Sale lanzado hacia su propio arco y no se controla.";
    }

    @Override
    public String getNombre(){
        return "Empuje";
    }

    /**
     * Lanza al jugador un poco hacia arriba.
     *
     * @param afectado el jugador que recibió el efecto
     * @param partido el partido que se está jugando
     */
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
