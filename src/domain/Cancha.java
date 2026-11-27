package domain;
import java.io.*;

/**
 * La cancha: su tamaño, el suelo, la gravedad, los arcos y cómo se frena el balón en el piso y en el
 * aire.
 */
public class Cancha implements Serializable{
    private static final double ANCHO = 1280;
    private static final double ALTO = 720;
    private static final double SUELO = 640;
    private static final double GRAVEDAD = 1500;
    private static final double ARCO_ANCHO = 90;
    private static final double ARCO_ALTO = 220;
    private static final double GROSOR_TRAVESANO = 10;
    private static final double DESACELERACION_CESPED = 400;

    private final Friccion friccionSuelo;
    private final Friccion friccionAire;

    /** Crea la cancha normal: el césped frena el balón y el aire no lo frena. */
    public Cancha(){
        this(new FriccionConstante(DESACELERACION_CESPED), new SinFriccion());
    }

    /**
     * Crea una cancha escogiendo cómo se frena el balón.
     *
     * @param friccionSuelo cómo se frena el balón cuando va por el suelo
     * @param friccionAire cómo se frena el balón cuando va por el aire
     */
    public Cancha(Friccion friccionSuelo, Friccion friccionAire){
        this.friccionSuelo = friccionSuelo;
        this.friccionAire = friccionAire;
    }

    /**
     * Dice si el balón entró completo al arco de la izquierda.
     *
     * @param balon el balón
     * @return true si es gol en ese arco
     */
    public boolean entroEnArcoIzquierdo(Balon balon){
        return balon.getX() + balon.getRadio() < ARCO_ANCHO && balon.getY() > SUELO - ARCO_ALTO;
    }

    /**
     * Dice si el balón entró completo al arco de la derecha.
     *
     * @param balon el balón
     * @return true si es gol en ese arco
     */
    public boolean entroEnArcoDerecho(Balon balon){
        return balon.getX() - balon.getRadio() > ANCHO - ARCO_ANCHO && balon.getY() > SUELO - ARCO_ALTO;
    }

    /**
     * Hace rebotar el balón si pega en el travesaño de alguno de los dos arcos.
     *
     * @param balon el balón
     */
    public void rebotarEnTravesanos(Balon balon){
        double y = SUELO - ARCO_ALTO - GROSOR_TRAVESANO/2;
        balon.rebotarEn(0, y, ARCO_ANCHO, GROSOR_TRAVESANO);
        balon.rebotarEn(ANCHO - ARCO_ANCHO, y, ARCO_ANCHO, GROSOR_TRAVESANO);
    }

    /**
     * El ancho de la cancha.
     *
     * @return el ancho en píxeles
     */
    public double getAncho(){
        return ANCHO;
    }

    /**
     * El alto de la cancha.
     *
     * @return el alto en píxeles
     */
    public double getAlto(){
        return ALTO;
    }

    /**
     * A qué altura está el suelo.
     *
     * @return la posición del suelo, de arriba hacia abajo
     */
    public double getSuelo(){
        return SUELO;
    }

    /**
     * Qué tan fuerte jala la gravedad hacia abajo.
     *
     * @return la gravedad
     */
    public double getGravedad(){
        return GRAVEDAD;
    }

    /**
     * El alto de los arcos.
     *
     * @return el alto de cada arco
     */
    public double getArcoAlto(){
        return ARCO_ALTO;
    }

    /**
     * El ancho de los arcos.
     *
     * @return el ancho de cada arco
     */
    public double getArcoAncho(){
        return ARCO_ANCHO;
    }

    /**
     * Cómo se frena el balón en el suelo.
     *
     * @return la fricción del suelo
     */
    public Friccion getFriccionSuelo(){
        return friccionSuelo;
    }

    /**
     * Cómo se frena el balón en el aire.
     *
     * @return la fricción del aire
     */
    public Friccion getFriccionAire(){
        return friccionAire;
    }
}
