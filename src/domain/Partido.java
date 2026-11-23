package domain;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Partido implements Serializable{
    private static final double ALTURA_SAQUE = 100.0;
    private static final int PUNTOS_GANADOR = 100;
    private static final int PUNTOS_PERDEDOR = 25;
    private static final double ALCANCE_PIE = 60.0;
    private static final double ALTO_PIE = 50.0;
    private static final double VELOCIDAD_FALSOS = 700.0;
    private Cancha cancha;
    private Balon balon;
    private List<Balon> balonesFalsos = new ArrayList<>();
    private double tiempoFalsos;
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
        jugadorIzquierdo.decidir(this, segundos);
        jugadorDerecho.decidir(this, segundos);
        balon.actualizar(segundos, cancha);
        actualizarBalonesFalsos(segundos);
        jugadorIzquierdo.actualizar(segundos);
        jugadorDerecho.actualizar(segundos);
        jugadorIzquierdo.moverse(segundos, cancha);
        jugadorDerecho.moverse(segundos, cancha);
        jugadorIzquierdo.chocarCon(jugadorDerecho, cancha);
        resolverPoder(jugadorIzquierdo, jugadorDerecho);
        resolverPoder(jugadorDerecho, jugadorIzquierdo);
        balon.chocarCon(jugadorIzquierdo);
        balon.chocarCon(jugadorDerecho);
        resolverPatada(jugadorIzquierdo, jugadorDerecho, 1);
        resolverPatada(jugadorDerecho, jugadorIzquierdo, -1);
        cancha.rebotarEnTravesanos(balon);
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
        quitarBalonesFalsos();
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

    private void resolverPatada(Jugador pateador, Jugador rival, int sentido){
        if(!pateador.consumirPatada()){
            return;
        }
        double pieX;
        if(sentido == 1){
            pieX = pateador.getX() + pateador.getAncho();
        } else {
            pieX = pateador.getX() - ALCANCE_PIE;
        }
        double pieY = pateador.getY() + pateador.getAlto() - ALTO_PIE;
        if(balon.tocaZona(pieX, pieY, ALCANCE_PIE, ALTO_PIE)){
            if(pateador.tieneAura()){
                pateador.lanzarPoder(balon, sentido);
            } else {
                pateador.patearBalon(balon, sentido);
            }
        } else if(rival.ocupaZona(pieX, pieY, ALCANCE_PIE, ALTO_PIE)){
            rival.recibirDano(pateador.danoPatada());
        }
    }

    private void resolverPoder(Jugador jugador, Jugador rival){
        if(!balon.toca(jugador)){
            return;
        }
        if(balon.tienePoder() && balon.getPoder().esDe(rival)){
            recibirTiroPoder(jugador);
        } else if(jugador.tieneAura()){
            jugador.lanzarPoder(balon, sentidoDeAtaque(jugador));
        }
    }

    private void recibirTiroPoder(Jugador afectado){
        TiroPoder poder = balon.getPoder();
        balon.quitarPoder();
        afectado.recibirDano(poder.getDano());
        quitarBalonesFalsos();
        afectado.aplicarEfecto(poder.getEfecto(), this);
    }

    public int sentidoDeAtaque(Jugador jugador){
        return (jugador == jugadorIzquierdo) ? 1 : -1;
    }

    public int sentidoHaciaSuArco(Jugador jugador){
        return -sentidoDeAtaque(jugador);
    }

    public Jugador rivalDe(Jugador jugador){
        return (jugador == jugadorIzquierdo) ? jugadorDerecho : jugadorIzquierdo;
    }

    public void dividirBalon(int cantidad, double duracion){
        quitarBalonesFalsos();
        for(int i = 0; i < cantidad; i++){
            Balon falso = new Balon(balon.getX(), balon.getY());
            double angulo = Math.PI * (i + 1) / (cantidad + 1);
            falso.aplicarImpulso(Math.cos(angulo) * VELOCIDAD_FALSOS, -Math.sin(angulo) * VELOCIDAD_FALSOS);
            balonesFalsos.add(falso);
        }
        tiempoFalsos = duracion;
    }

    private void actualizarBalonesFalsos(double segundos){
        if(balonesFalsos.isEmpty()){
            return;
        }
        tiempoFalsos -= segundos;
        if(tiempoFalsos <= 0){
            quitarBalonesFalsos();
            return;
        }
        for(Balon falso : balonesFalsos){
            falso.actualizar(segundos, cancha);
            cancha.rebotarEnTravesanos(falso);
        }
    }

    private void quitarBalonesFalsos(){
        balonesFalsos.clear();
        tiempoFalsos = 0;
    }

    public List<Balon> getBalonesFalsos(){
        return balonesFalsos;
    }
}
