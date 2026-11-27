package domain;

/** Tiro de poder que sale recto y horizontal, sin caer mientras dura el poder. */
public class TiroRecto implements Tiro {
    private final double velocidad;

    /**
     * Crea el tiro.
     *
     * @param velocidad qué tan rápido sale
     */
    public TiroRecto(double velocidad){
        this.velocidad = velocidad;
    }

    @Override
    public void impulsar(Balon balon, int sentido){
        balon.aplicarImpulso(sentido * velocidad, 0);
    }

    @Override
    public String getDescripcion(){
        return "Recto y horizontal, sin caer mientras dura el poder.";
    }

    @Override
    public double getFactorGravedad(){
        return 0;
    }
}
