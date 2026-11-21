package presentation;

import domain.DOPOSoccer;
import domain.DOPOSoccerException;

/** Builder: la pantalla "Arma el picadito" llena las partes y al final se construye el partido. */
public class ConfiguracionPartido {
    private Pais pais1 = Pais.NIGERIA;
    private Pais pais2 = Pais.CAMERUN;
    private boolean porTiempo = true;
    private int cantidad = 2;
    private int ancho = 1024;
    private int alto = 576;

    public ConfiguracionPartido conPaises(Pais pais1, Pais pais2) {
        this.pais1 = pais1;
        this.pais2 = pais2;
        return this;
    }

    public ConfiguracionPartido porTiempo(int minutos) {
        this.porTiempo = true;
        this.cantidad = minutos;
        return this;
    }

    public ConfiguracionPartido porGoles(int goles) {
        this.porTiempo = false;
        this.cantidad = goles;
        return this;
    }

    public ConfiguracionPartido conResolucion(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        return this;
    }

    public void construir(DOPOSoccer juego) throws DOPOSoccerException {
        if (porTiempo) {
            juego.nuevoPartidoPorTiempo(cantidad);
        } else {
            juego.nuevoPartidoPorGoles(cantidad);
        }
    }

    public Pais getPais1() { return pais1; }
    public Pais getPais2() { return pais2; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
}