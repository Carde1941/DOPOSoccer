package presentation;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import domain.DOPOSoccer;

/** Las teclas de un jugador. Traduce lo que se presiona en el teclado a órdenes para el juego. */
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

    /**
     * Crea los controles con las teclas escogidas.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @param izquierda la tecla para ir a la izquierda
     * @param derecha la tecla para ir a la derecha
     * @param saltar la tecla para saltar
     * @param patear la tecla para patear
     * @param poder la tecla para activar el poder
     */
    public Controles(int jugador, int izquierda, int derecha, int saltar, int patear, int poder){
        this.jugador = jugador;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.saltar = saltar;
        this.patear = patear;
        this.poder = poder;
    }

    /**
     * Las teclas del jugador de la izquierda: A y D para moverse, W para saltar, espacio para patear y
     * S para el poder.
     *
     * @return los controles
     */
    public static Controles izquierdo(){
        return new Controles(DOPOSoccer.IZQUIERDO, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_SPACE, KeyEvent.VK_S);
    }

    /**
     * Las teclas del jugador de la derecha: las flechas para moverse y saltar, K para patear y la
     * flecha de abajo para el poder.
     *
     * @return los controles
     */
    public static Controles derecho(){
        return new Controles(DOPOSoccer.DERECHO, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_K, KeyEvent.VK_DOWN);
    }

    /**
     * Las teclas de un jugador.
     *
     * @param jugador el jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @return los controles de ese jugador
     */
    public static Controles de(int jugador){
        if (jugador == DOPOSoccer.IZQUIERDO){
            return izquierdo();
        }
        return derecho();
    }

    /**
     * El nombre de una tecla como se lee en el teclado.
     *
     * @param tecla el código de la tecla
     * @return el nombre, por ejemplo "Espacio"
     */
    public static String nombre(int tecla){
        return KeyEvent.getKeyText(tecla);
    }

    /**
     * A qué jugador pertenecen estas teclas.
     *
     * @return IZQUIERDO o DERECHO
     */
    public int getJugador(){
        return jugador;
    }

    /**
     * Qué tecla hace cada acción, para mostrarlas en pantalla.
     *
     * @return cada acción con el nombre de su tecla
     */
    public Map<String, String> getTeclas(){
        Map<String, String> teclas = new LinkedHashMap<>();
        teclas.put(MOVER_IZQUIERDA, nombre(izquierda));
        teclas.put(MOVER_DERECHA, nombre(derecha));
        teclas.put(SALTAR, nombre(saltar));
        teclas.put(PATEAR, nombre(patear));
        teclas.put(PODER, nombre(poder));
        return teclas;
    }

    /**
     * Le dice al juego lo que hay que hacer cuando se presiona una tecla. Si la tecla no es de este
     * jugador, no hace nada.
     *
     * @param tecla el código de la tecla
     * @param juego el juego; la pantalla le pide a él toda la información
     */
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

    /**
     * Cuando se suelta una flecha, el jugador sigue hacia el otro lado si esa tecla sigue presionada;
     * si no, se detiene.
     *
     * @param tecla el código de la tecla
     * @param juego el juego; la pantalla le pide a él toda la información
     */
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

    /**
     * Hace como si se hubieran soltado todas las teclas. Se usa al pausar, para que nadie quede
     * caminando solo.
     *
     * @param juego el juego; la pantalla le pide a él toda la información
     */
    public void soltarTodo(DOPOSoccer juego){
        izquierdaPresionada = false;
        derechaPresionada = false;
        juego.detenerse(jugador);
    }

}
