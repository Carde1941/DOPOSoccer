package domain;

/** Null Object: el jugador no está bajo ningún efecto. */
public class SinEfecto extends Efecto{

    public SinEfecto(){
        super(0);
    }

    @Override
    public String getNombre(){
        return "Ninguno";
    }

    @Override
    public boolean haTerminado(){
        return false;
    }
}
