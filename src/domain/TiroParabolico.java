package domain;

public class TiroParabolico implements Tiro {
    private final double adelante;
    private final double arriba;

    public TiroParabolico(double adelante, double arriba){
        this.adelante = adelante;
        this.arriba = arriba;
    }

    @Override
    public void impulsar(Balon balon, int sentido){
        balon.aplicarImpulso(sentido * adelante, -arriba);
    } 
}
