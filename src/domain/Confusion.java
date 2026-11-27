package domain;

/**
 * El efecto del poder de Estados Unidos. Al empezar, el balón se divide en varios iguales; solo uno es
 * el de verdad. Los falsos desaparecen cuando se acaba el efecto.
 */
public class Confusion extends Efecto{
    private static final double DURACION = 2.5;
    private static final int BALONES_FALSOS = 7;

    /** Crea el efecto con su duración. */
    public Confusion(){
        super(DURACION);
    }

    @Override
    public String getDescripcion(){
        return "El balón se divide en " + (BALONES_FALSOS + 1) + " iguales; " + BALONES_FALSOS + " son falsos.";
    }

    @Override
    public String getNombre(){
        return "Confusión";
    }

    /**
     * Divide el balón en varios balones falsos.
     *
     * @param afectado el jugador que recibió el efecto
     * @param partido el partido que se está jugando
     */
    @Override
    public void alEmpezar(Jugador afectado, Partido partido){
        partido.dividirBalon(BALONES_FALSOS, DURACION);
    }
}
