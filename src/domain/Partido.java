package domain;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Un partido entre dos jugadores. En cada paso mueve todo, revisa los choques, las patadas y los
 * poderes, cuenta los goles y decide si ya terminó.
 */
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

    /**
     * Crea el partido con los jugadores en sus puestos y el balón en el centro.
     *
     * @param jugadorIzquierdo el jugador que arranca a la izquierda
     * @param jugadorDerecho el jugador que arranca a la derecha
     * @param tipoPartido cómo se decide cuándo termina el partido
     */
    public Partido(Jugador jugadorIzquierdo, Jugador jugadorDerecho, TipoPartido tipoPartido){
        this.jugadorIzquierdo = jugadorIzquierdo;
        this.jugadorDerecho = jugadorDerecho;
        this.tipoPartido = tipoPartido;
        cancha = new Cancha();
        balon = new Balon(cancha.getAncho() / 2, ALTURA_SAQUE);
        colocarJugadores();
    }

    /**
     * Avanza el partido un paso, en este orden: el tiempo, las decisiones de cada jugador, el balón,
     * los jugadores, los choques, los poderes, las patadas y los goles. Si está pausado o ya terminó,
     * no hace nada.
     *
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
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

    /** Pausa el partido. */
    public void pausar(){
        pausado = true;
    }

    /** Sigue el partido. */
    public void reanudar(){
        pausado = false;
    }

    /**
     * Dice si el partido terminó.
     *
     * @return true si terminó
     */
    public boolean haTerminado(){
        return terminado;
    }

    /**
     * Dice si está en pausa.
     *
     * @return true si está pausado
     */
    public boolean estaPausado(){
        return pausado;
    }

    /**
     * El tipo de partido.
     *
     * @return el tipo de partido
     */
    public TipoPartido getTipoPartido(){
        return tipoPartido;
    }

    /**
     * La cancha.
     *
     * @return la cancha
     */
    public Cancha getCancha(){
        return cancha;
    }

    /**
     * El balón de verdad.
     *
     * @return el balón
     */
    public Balon getBalon(){
        return balon;
    }

    /**
     * El jugador de la izquierda.
     *
     * @return el jugador
     */
    public Jugador getJugadorIzquierdo(){
        return jugadorIzquierdo;
    }

    /**
     * El jugador de la derecha.
     *
     * @return el jugador
     */
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

    /**
     * El jugador que tiene más goles.
     *
     * @return el jugador, o null si van empatados
     */
    public Jugador getGanador(){
        if(jugadorDerecho.getGoles() > jugadorIzquierdo.getGoles()){
            return jugadorDerecho; 
        } else if(jugadorDerecho.getGoles() < jugadorIzquierdo.getGoles()){
            return jugadorIzquierdo;
        } else {
            return null;
        }
    }

    /** Termina el partido y reparte los puntos. Si ya había terminado, no hace nada. */
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

    /**
     * Hacia dónde ataca un jugador.
     *
     * @param jugador el jugador
     * @return 1 si ataca hacia la derecha y -1 si ataca hacia la izquierda
     */
    public int sentidoDeAtaque(Jugador jugador){
        return (jugador == jugadorIzquierdo) ? 1 : -1;
    }

    /**
     * Hacia dónde queda el arco que defiende un jugador.
     *
     * @param jugador el jugador
     * @return 1 si queda a la derecha y -1 si queda a la izquierda
     */
    public int sentidoHaciaSuArco(Jugador jugador){
        return -sentidoDeAtaque(jugador);
    }

    /**
     * El rival de un jugador.
     *
     * @param jugador el jugador
     * @return el otro jugador
     */
    public Jugador rivalDe(Jugador jugador){
        return (jugador == jugadorIzquierdo) ? jugadorDerecho : jugadorIzquierdo;
    }

    /**
     * Saca balones falsos desde donde está el balón, abiertos en abanico hacia arriba. Duran un tiempo
     * y desaparecen.
     *
     * @param cantidad cuántos balones falsos
     * @param duracion cuántos segundos dura
     */
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

    /**
     * Los balones falsos que hay en la cancha.
     *
     * @return la lista; está vacía si no hay
     */
    public List<Balon> getBalonesFalsos(){
        return balonesFalsos;
    }
}
