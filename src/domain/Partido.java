package domain;

public class Partido {
    private static final double ALTURA_SAQUE = 100.0;
    private static final int PUNTOS_GANADOR = 100;
    private static final int PUNTOS_PERDEDOR = 25;
    private Cancha cancha;
    private Balon balon;
    private Jugador jugadorIzquierdo, jugadorDerecho;
    private TipoPartido tipoPartido;
    private boolean terminado, pausado; 

    public Partido(Jugador jugadorIzquierdo, Jugador jugadorDerecho, TipoPartido tipoPartido){
        this.jugadorIzquierdo = jugadorIzquierdo;
        this.jugadorDerecho = jugadorDerecho;
        this.tipoPartido = tipoPartido;
        cancha = new Cancha();
        balon = new Balon(cancha.getAncho() / 2, ALTURA_SAQUE);
        colocarJugadores();
    }

    public void actualizar(double segundos){
        if(terminado || pausado){
            return;
        }
        tipoPartido.actualizar(segundos);
        balon.actualizar(segundos, cancha);
        jugadorIzquierdo.actualizar(segundos);
        jugadorDerecho.actualizar(segundos);
        jugadorIzquierdo.moverse(segundos, cancha);
        jugadorDerecho.moverse(segundos, cancha);
        balon.chocarCon(jugadorIzquierdo);
        balon.chocarCon(jugadorDerecho);
        verificarGol();
        if(tipoPartido.haTerminado(jugadorIzquierdo.getGoles(), jugadorDerecho.getGoles())){
            finalizar();
        }
    }

    public void pausar(){
        pausado = true;
    }

    public void reanudar(){
        pausado = false;
    }

    public boolean haTerminado(){
        return terminado;
    }

    public boolean estaPausado(){
        return pausado;
    }

    public TipoPartido getTipoPartido(){
        return tipoPartido;
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
        colocarJugadores();
    }

    private void finalizar(){
        terminado = true;
        Jugador ganador = getGanador();
        if(ganador == null){
            jugadorDerecho.sumarPuntos(PUNTOS_PERDEDOR);
            jugadorIzquierdo.sumarPuntos(PUNTOS_PERDEDOR);
        } else{
            Jugador perdedor = (ganador == jugadorIzquierdo) ? jugadorDerecho : jugadorIzquierdo;
            ganador.sumarPuntos(PUNTOS_GANADOR);
            perdedor.sumarPuntos(PUNTOS_PERDEDOR);
        }
    }

    public Jugador getGanador(){
        if(jugadorDerecho.getGoles() > jugadorIzquierdo.getGoles()){
            return jugadorDerecho; 
        } else if(jugadorDerecho.getGoles() < jugadorIzquierdo.getGoles()){
            return jugadorIzquierdo;
        } else {
            return null;
        }
    }

    public void terminar(){
        if(!terminado){
            finalizar();
        }
    }

    private void colocarJugadores() {
    double y = cancha.getSuelo() - jugadorIzquierdo.getAlto();
    jugadorIzquierdo.colocar(cancha.getAncho() * 0.25 - jugadorIzquierdo.getAncho() / 2, y);
    jugadorDerecho.colocar(cancha.getAncho() * 0.75 - jugadorDerecho.getAncho() / 2, y);
    }
}
