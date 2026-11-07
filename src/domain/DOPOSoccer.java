package domain;

public class DOPOSoccer {
    public static final int IZQUIERDO = 0;
    public static final int DERECHO = 1;
    public static final int EMPATE = -1;

    private Partido partido;

    public void nuevoPartidoPorGoles(int goles) throws DOPOSoccerException {
        partido = new Partido(new Jugador(new Estadisticas()), new Jugador(new Estadisticas()), new PorGoles(goles));
    }

    public void nuevoPartidoPorTiempo(int minutos) throws DOPOSoccerException {
        partido = new Partido(new Jugador(new Estadisticas()), new Jugador(new Estadisticas()), new PorTiempo(minutos));
    }

    public void actualizar(double segundos) {
        partido.actualizar(segundos);
    }

    public void pausar() {
        partido.pausar();
    }

    public void reanudar() {
        partido.reanudar();
    }

    public void terminar() {
        partido.terminar();
    }

    public boolean estaPausado() {
        return partido.estaPausado();
    }

    public boolean haTerminado() {
        return partido.haTerminado();
    }

    public void moverIzquierda(int jugador) {
        jugador(jugador).moverIzquierda();
    }

    public void moverDerecha(int jugador) {
        jugador(jugador).moverDerecha();
    }

    public void detenerse(int jugador) {
        jugador(jugador).detenerse();
    }

    public void saltar(int jugador) {
        jugador(jugador).saltar();
    }

    public void patear(int jugador) {
        jugador(jugador).patear();
    }


    public double getAnchoCancha() {
        return partido.getCancha().getAncho();
    }

    public double getAltoCancha() {
        return partido.getCancha().getAlto();
    }

    public double getSuelo() {
        return partido.getCancha().getSuelo();
    }

    public double getAnchoArco() {
        return partido.getCancha().getArcoAncho();
    }

    public double getAltoArco() {
        return partido.getCancha().getArcoAlto();
    }

    public double getXBalon() {
        return partido.getBalon().getX();
    }

    public double getYBalon() {
        return partido.getBalon().getY();
    }

    public double getRadioBalon() {
        return partido.getBalon().getRadio();
    }


    public double getXJugador(int jugador) {
        return jugador(jugador).getX();
    }

    public double getYJugador(int jugador) {
        return jugador(jugador).getY();
    }

    public double getAnchoJugador(int jugador) {
        return jugador(jugador).getAncho();
    }

    public double getAltoJugador(int jugador) {
        return jugador(jugador).getAlto();
    }

    public double getVida(int jugador) {
        return jugador(jugador).getVida();
    }

    public double getVidaMaxima(int jugador) {
        return jugador(jugador).getEstadisticas().vidaMaxima();
    }

    public double getProgresoPoder(int jugador) {
        return jugador(jugador).getBarra().getProgreso();
    }

    public boolean estaAturdido(int jugador) {
        return jugador(jugador).estaAturdido();
    }

    public boolean estaPateando(int jugador) {
        return jugador(jugador).estaPateando();
    }

    public int getGoles(int jugador) {
        return jugador(jugador).getGoles();
    }

    public int getPuntos(int jugador) {
        return jugador(jugador).getPuntos();
    }

    public boolean esPorTiempo() {
        return partido.getTipoPartido() instanceof PorTiempo;
    }

    public double getSegundosRestantes() {
        if (esPorTiempo()) {
            return ((PorTiempo) partido.getTipoPartido()).getSegundosRestantes();
        }
        return 0;
    }

    public String getInformacion(){
        return partido.getTipoPartido().getInformacion(getGoles(IZQUIERDO), getGoles(DERECHO));
    }

    public int getGanador() {
        Jugador ganador = partido.getGanador();
        if (ganador == null) {
            return EMPATE;
        }
        if (ganador == partido.getJugadorIzquierdo()) {
            return IZQUIERDO;
        }
        return DERECHO;
    }

    private Jugador jugador(int jugador) {
        if (jugador == IZQUIERDO) {
            return partido.getJugadorIzquierdo();
        }
        return partido.getJugadorDerecho();
    }
}