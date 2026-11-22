package domain;

public class Nigeria extends Personaje{
    private static final double DANO_TIRO = 40;
    private static final double TIRO_VX = 900;
    private static final double TIRO_VY = 250;

    @Override
    public String getNombre(){
        return "Nigeria";
    }

    @Override
    public double getDanoTiro(){
        return DANO_TIRO;
    }

    @Override
    public Tiro crearTiroPoder(){
        return new TiroParabolico(TIRO_VX, TIRO_VY);
    }

    @Override
    public Efecto crearEfecto(){
        return new Inconsciente();
    }
}
