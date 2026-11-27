package domain;
import java.io.*;

/**
 * Un jugador en la cancha: dónde está, cuánta vida tiene, sus goles y puntos, su personaje, su barra
 * de poder, el efecto que tenga encima y quién lo maneja.
 */
public class Jugador implements Serializable{
    private static final double DANO_BASE_PATADA = 10.0;
    private static final double DURACION_ATURDIMIENTO = 3.0;
    private static final double ANCHO = 70.0;
    private static final double ALTO = 110.0;
    private static final double VELOCIDAD_BASE = 280.0;
    private static final double IMPULSO_SALTO_BASE = 720.0;
    private static final double DURACION_PATADA = 0.25;
    private static final double TIRO_VX = 650.0;
    private static final double TIRO_VY = 750.0;
    private Estadisticas estadisticas;
    private BarraPoder barra;
    private Tiro tiro;
    private Controlador controlador;
    private Personaje personaje;
    private Efecto efecto;
    private double vida;
    private double tiempoAturdido, x, y, vy, tiempoPatada;
    private boolean enSuelo, patadaPendiente, conAura;
    private int goles, puntos;
    private transient int direccion;

    /**
     * Crea un jugador con Nigeria como personaje.
     *
     * @param estadisticas las estadísticas del jugador
     */
    public Jugador(Estadisticas estadisticas){
        this(estadisticas, new Nigeria());
    }

    /**
     * Crea un jugador con el personaje escogido. Arranca con la vida llena, sin efecto y manejado por
     * una persona.
     *
     * @param estadisticas las estadísticas del jugador
     * @param personaje el personaje
     */
    public Jugador(Estadisticas estadisticas, Personaje personaje){
        this.estadisticas = estadisticas;
        this.personaje = personaje;
        barra = new BarraPoder();
        tiro = new TiroParabolico(TIRO_VX, TIRO_VY);
        controlador = new Humano();
        efecto = new SinEfecto();
        vida = estadisticas.vidaMaxima();
        goles = 0;
        puntos = 0;
        tiempoAturdido = 0;
    }

    /**
     * Cuánta vida quita una patada normal, según el nivel de Kick.
     *
     * @return el daño
     */
    public double danoPatada(){
        return DANO_BASE_PATADA * estadisticas.multiplicadorKick();
    }

    /**
     * Le quita vida al jugador. Si llega a cero, queda aturdido unos segundos. Si ya está aturdido, no
     * le pasa nada.
     *
     * @param dano cuánta vida le quita
     */
    public void recibirDano(double dano){
        if(estaAturdido()){
            return;
        } else{
            vida = Math.max(vida - dano, 0);
            if(vida == 0){
                tiempoAturdido = DURACION_ATURDIMIENTO;
            }
        }

    }

    /**
     * Dice si el jugador está aturdido.
     *
     * @return true si está aturdido
     */
    public boolean estaAturdido(){
        return tiempoAturdido > 0;
    }

    /**
     * Avanza todo lo del jugador que depende del tiempo: la barra de poder, el efecto, el aturdimiento
     * y la patada. Cuando el aturdimiento se acaba, recupera toda la vida.
     *
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
    public void actualizar(double segundos){
        barra.actualizar(segundos);
        efecto.actualizar(segundos);
        if(efecto.haTerminado()){
            efecto = new SinEfecto();
        }
        if(estaAturdido()){
            tiempoAturdido -= segundos;
            if(tiempoAturdido <= 0){
                tiempoAturdido = 0;
                vida = estadisticas.vidaMaxima();
            }
        }
        tiempoPatada = Math.max(tiempoPatada - segundos, 0);
    }

    /**
     * Pone al jugador en un punto, quieto y en el suelo. Se usa para el saque.
     *
     * @param x la posición de izquierda a derecha
     * @param y la posición de arriba hacia abajo (en pantalla, y crece hacia abajo)
     */
    public void colocar(double x, double y){
        this.x = x;
        this.y = y;
        vy = 0;
        direccion = 0;
        enSuelo = true;
    }

    /** Salta, si está en el suelo y nada se lo impide. */
    public void saltar(){
        if(enSuelo && !estaAturdido() && efecto.puedeSaltar()){
            vy = -IMPULSO_SALTO_BASE * estadisticas.multiplicadorJump();
            enSuelo = false;
        }
    }

