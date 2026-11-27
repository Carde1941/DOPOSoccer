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
    private final Perfil[] perfiles = {new Perfil(), new Perfil()};
    private boolean puntosEntregados;

    public List<String> getTiposPartido() {
        return Catalogo.getTiposPartido();
    }

    public List<Integer> getCantidades(String tipo) throws DOPOSoccerException {
        return Catalogo.getCantidades(tipo);
    }

    public String describirCantidad(String tipo, int cantidad) throws DOPOSoccerException {
        return Catalogo.describirCantidad(tipo, cantidad);
    }

    public void nuevoPartido(String tipo, int cantidad) throws DOPOSoccerException {
        TipoPartido tipoPartido = Catalogo.crearTipoPartido(tipo, cantidad);
        partido = new Partido(new Jugador(perfiles[IZQUIERDO].getEstadisticas()),
            new Jugador(perfiles[DERECHO].getEstadisticas()), tipoPartido);
        puntosEntregados = false;
    }

    public void actualizar(double segundos) {
        partido.actualizar(segundos);
        entregarPuntos();
    }

    public void pausar() {
        partido.pausar();
    }

    public void reanudar() {
        partido.reanudar();
    }

    public void terminar() {
        partido.terminar();
        entregarPuntos();
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

    public void activarPoder(int jugador) {
        jugador(jugador).activarPoder();
    }

    public List<String> getPersonajes() {
        return Catalogo.getPersonajes();
    }

    public void elegirPersonaje(int jugador, String personaje) throws DOPOSoccerException {
        jugador(jugador).setPersonaje(Catalogo.crearPersonaje(personaje));
    }

    public String getNombrePoder(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).getNombrePoder();
    }

    public String getDescripcionTiro(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).crearTiroPoder().getDescripcion();
    }

    public String getDescripcionEfecto(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).crearEfecto().getDescripcion();
    }

    public double getDanoPoder(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).getDanoTiro();
    }

    public double getDuracionEfecto(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).crearEfecto().getDuracion();
    }

    public String getPersonaje(int jugador) {
        return jugador(jugador).getPersonaje().getNombre();
    }

    public boolean tieneAura(int jugador) {
        return jugador(jugador).tieneAura();
    }

    public String getEfecto(int jugador) {
        return jugador(jugador).getEfecto().getNombre();
    }

    public boolean balonTienePoder() {
        return partido.getBalon().tienePoder();
    }

    /** El personaje que lanzó el tiro de poder que lleva el balón, o null si no lleva ninguno. */
    public String getPersonajeDelPoder() {
        if (!balonTienePoder()) {
            return null;
        }
        return partido.getBalon().getPoder().getLanzador().getPersonaje().getNombre();
    }

    public int getCantidadBalonesFalsos() {
        return partido.getBalonesFalsos().size();
    }

    public double getXBalonFalso(int indice) {
        return partido.getBalonesFalsos().get(indice).getX();
    }

    public double getYBalonFalso(int indice) {
        return partido.getBalonesFalsos().get(indice).getY();
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

    public double getVxBalon() {
        return partido.getBalon().getVx();
    }

    public double getVyBalon() {
        return partido.getBalon().getVy();
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
            puntosEntregados = partido.haTerminado();
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

    public boolean usaTeclado(int jugador){
        return jugador(jugador).usaTeclado();
    }

    public List<String> getEstadisticas(){
        return perfiles[IZQUIERDO].getEstadisticas().getNombres();
    }

    public int getNivel(int jugador, String estadistica) throws DOPOSoccerException{
        return perfiles[jugador].getEstadisticas().getNivel(estadistica);
    }

    public boolean estaAlMaximo(int jugador, String estadistica) throws DOPOSoccerException{
        return perfiles[jugador].getEstadisticas().estaAlMaximo(estadistica);
    }

    public int getPrecioMejora(int jugador, String estadistica) throws DOPOSoccerException{
        return perfiles[jugador].precioMejora(estadistica);
    }

    public void mejorar(int jugador, String estadistica) throws DOPOSoccerException{
        perfiles[jugador].mejorar(estadistica);
    }

    public int getPuntosAcumulados(int jugador){
        return perfiles[jugador].getPuntos();
    }

    public void guardarProgreso(File archivo) throws DOPOSoccerException{
        try(FileOutputStream escritura = new FileOutputStream(archivo);
            ObjectOutputStream salida = new ObjectOutputStream(escritura)){
            salida.writeObject(perfiles);
        } catch (IOException e){
            throw new DOPOSoccerException(DOPOSoccerException.ERROR_AL_GUARDAR_PROGRESO, e);
        }
    }

    public void abrirProgreso(File archivo) throws DOPOSoccerException{
        try (FileInputStream lectura = new FileInputStream(archivo);
            ObjectInputStream entrada = new ObjectInputStream(lectura)){
            Object leido = entrada.readObject();
            if (!(leido instanceof Perfil[]) || ((Perfil[]) leido).length != perfiles.length){
                throw new DOPOSoccerException(DOPOSoccerException.ARCHIVO_NO_ES_PROGRESO);
            }
            Perfil[] leidos = (Perfil[]) leido;
            perfiles[IZQUIERDO] = leidos[IZQUIERDO];
            perfiles[DERECHO] = leidos[DERECHO];
        } catch(IOException | ClassNotFoundException e){
            throw new DOPOSoccerException(DOPOSoccerException.ERROR_AL_ABRIR_PROGRESO, e);
        }
    }

    private void entregarPuntos(){
        if (partido.haTerminado() && !puntosEntregados){
            perfiles[IZQUIERDO].sumarPuntos(getPuntos(IZQUIERDO));
            perfiles[DERECHO].sumarPuntos(getPuntos(DERECHO));
            puntosEntregados = true;
        }
    }
}
