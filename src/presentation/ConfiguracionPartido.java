package presentation;

import domain.DOPOSoccer;
import domain.DOPOSoccerException;

/** Builder: la pantalla "Arma el picadito" llena las partes y al final se construye el partido. */
public class ConfiguracionPartido {
    private String personaje1;
    private String personaje2;
    private String tipo = "Por tiempo";
    private String maquina;
    private int ladoMaquina;
    private int cantidad = 2;
    private int ancho = 1024;
    private int alto = 576;

    public ConfiguracionPartido conPersonajes(String personaje1, String personaje2) {
        this.personaje1 = personaje1;
        this.personaje2 = personaje2;
        return this;
    }

    public ConfiguracionPartido conTipo(String tipo, int cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        return this;
    }

    public ConfiguracionPartido conMaquina(int lado, String maquina) {
        this.ladoMaquina = lado;
        this.maquina = maquina;
        return this;
    }

    public ConfiguracionPartido sinMaquina() {
        this.maquina = null;
        return this;
    }

    public ConfiguracionPartido conResolucion(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        return this;
    }

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