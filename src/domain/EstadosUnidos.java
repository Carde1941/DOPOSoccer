package domain;

/**
 * Estados Unidos. Su poder, la Ilusión, sale en parábola y llena la cancha de balones falsos para
 * confundir.
 */
public class EstadosUnidos extends Personaje{
    private static final double DANO_TIRO = 30;
    private static final double TIRO_VX = 850;
    private static final double TIRO_VY = 550;

    @Override
    public String getNombre(){
        return "Estados Unidos";
    }

    @Override
    public double getDanoTiro(){
        return DANO_TIRO;
    }

    @Override
    public String getNombrePoder(){
        return "Ilusión";
    }

    @Override
    public Tiro crearTiroPoder(){
        return new TiroParabolico(TIRO_VX, TIRO_VY);
    }

    @Override
    public Efecto crearEfecto(){
        return new Confusion();
    }
}
