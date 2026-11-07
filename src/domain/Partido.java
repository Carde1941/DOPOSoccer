package domain;

public class Partido {
    private static final double ALTURA_SAQUE = 100.0;
    private Cancha cancha;
    private Balon balon;
    private Jugador jugadorIzquierdo, jugadorDerecho;

    public Partido(Jugador jugadorIzquierdo, Jugador jugadorDerecho){
        this.jugadorIzquierdo = jugadorIzquierdo;
        this.jugadorDerecho = jugadorDerecho;
        cancha = new Cancha();
        balon = new Balon(cancha.getAncho() / 2, ALTURA_SAQUE);
    }

    public void actualizar(double segundos){
        balon.actualizar(segundos, cancha);
        jugadorIzquierdo.actualizar(segundos);
        jugadorDerecho.actualizar(segundos);
        verificarGol();
    }

    public Cancha getCancha(){
        return cancha;
    }

    public Balon getBalon(){
        return balon;
    }

    public Jugador getJugadorIzquierdo(){
        return jugadorIzquierdo;
    }

    public Jugador getJugadorDerecho(){
        return jugadorDerecho;
    }

    private void verificarGol(){
        if(cancha.entroEnArcoIzquierdo(balon)){
            jugadorDerecho.anotarGol();
            jugadorIzquierdo.recibirGol();
            reiniciarSaque();
        }

        if(cancha.entroEnArcoDerecho(balon)){
            jugadorIzquierdo.anotarGol();
            jugadorDerecho.recibirGol();
            reiniciarSaque();
        }
    }

    private void reiniciarSaque(){
        balon.reiniciar(cancha.getAncho() / 2, ALTURA_SAQUE);
    }
}
