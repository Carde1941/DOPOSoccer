package domain;

public class Cancha {
    private static final double ANCHO = 1280;
    private static final double ALTO = 720;
    private static final double SUELO = 640;
    private static final double GRAVEDAD = 1500;
    private static final double ARCO_ANCHO = 90;
    private static final double ARCO_ALTO = 220;
    private static final double GROSOR_TRAVESANO = 10;

    public boolean entroEnArcoIzquierdo(Balon balon){
        return balon.getX() + balon.getRadio() < ARCO_ANCHO && balon.getY() > SUELO - ARCO_ALTO;
    }

    public boolean entroEnArcoDerecho(Balon balon){
        return balon.getX() - balon.getRadio() > ANCHO - ARCO_ANCHO && balon.getY() > SUELO - ARCO_ALTO;
    }

    public void rebotarEnTravesanos(Balon balon){
        double y = SUELO - ARCO_ALTO - GROSOR_TRAVESANO/2;
        balon.rebotarEn(0, y, ARCO_ANCHO, GROSOR_TRAVESANO);
        balon.rebotarEn(ANCHO - ARCO_ANCHO, y, ARCO_ANCHO, GROSOR_TRAVESANO);
    }

    public double getAncho(){
        return ANCHO;
    }

    public double getAlto(){
        return ALTO;
    }

    public double getSuelo(){
        return SUELO;
    }

    public double getGravedad(){
        return GRAVEDAD;
    }

    public double getArcoAlto(){
        return ARCO_ALTO;
    }

    public double getArcoAncho(){
        return ARCO_ANCHO;
    }
}
