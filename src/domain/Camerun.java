package domain;

public class Camerun extends Personaje{
    private static final double DANO_TIRO = 60;
    private static final double VELOCIDAD_TIRO = 950;

    @Override
    public String getNombre(){
        return "Camerún";
    }

    @Override
    public double getDanoTiro(){
        return DANO_TIRO;
    }

    @Override
    public String getNombrePoder(){
        return "Rayo";
    }

    @Override
    public Tiro crearTiroPoder(){
        return new TiroRecto(VELOCIDAD_TIRO);
    }

    @Override
    public Efecto crearEfecto(){
        return new Electrocutado();
    }
}
