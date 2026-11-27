package domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.util.List;

/**
 * La puerta de entrada al juego. La pantalla solo habla con esta clase: le pide crear partidos, le
 * pasa las teclas y le pregunta qué dibujar. A los jugadores se les nombra con IZQUIERDO y DERECHO.
 */
public class DOPOSoccer {
    /** El jugador que juega en la izquierda. */
    public static final int IZQUIERDO = 0;
    /** El jugador que juega en la derecha. */
    public static final int DERECHO = 1;
    /** Lo que devuelve getGanador cuando nadie ganó. */
    public static final int EMPATE = -1;

    private Partido partido;
    private final Perfil[] perfiles = {new Perfil(), new Perfil()};
    private boolean puntosEntregados;

    /**
     * Los tipos de partido que se pueden jugar.
     *
     * @return la lista de nombres
     */
    public List<String> getTiposPartido() {
        return Catalogo.getTiposPartido();
    }

    /**
     * Las cantidades que se pueden escoger para un tipo de partido.
     *
     * @param tipo el nombre del tipo de partido, por ejemplo "Por tiempo"
     * @return la lista de minutos o de goles
     * @throws DOPOSoccerException si el tipo no existe
     */
    public List<Integer> getCantidades(String tipo) throws DOPOSoccerException {
        return Catalogo.getCantidades(tipo);
    }

    /**
     * El texto que se muestra para una cantidad.
     *
     * @param tipo el nombre del tipo de partido, por ejemplo "Por tiempo"
     * @param cantidad los minutos o los goles
     * @return el texto, por ejemplo "2 minutos"
     * @throws DOPOSoccerException si el tipo no existe
     */
    public String describirCantidad(String tipo, int cantidad) throws DOPOSoccerException {
        return Catalogo.describirCantidad(tipo, cantidad);
    }

    /**
     * Arranca un partido nuevo. Cada jugador entra con las estadísticas que ha ido mejorando.
     *
     * @param tipo el nombre del tipo de partido, por ejemplo "Por tiempo"
     * @param cantidad los minutos o los goles
     * @throws DOPOSoccerException si el tipo no existe o la cantidad no sirve
     */
    public void nuevoPartido(String tipo, int cantidad) throws DOPOSoccerException {
        TipoPartido tipoPartido = Catalogo.crearTipoPartido(tipo, cantidad);
        partido = new Partido(new Jugador(perfiles[IZQUIERDO].getEstadisticas()),
            new Jugador(perfiles[DERECHO].getEstadisticas()), tipoPartido);
        puntosEntregados = false;
    }

    /**
     * Avanza el partido. Si con eso termina, les entrega los puntos a los jugadores.
     *
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
    public void actualizar(double segundos) {
        partido.actualizar(segundos);
        entregarPuntos();
    }

    /** Pausa el partido. */
    public void pausar() {
        partido.pausar();
    }

    /** Sigue el partido después de una pausa. */
    public void reanudar() {
        partido.reanudar();
    }

    /** Termina el partido antes de tiempo y entrega los puntos. */
    public void terminar() {
        partido.terminar();
        entregarPuntos();
    }

    /**
     * Dice si el partido está en pausa.
     *
     * @return true si está pausado
     */
    public boolean estaPausado() {
        return partido.estaPausado();
    }

    /**
     * Dice si el partido ya terminó.
     *
     * @return true si terminó
     */
    public boolean haTerminado() {
        return partido.haTerminado();
    }

    /**
     * El jugador empieza a caminar hacia la izquierda.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     */
    public void moverIzquierda(int jugador) {
        jugador(jugador).moverIzquierda();
    }

    /**
     * El jugador empieza a caminar hacia la derecha.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     */
    public void moverDerecha(int jugador) {
        jugador(jugador).moverDerecha();
    }

    /**
     * El jugador deja de caminar.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     */
    public void detenerse(int jugador) {
        jugador(jugador).detenerse();
    }

    /**
     * El jugador salta, si está en el suelo.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     */
    public void saltar(int jugador) {
        jugador(jugador).saltar();
    }

    /**
     * El jugador patea.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     */
    public void patear(int jugador) {
        jugador(jugador).patear();
    }

