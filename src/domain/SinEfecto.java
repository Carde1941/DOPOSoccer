package domain;

/**
 * Cuando el jugador no tiene ningún efecto encima. Así el jugador siempre tiene un efecto y nunca hay
 * que preguntar si es null.
 */
public class SinEfecto extends Efecto{

    /** Crea el efecto vacío. */
    public SinEfecto(){
        super(0);
    }

    @Override
    public String getDescripcion(){
        return "No le pasa nada.";
    }

    @Override
    public String getNombre(){
        return "Ninguno";
    }

    /**
     * Nunca termina: el jugador se queda así hasta que lo golpee un poder.
     *
     * @return siempre false
     */
    @Override
    public boolean haTerminado(){
        return false;
    }
}
