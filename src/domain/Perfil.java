package domain;

import java.io.Serializable;

public class Perfil implements Serializable {
    private static final int PRECIO_BASE = 500;
    private final Estadisticas estadisticas = new Estadisticas();
    private int puntos;

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
    }

    public int precioMejora(String estadistica) throws DOPOSoccerException {
        return PRECIO_BASE * (int) Math.pow(2, estadisticas.getNivel(estadistica) - 1);
    }

    public void mejorar(String estadistica) throws DOPOSoccerException {
        if (estadisticas.estaAlMaximo(estadistica)) {
            throw new DOPOSoccerException(DOPOSoccerException.ESTADISTICA_AL_MAXIMO);
        }
        int precio = precioMejora(estadistica);
        if (puntos < precio) {
            throw new DOPOSoccerException(DOPOSoccerException.PUNTOS_INSUFICIENTES);
        }
        estadisticas.subir(estadistica);
        puntos -= precio;
    }
}
