package presentation;

import domain.DOPOSoccer;
import domain.DOPOSoccerException;

/**
 * Lo que se escogió en la pantalla "Arma el picadito": personajes, tipo de partido, máquina y
 * resolución. Al final se usa para crear el partido.
 */
public class ConfiguracionPartido {
    private String personaje1;
    private String personaje2;
    private String tipo = "Por tiempo";
    private String maquina;
    private int ladoMaquina;
    private int cantidad = 2;
    private int ancho = 1024;
    private int alto = 576;

    /**
     * Guarda el personaje de cada jugador.
     *
     * @param personaje1 el personaje del jugador de la izquierda
     * @param personaje2 el personaje del jugador de la derecha
     * @return esta misma configuración, para seguir llenándola
     */
    public ConfiguracionPartido conPersonajes(String personaje1, String personaje2) {
        this.personaje1 = personaje1;
        this.personaje2 = personaje2;
        return this;
    }

    /**
     * Guarda el tipo de partido y su cantidad.
     *
     * @param tipo el nombre del tipo de partido, por ejemplo "Por tiempo"
     * @param cantidad los minutos o los goles
     * @return esta misma configuración
     */
    public ConfiguracionPartido conTipo(String tipo, int cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        return this;
    }

    /**
     * Guarda qué máquina juega y en qué lado.
     *
     * @param lado el lado del jugador: DOPOSoccer.IZQUIERDO o DOPOSoccer.DERECHO
     * @param maquina el nombre de la máquina
     * @return esta misma configuración
     */
    public ConfiguracionPartido conMaquina(int lado, String maquina) {
        this.ladoMaquina = lado;
        this.maquina = maquina;
        return this;
    }

    /**
     * Deja el partido entre dos personas.
     *
     * @return esta misma configuración
     */
    public ConfiguracionPartido sinMaquina() {
        this.maquina = null;
        return this;
    }

    /**
     * Guarda el tamaño de la ventana del juego.
     *
     * @param ancho el ancho de la ventana
     * @param alto el alto de la ventana
     * @return esta misma configuración
     */
    public ConfiguracionPartido conResolucion(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        return this;
    }

    /**
     * Crea el partido en el juego con todo lo que se escogió.
     *
     * @param juego el juego; la pantalla le pide a él toda la información
     * @throws DOPOSoccerException si algo de lo escogido no existe
     */
    public void construir(DOPOSoccer juego) throws DOPOSoccerException {
        juego.nuevoPartido(tipo, cantidad);
        juego.elegirPersonaje(DOPOSoccer.IZQUIERDO, personaje1);
        juego.elegirPersonaje(DOPOSoccer.DERECHO, personaje2);
        if (maquina != null) {
            juego.ponerMaquina(ladoMaquina, maquina);
        }
    }

    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
}