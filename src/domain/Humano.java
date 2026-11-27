package domain;

/** Un jugador manejado por una persona. No decide nada solo: las teclas llegan desde la pantalla. */
public class Humano implements Controlador{
    @Override 
    public void decidir(Jugador propio, Partido partido, double segundos){
    }

    @Override
    public boolean usaTeclado(){
        return true;
    }
}
