package domain;

import java.util.Random;

public class Torpe implements Controlador{
    private static final double SEGUNDOS_POR_DECISION = 0.4;
    private static final int ACCIONES = 6;
    private final Random azar;
    private double espera; 
    

    public Torpe() {
        this(new Random());
    }

    public Torpe(Random azar){
        this.azar = azar;
    }

    @Override 
    public void decidir(Jugador propio, Partido partido, double segundos){
        espera -= segundos;
        if (espera>0){
            return ;
        }
        espera = SEGUNDOS_POR_DECISION;
        switch (azar.nextInt(ACCIONES)) {
            case 0 -> propio.moverIzquierda();
            case 1 -> propio.moverDerecha();
            case 2 -> propio.detenerse();
            case 3 -> propio.saltar();
            case 4 -> propio.activarPoder();
            default -> propio.patear();
        }
    }

    @Override
    public boolean usaTeclado(){
        return false;
    }



}
