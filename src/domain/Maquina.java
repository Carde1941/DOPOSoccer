package domain;

/**
 * Lo que tienen en común las máquinas: cada décima de segundo miran el partido y deciden qué hacer.
 * Cada máquina define su forma de jugar en jugar.
 */
public abstract class Maquina implements Controlador{
    private static final double SEGUNDOS_POR_DECISION = 0.1;
    private static final double TOLERANCIA = 10;
    private static final double DISTANCIA_DETRAS = 50;
    private static final double ALCANCE = 90;
    private double espera;

    /**
     * Espera a que pase el tiempo entre decisiones y, cuando toca, juega.
     *
     * @param propio el jugador que se está controlando
     * @param partido el partido que se está jugando
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
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

    /**
     * Lo que hace la máquina cada vez que le toca decidir.
     *
     * @param propio el jugador que se está controlando
     * @param partido el partido que se está jugando
     */
    protected abstract void jugar(Jugador propio, Partido partido);

    /**
     * Se pone detrás del balón, mirando al arco rival, y lo patea si lo tiene cerca.
     *
     * @param propio el jugador que se está controlando
     * @param partido el partido que se está jugando
     */
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

    /**
     * Camina hasta un punto de la cancha y se queda quieta al llegar.
     *
     * @param propio el jugador que se está controlando
     * @param x el punto al que tiene que llegar
     */
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

    /**
     * Dice si el balón está en la mitad de la cancha del jugador.
     *
     * @param propio el jugador que se está controlando
     * @param partido el partido que se está jugando
     * @return true si está en su mitad
     */
    protected boolean balonEnSuMitad(Jugador propio, Partido partido){
        double mitad = partido.getCancha().getAncho() / 2;
        double balonX = partido.getBalon().getX();
        if (partido.sentidoDeAtaque(propio) == 1){
            return balonX < mitad;
        }
        return balonX > mitad;
    }

    /**
     * El centro de un jugador, de izquierda a derecha.
     *
     * @param jugador el jugador
     * @return la posición del centro
     */
    protected double centroDe(Jugador jugador){
        return jugador.getX() + jugador.getAncho() / 2;
    }

    private boolean tieneCerca(Jugador propio, Balon balon){
        return Math.abs(centroDe(propio) - balon.getX()) < ALCANCE && balon.getY() > propio.getY() - ALCANCE;
    }
}