    /**
     * Mueve al jugador según hacia dónde camina, la gravedad y su efecto, sin dejarlo salirse de la
     * cancha.
     *
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     * @param cancha la cancha donde se juega
     */
    public void moverse(double segundos, Cancha cancha){
        enSuelo = false;
        if(!estaAturdido() && efecto.puedeMoverse()){
            x += direccion * VELOCIDAD_BASE * estadisticas.multiplicadorSpeed() * efecto.getFactorVelocidad() * segundos;
        }
        x += efecto.getArrastre() * segundos;
        vy += cancha.getGravedad() * segundos;
        y += vy * segundos;
        if(y + ALTO > cancha.getSuelo()){
            y = cancha.getSuelo() - ALTO;
            vy = 0;
            enSuelo = true;
        }
        limitarA(cancha);
    }

    /**
     * Si los dos jugadores se están tocando, los separa para que no se atraviesen.
     *
     * @param otro el otro jugador
     * @param cancha la cancha donde se juega
     */
    public void chocarCon(Jugador otro, Cancha cancha){
        if(!ocupaZona(otro.x, otro.y, otro.getAncho(), otro.getAlto())){
            return;
        }
        double solapeX = Math.min(x + ANCHO, otro.x + otro.getAncho()) - Math.max(x, otro.x);
        double solapeY = Math.min(y + ALTO, otro.y + otro.getAlto()) - Math.max(y, otro.y);
        if(solapeY < solapeX){
            Jugador arriba = (y < otro.y) ? this : otro;
            Jugador abajo = (arriba == this) ? otro : this;
            arriba.pararseSobre(abajo);
        } else {
            Jugador izquierdo = (x <= otro.x) ? this : otro;
            Jugador derecho = (izquierdo == this) ? otro : this;
            separar(izquierdo, derecho, solapeX, cancha);
        }
    }

    private void pararseSobre(Jugador otro){
        y = otro.y - ALTO;
        vy = 0;
        enSuelo = true;
    }

    private static void separar(Jugador izquierdo, Jugador derecho, double solape, Cancha cancha){
        izquierdo.x -= solape / 2;
        derecho.x += solape / 2;
        izquierdo.limitarA(cancha);
        derecho.limitarA(cancha);
        if(izquierdo.x + izquierdo.getAncho() > derecho.x){
            if(izquierdo.x <= 0){
                derecho.x = izquierdo.x + izquierdo.getAncho();
            } else {
                izquierdo.x = derecho.x - izquierdo.getAncho();
            }
        }
    }

    private void limitarA(Cancha cancha){
        x = Math.max(x, 0);
        x = Math.min(x, cancha.getAncho() - ANCHO);
    }

    /** Empieza una patada, si no está aturdido, no está pateando ya y su efecto lo deja. */
    public void patear(){
        if(!estaAturdido() && !estaPateando() && efecto.puedePatear()){
            tiempoPatada = DURACION_PATADA;
            patadaPendiente = true;
        }
    }

    /**
     * Dice si el jugador está en medio de una patada.
     *
     * @return true si está pateando
     */
    public boolean estaPateando(){
        return tiempoPatada > 0;
    }

    /**
     * Avisa una sola vez que hay una patada nueva, para que el partido la revise solo una vez.
     *
     * @return true si había una patada sin revisar
     */
    public boolean consumirPatada(){
        if(patadaPendiente){
            patadaPendiente = false;
            return true;
        }
        return false;
    }

    /**
     * Patea el balón con la patada normal.
     *
     * @param balon el balón
     * @param sentido 1 si sale hacia la derecha y -1 si sale hacia la izquierda
     */
    public void patearBalon(Balon balon, int sentido){
        tiro.impulsar(balon, sentido);
    }

    /** Activa el poder: si la barra está llena, el jugador queda con el aura y la barra se vacía. */
    public void activarPoder(){
        if(barra.estaLlena() && !conAura && !estaAturdido()){
            conAura = true;
            barra.vaciar();
        }
    }

    /**
     * Dice si el jugador tiene el poder activado y listo para lanzar.
     *
     * @return true si tiene el aura
     */
    public boolean tieneAura(){
        return conAura;
    }

    /**
     * Lanza el tiro de poder de su personaje: impulsa el balón y le carga el daño y el efecto que le
     * va a dejar al rival.
     *
     * @param balon el balón
     * @param sentido 1 si sale hacia la derecha y -1 si sale hacia la izquierda
     */
    public void lanzarPoder(Balon balon, int sentido){
        conAura = false;
        Tiro tiroPoder = personaje.crearTiroPoder();
        tiroPoder.impulsar(balon, sentido);
        double dano = personaje.getDanoTiro() * estadisticas.multiplicadorKick();
        balon.cargarPoder(new TiroPoder(this, dano, personaje.crearEfecto(), tiroPoder.getFactorGravedad()));
    }

