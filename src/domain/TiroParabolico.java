package domain;

/**
 * Tiro que sale hacia adelante y hacia arriba, y después cae con la gravedad. Es la patada normal y el
 * poder de algunos personajes.
 */
public class TiroParabolico implements Tiro {
    private final double adelante;
    private final double arriba;

    /**
     * Crea el tiro.
     *
     * @param adelante qué tan rápido sale hacia adelante
     * @param arriba qué tan rápido sale hacia arriba
     */
    public TiroParabolico(double adelante, double arriba){
        this.adelante = adelante;
        this.arriba = arriba;
    }

    @Override
    public void impulsar(Balon balon, int sentido){
        balon.aplicarImpulso(sentido * adelante, -arriba);
    }

    @Override
    public String getDescripcion(){
        return "En parábola: sube y cae con la gravedad.";
    }
}
