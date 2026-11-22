package domain;

public class Corea extends Personaje{
    private static final double DANO_TIRO = 40;
    private static final double VELOCIDAD_TIRO = 1300;

    @Override
    public String getNombre(){
        return "Corea del Sur";
    }

    @Override
    public double getDanoTiro(){
        return DANO_TIRO;
    }

    @Override
    public Tiro crearTiroPoder(){
        return new TiroRecto(VELOCIDAD_TIRO);
    }

    @Override
    public Efecto crearEfecto(){
        return new Empuje();
    }
}
