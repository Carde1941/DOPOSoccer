package domain;

/** Ilusión: el balón se divide en 8 iguales; los 7 falsos desaparecen cuando termina el efecto. */
public class Confusion extends Efecto{
    private static final double DURACION = 2.5;
    private static final int BALONES_FALSOS = 7;

    public Confusion(){
        super(DURACION);
    }

    @Override
    public String getNombre(){
        return "Confusión";
    }

    @Override
    public void alEmpezar(Jugador afectado, Partido partido){
        partido.dividirBalon(BALONES_FALSOS, DURACION);
    }
}
