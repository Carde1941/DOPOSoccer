package presentation;

import domain.DOPOSoccer;
import domain.DOPOSoccerException;

/** Builder: la pantalla "Arma el picadito" llena las partes y al final se construye el partido. */
public class ConfiguracionPartido {
    private Pais pais1 = Pais.NIGERIA;
    private Pais pais2 = Pais.CAMERUN;
    private String tipo = "Por tiempo";
    private String maquina;
    private int cantidad = 2;
    private int ancho = 1024;
    private int alto = 576;

    public ConfiguracionPartido conPaises(Pais pais1, Pais pais2) {
        this.pais1 = pais1;
        this.pais2 = pais2;
        return this;
    }

    public ConfiguracionPartido conTipo(String tipo, int cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        return this;
    }

    public ConfiguracionPartido conMaquina(String maquina) {
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
        if (maquina != null) {
            juego.ponerMaquina(DOPOSoccer.DERECHO, maquina);
        }
    }

    public Pais getPais1() { return pais1; }
    public Pais getPais2() { return pais2; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
}