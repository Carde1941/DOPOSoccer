package domain;
import java.io.*;

/** El balón del partido. Sabe dónde está, qué tan rápido va y si lleva un tiro de poder encima. */
public class Balon implements Serializable{
    private static final double RADIO = 22.0;
    private static final double REBOTE = 0.75;
    private static final double EMPUJE_CABEZA = 250.0;
    private static final double EMPUJE_LATERAL = 150.0;
    private double x, y, vx, vy;
    private TiroPoder poder;

    /**
     * Crea el balón quieto en un punto de la cancha.
     *
     * @param x la posición de izquierda a derecha
     * @param y la posición de arriba hacia abajo (en pantalla, y crece hacia abajo)
     */
    public Balon(double x, double y){
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
    }

    /**
     * Le cambia la velocidad al balón de golpe, como cuando alguien lo patea.
     *
     * @param vx la velocidad de izquierda a derecha (negativa va hacia la izquierda)
     * @param vy la velocidad de arriba hacia abajo (negativa va hacia arriba)
     */
    public void aplicarImpulso(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Pone el balón quieto en un punto y le quita el poder que llevara. Se usa para el saque después
     * de un gol.
     *
     * @param x la posición de izquierda a derecha
     * @param y la posición de arriba hacia abajo (en pantalla, y crece hacia abajo)
     */
    public void reiniciar(double x, double y){
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
        poder = null;
    }

    /**
     * Le pone al balón un tiro de poder, que lleva encima hasta que golpee a alguien o se acabe.
     *
     * @param poder el tiro de poder que va a llevar el balón
     */
    public void cargarPoder(TiroPoder poder){
        this.poder = poder;
    }

    /** Le quita al balón el tiro de poder que llevaba. */
    public void quitarPoder(){
        poder = null;
    }

    /**
     * Dice si el balón lleva un tiro de poder encima.
     *
     * @return true si lleva uno
     */
    public boolean tienePoder(){
        return poder != null;
    }

    /**
     * El tiro de poder que lleva el balón.
     *
     * @return el tiro de poder, o null si no lleva ninguno
     */
    public TiroPoder getPoder(){
        return poder;
    }

    /**
     * Mueve el balón un poquito: le aplica la gravedad, lo hace rebotar en el suelo, el techo y las
     * paredes, y lo va frenando. Si lleva un tiro de poder, también le descuenta el tiempo. Si pega en
     * una pared de los lados, pierde el poder.
     *
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     * @param cancha la cancha donde se juega
     */
    public void actualizar(double segundos, Cancha cancha){
        double factorGravedad = 1;
        if(poder != null){
            factorGravedad = poder.getFactorGravedad();
            poder.actualizar(segundos);
            if(poder.haTerminado()){
                poder = null;
            }
        }
        vy += cancha.getGravedad() * factorGravedad * segundos;
        x += vx * segundos;
        y += vy * segundos;

        if(y + RADIO > cancha.getSuelo()){
            y = cancha.getSuelo() - RADIO;
            vy = -vy * REBOTE;
        }

        if(y - RADIO < 0){
            y = RADIO;
            vy = -vy * REBOTE;
        }

        if(x - RADIO < 0){
            x = RADIO;
            vx = -vx * REBOTE;
            poder = null;
        }

        if(x + RADIO > cancha.getAncho()){
            x = cancha.getAncho() - RADIO;
            vx = -vx * REBOTE;
            poder = null;
        }
        aplicarFriccion(segundos, cancha);
    }

/**
 * Hace rebotar el balón si se metió en un rectángulo, por ejemplo un travesaño. Si no lo está tocando,
 * no pasa nada.
 *
 * @param zonaX dónde empieza la zona, de izquierda a derecha
 * @param zonaY dónde empieza la zona, de arriba hacia abajo
 * @param ancho el ancho
 * @param alto el alto
 */
public void rebotarEn(double zonaX, double zonaY, double ancho, double alto){
    double cercanoX = Math.max(zonaX, Math.min(x, zonaX + ancho));
    double cercanoY = Math.max(zonaY, Math.min(y, zonaY + alto));
    double dx = x - cercanoX;
    double dy = y - cercanoY;
    double distancia = Math.sqrt(dx * dx + dy * dy);
    if (distancia >= RADIO){
        return;
    }
    double nx;
    double ny;
    if (distancia==0){
        nx = 0;
        ny = vy > 0 ? -1 : 1;
        cercanoY =vy > 0 ? zonaY : zonaY + alto;
    } else{
        nx = dx / distancia;
        ny = dy / distancia;
    }
    x = cercanoX + nx * RADIO;
    y = cercanoY + ny * RADIO;
    double velocidadNormal = vx * nx + vy * ny;
    if (velocidadNormal <0){
        vx -= (1 + REBOTE) * velocidadNormal * nx;
        vy -= (1 + REBOTE) * velocidadNormal * ny;
    }
}


    /**
     * Hace rebotar el balón si toca a un jugador: si le cae en la cabeza sale hacia arriba, y si lo
     * toca de lado sale hacia ese lado. No rebota en quien lanzó el tiro de poder que lleva.
     *
     * @param jugador el jugador contra el que puede chocar
     */
    public void chocarCon(Jugador jugador){
        if(!toca(jugador) || (poder != null && poder.esDe(jugador))){
            return;
        }
        else{
            if(y < jugador.getY()){
                y = jugador.getY() - RADIO;
                vy = -Math.abs(vy) * REBOTE - EMPUJE_CABEZA;
            } else if(x < jugador.getX() + jugador.getAncho() / 2){
                x = jugador.getX() - RADIO;
                vx = -Math.abs(vx) * REBOTE -EMPUJE_LATERAL;
            } else {
                x = jugador.getX() + jugador.getAncho() + RADIO;
                vx = Math.abs(vx) * REBOTE + EMPUJE_LATERAL;
            }
        }
    }

    /**
     * Dice si el balón está tocando el suelo.
     *
     * @param cancha la cancha donde se juega
     * @return true si está en el suelo
     */
    public boolean estaEnElSuelo(Cancha cancha){
        return y + RADIO >= cancha.getSuelo();
    }

    /**
     * Dónde está el centro del balón, de izquierda a derecha.
     *
     * @return la posición horizontal del centro
     */
    public double getX(){
        return x;
    }

    /**
     * Dónde está el centro del balón, de arriba hacia abajo.
     *
     * @return la posición vertical del centro
     */
    public double getY(){
        return y;
    }

    /**
     * Qué tan rápido va el balón hacia los lados.
     *
     * @return la velocidad horizontal; negativa si va hacia la izquierda
     */
    public double getVx(){
        return vx;
    }

    /**
     * Qué tan rápido va el balón hacia arriba o abajo.
     *
     * @return la velocidad vertical; negativa si va subiendo
     */
    public double getVy(){
        return vy;
    }

    /**
     * El radio del balón.
     *
     * @return el radio
     */
    public double getRadio(){
        return RADIO;
    }

    /**
     * Cuánta velocidad conserva el balón cuando rebota.
     *
     * @return un número entre 0 y 1; 0.75 quiere decir que conserva el 75 %
     */
    public double getRebote(){
        return REBOTE;
    }

    /**
     * Dice si el balón toca un rectángulo de la cancha.
     *
     * @param zonaX dónde empieza la zona, de izquierda a derecha
     * @param zonaY dónde empieza la zona, de arriba hacia abajo
     * @param ancho el ancho
     * @param alto el alto
     * @return true si lo toca
     */
    public boolean tocaZona(double zonaX, double zonaY, double ancho, double alto) {
    return x + RADIO > zonaX
        && x - RADIO < zonaX + ancho
        && y + RADIO > zonaY
        && y - RADIO < zonaY + alto;
    }

    /**
     * Dice si el balón está tocando a un jugador.
     *
     * @param jugador el jugador
     * @return true si lo toca
     */
    public boolean toca(Jugador jugador) {
    return tocaZona(jugador.getX(), jugador.getY(), jugador.getAncho(), jugador.getAlto());
    }

    private void aplicarFriccion(double segundos, Cancha cancha){
        Friccion friccion = estaEnElSuelo(cancha) ? cancha.getFriccionSuelo() : cancha.getFriccionAire();
        vx = friccion.frenar(vx, segundos);
    }
}