    /**
     * El jugador activa su poder, si tiene la barra llena.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     */
    public void activarPoder(int jugador) {
        jugador(jugador).activarPoder();
    }

    /**
     * Los personajes que se pueden escoger.
     *
     * @return la lista de nombres
     */
    public List<String> getPersonajes() {
        return Catalogo.getPersonajes();
    }

    /**
     * Le pone un personaje al jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @throws DOPOSoccerException si el personaje no existe
     */
    public void elegirPersonaje(int jugador, String personaje) throws DOPOSoccerException {
        jugador(jugador).setPersonaje(Catalogo.crearPersonaje(personaje));
    }

    /**
     * El nombre del poder de un personaje. No hace falta que haya un partido.
     *
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @return el nombre del poder, por ejemplo "Rayo"
     * @throws DOPOSoccerException si el personaje no existe
     */
    public String getNombrePoder(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).getNombrePoder();
    }

    /**
     * Cómo sale el tiro de poder de un personaje.
     *
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @return la descripción del tiro
     * @throws DOPOSoccerException si el personaje no existe
     */
    public String getDescripcionTiro(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).crearTiroPoder().getDescripcion();
    }

    /**
     * Qué le pasa al rival cuando lo golpea el poder de un personaje.
     *
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @return la descripción del efecto
     * @throws DOPOSoccerException si el personaje no existe
     */
    public String getDescripcionEfecto(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).crearEfecto().getDescripcion();
    }

    /**
     * Cuánta vida quita el poder de un personaje, antes de sumarle la mejora de Kick.
     *
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @return el daño base
     * @throws DOPOSoccerException si el personaje no existe
     */
    public double getDanoPoder(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).getDanoTiro();
    }

    /**
     * Cuánto dura el efecto del poder de un personaje.
     *
     * @param personaje el nombre del personaje, por ejemplo "Camerún"
     * @return la duración en segundos
     * @throws DOPOSoccerException si el personaje no existe
     */
    public double getDuracionEfecto(String personaje) throws DOPOSoccerException {
        return Catalogo.crearPersonaje(personaje).crearEfecto().getDuracion();
    }

    /**
     * El personaje que está usando el jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return el nombre del personaje
     */
    public String getPersonaje(int jugador) {
        return jugador(jugador).getPersonaje().getNombre();
    }

    /**
     * Dice si el jugador activó el poder y todavía no lo ha lanzado.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return true si tiene el aura
     */
    public boolean tieneAura(int jugador) {
        return jugador(jugador).tieneAura();
    }

    /**
     * El efecto que está sufriendo el jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return el nombre del efecto, o "Ninguno"
     */
    public String getEfecto(int jugador) {
        return jugador(jugador).getEfecto().getNombre();
    }

    /**
     * Dice si el balón lleva un tiro de poder.
     *
     * @return true si lleva uno
     */
    public boolean balonTienePoder() {
        return partido.getBalon().tienePoder();
    }

    /**
     * El personaje que lanzó el tiro de poder que lleva el balón.
     *
     * @return el nombre del personaje, o null si el balón no lleva poder
     */
    public String getPersonajeDelPoder() {
        if (!balonTienePoder()) {
            return null;
        }
        return partido.getBalon().getPoder().getLanzador().getPersonaje().getNombre();
    }

    /**
     * Cuántos balones falsos hay en la cancha.
     *
     * @return la cantidad; 0 si no hay
     */
    public int getCantidadBalonesFalsos() {
        return partido.getBalonesFalsos().size();
    }

    /**
     * Dónde está un balón falso, de izquierda a derecha.
     *
     * @param indice cuál de los balones falsos, contando desde 0
     * @return la posición horizontal
     */
    public double getXBalonFalso(int indice) {
        return partido.getBalonesFalsos().get(indice).getX();
    }

    /**
     * Dónde está un balón falso, de arriba hacia abajo.
     *
     * @param indice cuál de los balones falsos, contando desde 0
     * @return la posición vertical
     */
    public double getYBalonFalso(int indice) {
        return partido.getBalonesFalsos().get(indice).getY();
    }


    /**
     * El ancho de la cancha.
     *
     * @return el ancho
     */
    public double getAnchoCancha() {
        return partido.getCancha().getAncho();
    }

    /**
     * El alto de la cancha.
     *
     * @return el alto
     */
    public double getAltoCancha() {
        return partido.getCancha().getAlto();
    }

    /**
     * A qué altura está el suelo.
     *
     * @return la posición del suelo
     */
    public double getSuelo() {
        return partido.getCancha().getSuelo();
    }

    /**
     * El ancho de los arcos.
     *
     * @return el ancho
     */
    public double getAnchoArco() {
        return partido.getCancha().getArcoAncho();
    }

    /**
     * El alto de los arcos.
     *
     * @return el alto
     */
    public double getAltoArco() {
        return partido.getCancha().getArcoAlto();
    }

    /**
     * Dónde está el balón, de izquierda a derecha.
     *
     * @return la posición horizontal del centro
     */
    public double getXBalon() {
        return partido.getBalon().getX();
    }

    /**
     * Dónde está el balón, de arriba hacia abajo.
     *
     * @return la posición vertical del centro
     */
    public double getYBalon() {
        return partido.getBalon().getY();
    }

    /**
     * Qué tan rápido va el balón hacia los lados.
     *
     * @return la velocidad horizontal
     */
    public double getVxBalon() {
        return partido.getBalon().getVx();
    }

    /**
     * Qué tan rápido va el balón hacia arriba o abajo.
     *
     * @return la velocidad vertical
     */
    public double getVyBalon() {
        return partido.getBalon().getVy();
    }

    /**
     * El radio del balón.
     *
     * @return el radio
     */
    public double getRadioBalon() {
        return partido.getBalon().getRadio();
    }


    /**
     * Dónde está el jugador, de izquierda a derecha.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return la posición de su borde izquierdo
     */
    public double getXJugador(int jugador) {
        return jugador(jugador).getX();
    }

    /**
     * Dónde está el jugador, de arriba hacia abajo.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return la posición de su borde de arriba
     */
    public double getYJugador(int jugador) {
        return jugador(jugador).getY();
    }

    /**
     * El ancho del jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return el ancho
     */
    public double getAnchoJugador(int jugador) {
        return jugador(jugador).getAncho();
    }

    /**
     * El alto del jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return el alto
     */
    public double getAltoJugador(int jugador) {
        return jugador(jugador).getAlto();
    }

    /**
     * La vida que le queda al jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return la vida actual
     */
    public double getVida(int jugador) {
        return jugador(jugador).getVida();
    }

    /**
     * La vida con la que arranca el jugador, según su nivel de Survival.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return la vida máxima
     */
    public double getVidaMaxima(int jugador) {
        return jugador(jugador).getEstadisticas().vidaMaxima();
    }

    /**
     * Qué tan llena está la barra de poder del jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return un número de 0 a 1
     */
    public double getProgresoPoder(int jugador) {
        return jugador(jugador).getBarra().getProgreso();
    }

    /**
     * Dice si el jugador está aturdido porque se quedó sin vida.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return true si está aturdido
     */
    public boolean estaAturdido(int jugador) {
        return jugador(jugador).estaAturdido();
    }

    /**
     * Dice si el jugador está en medio de una patada.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return true si está pateando
     */
    public boolean estaPateando(int jugador) {
        return jugador(jugador).estaPateando();
    }

    /**
     * Los goles del jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return los goles
     */
    public int getGoles(int jugador) {
        return jugador(jugador).getGoles();
    }

    /**
     * Los puntos que ganó el jugador en este partido.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return los puntos
     */
    public int getPuntos(int jugador) {
        return jugador(jugador).getPuntos();
    }

    /**
     * El texto que va debajo del marcador: el tiempo que queda, la meta de goles o "Muerte súbita".
     *
     * @return el texto
     */
    public String getInformacion(){
        return partido.getTipoPartido().getInformacion(getGoles(IZQUIERDO), getGoles(DERECHO));
    }

    /**
     * Quién va ganando, o quién ganó si ya terminó.
     *
     * @return IZQUIERDO, DERECHO o EMPATE
     */
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

    /**
     * Guarda la partida en un archivo para seguirla después.
     *
     * @param archivo dónde se guarda
     * @throws DOPOSoccerException si no hay partido o no se pudo escribir el archivo
     */
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

    /**
     * Abre una partida guardada. Si algo falla, el partido que se estaba jugando no se pierde.
     *
     * @param archivo el archivo que se va a abrir
     * @throws DOPOSoccerException si no se pudo leer el archivo o no es una partida
     */
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

    /**
     * Las máquinas contra las que se puede jugar.
     *
     * @return la lista de nombres
     */
    public List<String> getMaquinas(){
        return Catalogo.getMaquinas();
    }

    /**
     * Hace que una máquina maneje al jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @param perfil el nombre de la máquina, por ejemplo "Defensiva"
     * @throws DOPOSoccerException si la máquina no existe
     */
    public void ponerMaquina(int jugador, String perfil) throws DOPOSoccerException{
        Controlador maquina = Catalogo.crearMaquina(perfil);   
        Jugador elegido = jugador(jugador);                   
        elegido.setControlador(maquina);        
    }

    /**
     * Dice si el jugador se maneja con el teclado o lo maneja una máquina.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return true si lo maneja una persona
     */
    public boolean usaTeclado(int jugador){
        return jugador(jugador).usaTeclado();
    }

    /**
     * Los nombres de las estadísticas que se pueden mejorar.
     *
     * @return la lista de nombres
     */
    public List<String> getEstadisticas(){
        return perfiles[IZQUIERDO].getEstadisticas().getNombres();
    }

    /**
     * El nivel que tiene el jugador en una estadística.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @param estadistica el nombre de la estadística: Speed, Kick, Jump o Survival
     * @return el nivel, de 1 a 10
     * @throws DOPOSoccerException si la estadística no existe
     */
    public int getNivel(int jugador, String estadistica) throws DOPOSoccerException{
        return perfiles[jugador].getEstadisticas().getNivel(estadistica);
    }

    /**
     * Dice si una estadística ya está en el nivel máximo.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @param estadistica el nombre de la estadística: Speed, Kick, Jump o Survival
     * @return true si ya no se puede subir más
     * @throws DOPOSoccerException si la estadística no existe
     */
    public boolean estaAlMaximo(int jugador, String estadistica) throws DOPOSoccerException{
        return perfiles[jugador].getEstadisticas().estaAlMaximo(estadistica);
    }

    /**
     * Cuántos puntos cuesta subir una estadística un nivel.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @param estadistica el nombre de la estadística: Speed, Kick, Jump o Survival
     * @return el precio en puntos
     * @throws DOPOSoccerException si la estadística no existe
     */
    public int getPrecioMejora(int jugador, String estadistica) throws DOPOSoccerException{
        return perfiles[jugador].precioMejora(estadistica);
    }

    /**
     * Sube un nivel una estadística del jugador y le cobra los puntos.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @param estadistica el nombre de la estadística: Speed, Kick, Jump o Survival
     * @throws DOPOSoccerException si la estadística no existe, ya está al máximo o no alcanzan los
     *                             puntos
     */
    public void mejorar(int jugador, String estadistica) throws DOPOSoccerException{
        perfiles[jugador].mejorar(estadistica);
    }

    /**
     * Los puntos que el jugador ha juntado entre todos los partidos y todavía no ha gastado.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return los puntos disponibles
     */
    public int getPuntosAcumulados(int jugador){
        return perfiles[jugador].getPuntos();
    }

    /**
     * Guarda en un archivo los puntos y las mejoras de los dos jugadores.
     *
     * @param archivo dónde se guarda
     * @throws DOPOSoccerException si no se pudo escribir el archivo
     */
    public void guardarProgreso(File archivo) throws DOPOSoccerException{
        try(FileOutputStream escritura = new FileOutputStream(archivo);
            ObjectOutputStream salida = new ObjectOutputStream(escritura)){
            salida.writeObject(perfiles);
        } catch (IOException e){
            throw new DOPOSoccerException(DOPOSoccerException.ERROR_AL_GUARDAR_PROGRESO, e);
        }
    }

    /**
     * Carga los puntos y las mejoras guardados.
     *
     * @param archivo el archivo con el progreso
     * @throws DOPOSoccerException si no se pudo leer el archivo o no es un progreso
     */
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
