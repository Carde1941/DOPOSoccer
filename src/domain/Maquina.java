package domain;

/** Template Method: cada cierto tiempo la máquina mira el partido y juega según su perfil. */
public abstract class Maquina implements Controlador{
    private static final double SEGUNDOS_POR_DECISION = 0.1;
    private static final double TOLERANCIA = 10;
    private static final double DISTANCIA_DETRAS = 50;
    private static final double ALCANCE = 90;
    private double espera;

    @Override
    public void decidir(Jugador propio, Partido partido, double segundos){
        espera -= segundos;
        if (espera>0){
            return;
        }
        espera = SEGUNDOS_POR_DECISION;
        jugar(propio, partido);
    }

    @Override
    public boolean usaTeclado(){
        return false;
    }

    protected abstract void jugar(Jugador propio, Partido partido);

    /** Se pone detrás del balón, mirando al arco rival, y lo patea o lo cabecea si lo tiene cerca. */
    protected void atacar(Jugador propio, Partido partido){
        Balon balon = partido.getBalon();
        irHacia(propio, balon.getX() - partido.sentidoDeAtaque(propio) * DISTANCIA_DETRAS);
        if (tieneCerca(propio, balon)){
            if (balon.getY() < propio.getY()){
                propio.saltar();
            } else{
                propio.patear();
            }
        }
    }

    protected void irHacia(Jugador propio, double x){
        double centro = centroDe(propio);
        if (x < centro - TOLERANCIA){
            propio.moverIzquierda();
        } else if (x > centro + TOLERANCIA){
            propio.moverDerecha();
        } else{
            propio.detenerse();
        }
    }

    protected boolean balonEnSuMitad(Jugador propio, Partido partido){
        double mitad = partido.getCancha().getAncho() / 2;
        double balonX = partido.getBalon().getX();
        if (partido.sentidoDeAtaque(propio) == 1){
            return balonX < mitad;
        }
        return balonX > mitad;
    }

    protected double centroDe(Jugador jugador){
        return jugador.getX() + jugador.getAncho() / 2;
    }

    private boolean tieneCerca(Jugador propio, Balon balon){
        return Math.abs(centroDe(propio) - balon.getX()) < ALCANCE && balon.getY() > propio.getY() - ALCANCE;
    }
}
