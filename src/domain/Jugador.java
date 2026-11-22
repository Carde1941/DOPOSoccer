package domain;
import java.io.*;

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
    private double vida;
    private double tiempoAturdido, x, y, vy, tiempoPatada;
    private boolean enSuelo, patadaPendiente;
    private int goles, puntos;
    private transient int direccion;

    public Jugador(Estadisticas estadisticas){
        this.estadisticas = estadisticas;
        barra = new BarraPoder();
        tiro = new TiroParabolico(TIRO_VX, TIRO_VY);
        controlador = new Humano();
        vida = estadisticas.vidaMaxima();
        goles = 0;
        puntos = 0;
        tiempoAturdido = 0;
    }

    public double danoPatada(){
        return DANO_BASE_PATADA * estadisticas.multiplicadorKick();
    }

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

    public boolean estaAturdido(){
        return tiempoAturdido > 0;
    }

    public void actualizar(double segundos){
        barra.actualizar(segundos);
        if(estaAturdido()){
            tiempoAturdido -= segundos;
            if(tiempoAturdido <= 0){
                tiempoAturdido = 0;
                vida = estadisticas.vidaMaxima();
            }
        }
        tiempoPatada = Math.max(tiempoPatada - segundos, 0);
    }

    public void colocar(double x, double y){
        this.x = x;
        this.y = y;
        vy = 0;
        direccion = 0;
        enSuelo = true;
    }

    public void saltar(){
        if(enSuelo && !estaAturdido()){
            vy = -IMPULSO_SALTO_BASE * estadisticas.multiplicadorJump();
            enSuelo = false;
        }
    }

    public void moverse(double segundos, Cancha cancha){
        enSuelo = false;
        if(!estaAturdido()){
            x += direccion * VELOCIDAD_BASE * estadisticas.multiplicadorSpeed() * segundos;
        }
        vy += cancha.getGravedad() * segundos;
        y += vy * segundos;
        if(y + ALTO > cancha.getSuelo()){
            y = cancha.getSuelo() - ALTO;
            vy = 0;
            enSuelo = true;
        }
        limitarA(cancha);
    }

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

    public void patear(){
        if(!estaAturdido() && !estaPateando()){
            tiempoPatada = DURACION_PATADA;
            patadaPendiente = true;
        }
    }

    public boolean estaPateando(){
        return tiempoPatada > 0;
    }

    public boolean consumirPatada(){
        if(patadaPendiente){
            patadaPendiente = false;
            return true;
        }
        return false;
    }

    public void patearBalon(Balon balon, int sentido){
        tiro.impulsar(balon, sentido);
    }

    public boolean ocupaZona(double zonaX, double zonaY, double ancho, double alto){
        return x < zonaX + ancho && x + ANCHO > zonaX && y < zonaY + alto && y + ALTO > zonaY;
    }

    public void setControlador(Controlador controlador){
        this.controlador = controlador;
    }

    public void decidir(Partido partido, double segundos){
        controlador.decidir(this, partido, segundos);
    }

    public boolean usaTeclado(){
        return controlador.usaTeclado();
    }

    public void moverIzquierda(){
        direccion = -1;
    }

    public void moverDerecha(){
        direccion = 1;
    }

    public void detenerse(){
        direccion = 0;
    }

    public void anotarGol(){
        goles += 1;
    }

    public void sumarPuntos(int cantidad){
        puntos += cantidad;
    }

    public void recibirGol(){
        barra.recibirGol();
    }

    public double getVida(){
        return vida;
    }

    public int getGoles(){
        return goles;
    }

    public int getPuntos(){
        return puntos;
    }

    public BarraPoder getBarra(){
        return barra;
    }

    public Estadisticas getEstadisticas(){
        return estadisticas;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getAncho(){
        return ANCHO;
    }

    public double getAlto(){
        return ALTO;
    }

    public boolean estaEnSuelo(){
        return enSuelo;
    }
}
