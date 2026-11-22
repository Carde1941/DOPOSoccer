package domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.util.List;

public class DOPOSoccer {
    public static final int IZQUIERDO = 0;
    public static final int DERECHO = 1;
    public static final int EMPATE = -1;

    private Partido partido;

    public List<String> getTiposPartido() {
        return Catalogo.getTiposPartido();
    }

    public void nuevoPartido(String tipo, int cantidad) throws DOPOSoccerException {
        TipoPartido tipoPartido = Catalogo.crearTipoPartido(tipo, cantidad);
        partido = new Partido(new Jugador(new Estadisticas()), new Jugador(new Estadisticas()), tipoPartido);
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

    public void guardar(File archivo) throws DOPOSoccerException{
        if (partido == null){
            throw new DOPOSoccerException(DOPOSoccerException.NO_HAY_PARTIDO);
        }
        try(FileOutputStream escritura = new FileOutputStream(archivo);
            ObjectOutputStream salida = new ObjectOutputStream(escritura)){
            salida.writeObject(partido);
        } catch (IOException e){
        throw new DOPOSoccerException(DOPOSoccerException.ERROR_AL_GUARDAR, e);
        }
    }

    public void abrir(File archivo) throws DOPOSoccerException{
        try (FileInputStream lectura = new FileInputStream(archivo);
            ObjectInputStream entrada = new ObjectInputStream(lectura)){
                Object leido = entrada.readObject();
            if (!(leido instanceof Partido)){
                throw new DOPOSoccerException(DOPOSoccerException.ARCHIVO_NO_ES_PARTIDA);
            }
            partido = (Partido) leido;
        } catch(IOException | ClassNotFoundException e){
            throw new DOPOSoccerException(DOPOSoccerException.ERROR_AL_ABRIR, e);
        }

    }

    public List<String> getMaquinas(){
        return Catalogo.getMaquinas();
    }

    public void ponerMaquina(int jugador, String perfil) throws DOPOSoccerException{
        Controlador maquina = Catalogo.crearMaquina(perfil);   
        Jugador elegido = jugador(jugador);                   
        elegido.setControlador(maquina);        
    }

    public boolean esHumano(int jugador){
        return jugador(jugador).esHumano();
    }
}