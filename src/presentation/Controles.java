package presentation;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import domain.DOPOSoccer;

public class Controles {
    public static final int PAUSA = KeyEvent.VK_P;
    public static final int GUARDAR = KeyEvent.VK_G;
    public static final int TERMINAR = KeyEvent.VK_ESCAPE;
    public static final String MOVER_IZQUIERDA = "Mover a la izquierda";
    public static final String MOVER_DERECHA = "Mover a la derecha";
    public static final String SALTAR = "Saltar";
    public static final String PATEAR = "Patear";
    public static final String PODER = "Activar poder";

    private int jugador;
    private int izquierda;
    private int derecha;
    private int saltar;
    private int patear;
    private int poder;
    private boolean izquierdaPresionada;
    private boolean derechaPresionada;

    public Controles(int jugador, int izquierda, int derecha, int saltar, int patear, int poder){
        this.jugador = jugador;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.saltar = saltar;
        this.patear = patear;
        this.poder = poder;
    }

    public static Controles izquierdo(){
        return new Controles(DOPOSoccer.IZQUIERDO, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_SPACE, KeyEvent.VK_S);
    }

    public static Controles derecho(){
        return new Controles(DOPOSoccer.DERECHO, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_K, KeyEvent.VK_DOWN);
    }

    public static Controles de(int jugador){
        if (jugador == DOPOSoccer.IZQUIERDO){
            return izquierdo();
        }
        return derecho();
    }

    public static String nombre(int tecla){
        return KeyEvent.getKeyText(tecla);
    }

    public int getJugador(){
        return jugador;
    }

    public Map<String, String> getTeclas(){
        Map<String, String> teclas = new LinkedHashMap<>();
        teclas.put(MOVER_IZQUIERDA, nombre(izquierda));
        teclas.put(MOVER_DERECHA, nombre(derecha));
        teclas.put(SALTAR, nombre(saltar));
        teclas.put(PATEAR, nombre(patear));
        teclas.put(PODER, nombre(poder));
        return teclas;
    }

    public void presionar(int tecla, DOPOSoccer juego){
        if (tecla == izquierda){
            izquierdaPresionada = true;
            juego.moverIzquierda(jugador);
        } else if (tecla == derecha){
            derechaPresionada = true;
            juego.moverDerecha(jugador);
        } else if (tecla == saltar){
            juego.saltar(jugador);
        } else if (tecla == patear){
            juego.patear(jugador);
        } else if (tecla == poder){
            juego.activarPoder(jugador);
        }
    }

    public void soltar(int tecla, DOPOSoccer juego){
        if (tecla == izquierda){
            izquierdaPresionada = false;
        } else if (tecla == derecha){
            derechaPresionada = false;
        } else{
            return;
        }
        if (izquierdaPresionada){
            juego.moverIzquierda(jugador);
        } else if (derechaPresionada){
            juego.moverDerecha(jugador);
        } else{
            juego.detenerse(jugador);
        }
    }

    public void soltarTodo(DOPOSoccer juego){
        izquierdaPresionada = false;
        derechaPresionada = false;
        juego.detenerse(jugador);
    }

}
