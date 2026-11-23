package domain;

/** Tiro de poder que sale recto y horizontal, sin caer mientras dura el poder. */
public class TiroRecto implements Tiro {
    private final double velocidad;

    public TiroRecto(double velocidad){
        this.velocidad = velocidad;
    }

    @Override
    public void impulsar(Balon balon, int sentido){
        balon.aplicarImpulso(sentido * velocidad, 0);
    }

    @Override
    public double getFactorGravedad(){
        return 0;
    }
}
