package domain;
import java.io.*;

public class Balon implements Serializable{
    private static final double RADIO = 22.0;
    private static final double REBOTE = 0.75;
    private static final double EMPUJE_CABEZA = 250.0;
    private static final double EMPUJE_LATERAL = 150.0;
    private double x, y, vx, vy;
    private TiroPoder poder;

    public Balon(double x, double y){
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
    }

    public void aplicarImpulso(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }

    public void reiniciar(double x, double y){
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
        poder = null;
    }

    public void cargarPoder(TiroPoder poder){
        this.poder = poder;
    }

    public void quitarPoder(){
        poder = null;
    }

    public boolean tienePoder(){
        return poder != null;
    }

    public TiroPoder getPoder(){
        return poder;
    }

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

    public boolean estaEnElSuelo(Cancha cancha){
        return y + RADIO >= cancha.getSuelo();
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getVx(){
        return vx;
    }

    public double getVy(){
        return vy;
    }

    public double getRadio(){
        return RADIO;
    }

    public double getRebote(){
        return REBOTE;
    }

    public boolean tocaZona(double zonaX, double zonaY, double ancho, double alto) {
    return x + RADIO > zonaX
        && x - RADIO < zonaX + ancho
        && y + RADIO > zonaY
        && y - RADIO < zonaY + alto;
    }

    public boolean toca(Jugador jugador) {
    return tocaZona(jugador.getX(), jugador.getY(), jugador.getAncho(), jugador.getAlto());
    }

    private void aplicarFriccion(double segundos, Cancha cancha){
        Friccion friccion = estaEnElSuelo(cancha) ? cancha.getFriccionSuelo() : cancha.getFriccionAire();
        vx = friccion.frenar(vx, segundos);
    }
}
