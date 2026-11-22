package domain;

public class Humano implements Controlador{
    @Override 
    public void decidir(Jugador propio, Partido partido, double segundos){
    }

    @Override
    public boolean esHumano(){
        return true;
    }
}
