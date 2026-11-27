package domain;

import java.io.Serializable;

/**
 * Lo que un jugador conserva de un partido a otro: sus puntos y sus estadísticas. Con los puntos
 * compra mejoras.
 */
public class Perfil implements Serializable {
    private static final int PRECIO_BASE = 500;
    private final Estadisticas estadisticas = new Estadisticas();
    private int puntos;

    /**
     * Sus estadísticas.
     *
     * @return las estadísticas
     */
    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    /**
     * Los puntos que tiene para gastar.
     *
     * @return los puntos
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Le suma puntos.
     *
     * @param cantidad cuántos puntos
     */
    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
    }

    /**
     * Cuánto cuesta subir una estadística un nivel. El precio se duplica en cada nivel: 500, 1000,
     * 2000...
     *
     * @param estadistica el nombre de la estadística: Speed, Kick, Jump o Survival
     * @return el precio en puntos
     * @throws DOPOSoccerException si la estadística no existe
     */
    public int precioMejora(String estadistica) throws DOPOSoccerException {
        return PRECIO_BASE * (int) Math.pow(2, estadisticas.getNivel(estadistica) - 1);
    }

    /**
     * Sube una estadística un nivel y descuenta los puntos.
     *
     * @param estadistica el nombre de la estadística: Speed, Kick, Jump o Survival
     * @throws DOPOSoccerException si la estadística no existe, ya está en 10 o no alcanzan los puntos
     */
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