    /**
     * Le pone un efecto al jugador. Si ya tenía uno, el nuevo lo reemplaza.
     *
     * @param nuevo el efecto nuevo
     * @param partido el partido que se está jugando
     */
    public void aplicarEfecto(Efecto nuevo, Partido partido){
        efecto = nuevo;
        efecto.alEmpezar(this, partido);
    }

    /**
     * Lanza al jugador hacia arriba.
     *
     * @param impulso qué tan fuerte sale hacia arriba
     */
    public void lanzarArriba(double impulso){
        vy = -impulso;
        enSuelo = false;
    }

    /**
     * Dice si el jugador toca un rectángulo de la cancha.
     *
     * @param zonaX dónde empieza la zona, de izquierda a derecha
     * @param zonaY dónde empieza la zona, de arriba hacia abajo
     * @param ancho el ancho
     * @param alto el alto
     * @return true si lo toca
     */
    public boolean ocupaZona(double zonaX, double zonaY, double ancho, double alto){
        return x < zonaX + ancho && x + ANCHO > zonaX && y < zonaY + alto && y + ALTO > zonaY;
    }

    /**
     * Cambia quién maneja al jugador.
     *
     * @param controlador quién va a manejar al jugador: una persona o una máquina
     */
    public void setControlador(Controlador controlador){
        this.controlador = controlador;
    }

    /**
     * Le pregunta a su controlador qué hacer.
     *
     * @param partido el partido que se está jugando
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
    public void decidir(Partido partido, double segundos){
        controlador.decidir(this, partido, segundos);
    }

    /**
     * Dice si al jugador lo maneja una persona.
     *
     * @return true si se maneja con el teclado
     */
    public boolean usaTeclado(){
        return controlador.usaTeclado();
    }

    /** Empieza a caminar hacia la izquierda. */
    public void moverIzquierda(){
        direccion = -1;
    }

    /** Empieza a caminar hacia la derecha. */
    public void moverDerecha(){
        direccion = 1;
    }

    /** Deja de caminar. */
    public void detenerse(){
        direccion = 0;
    }

    /** Le suma un gol. */
    public void anotarGol(){
        goles += 1;
    }

    /**
     * Le suma puntos.
     *
     * @param cantidad cuántos puntos
     */
    public void sumarPuntos(int cantidad){
        puntos += cantidad;
    }

    /** Le avisa que le metieron un gol, para darle el empujón en la barra de poder. */
    public void recibirGol(){
        barra.recibirGol();
    }

    /**
     * La vida que le queda.
     *
     * @return la vida
     */
    public double getVida(){
        return vida;
    }

    /**
     * Los goles que lleva.
     *
     * @return los goles
     */
    public int getGoles(){
        return goles;
    }

    /**
     * Los puntos que ganó en el partido.
     *
     * @return los puntos
     */
    public int getPuntos(){
        return puntos;
    }

    /**
     * Su barra de poder.
     *
     * @return la barra
     */
    public BarraPoder getBarra(){
        return barra;
    }

    /**
     * Sus estadísticas.
     *
     * @return las estadísticas
     */
    public Estadisticas getEstadisticas(){
        return estadisticas;
    }

    /**
     * Su personaje.
     *
     * @return el personaje
     */
    public Personaje getPersonaje(){
        return personaje;
    }

    /**
     * Le cambia el personaje.
     *
     * @param personaje el personaje nuevo
     */
    public void setPersonaje(Personaje personaje){
        this.personaje = personaje;
    }

    /**
     * El efecto que tiene encima.
     *
     * @return el efecto; nunca es null
     */
    public Efecto getEfecto(){
        return efecto;
    }

    /**
     * Dónde está su borde izquierdo.
     *
     * @return la posición horizontal
     */
    public double getX(){
        return x;
    }

    /**
     * Dónde está su borde de arriba.
     *
     * @return la posición vertical
     */
    public double getY(){
        return y;
    }

    /**
     * Su ancho.
     *
     * @return el ancho
     */
    public double getAncho(){
        return ANCHO;
    }

    /**
     * Su alto.
     *
     * @return el alto
     */
    public double getAlto(){
        return ALTO;
    }

    /**
     * Dice si está parado en el suelo.
     *
     * @return true si está en el suelo
     */
    public boolean estaEnSuelo(){
        return enSuelo;
    }
}
