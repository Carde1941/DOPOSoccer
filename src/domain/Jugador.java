package domain;

public class Jugador {
    private static final double DANO_BASE_PATADA = 10.0;
    private static final double DURACION_ATURDIMIENTO = 3.0;
    private static final double ANCHO = 70.0;
    private static final double ALTO = 110.0;
    private static final double VELOCIDAD_BASE = 280.0;
    private static final double IMPULSO_SALTO_BASE = 720.0;
    private Estadisticas estadisticas;
    private BarraPoder barra;
    private double vida;
    private int goles, puntos, direccion;
    private double tiempoAturdido, x, y, vy;
    private boolean enSuelo;

    public Jugador(Estadisticas estadisticas){
        this.estadisticas = estadisticas;
        barra = new BarraPoder();
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
        x = Math.max(x,0);
        x = Math.min(x, cancha.getAncho() - ANCHO);

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
