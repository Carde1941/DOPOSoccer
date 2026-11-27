package domain;

import java.util.List;

/** Sabe crear un tipo de partido y qué cantidades ofrecer en la pantalla: minutos o goles. */
public interface CreadorTipoPartido {
    /**
     * Crea el tipo de partido con la cantidad escogida.
     *
     * @param cantidad los minutos o los goles
     * @return el tipo de partido
     * @throws DOPOSoccerException si la cantidad no sirve
     */
    TipoPartido crear(int cantidad) throws DOPOSoccerException;
    /**
     * Las cantidades que se le ofrecen al usuario.
     *
     * @return la lista de cantidades
     */
    List<Integer> getCantidades();
    /**
     * El texto que se muestra para una cantidad.
     *
     * @param cantidad los minutos o los goles
     * @return el texto, por ejemplo "3 goles"
     */
    String describir(int cantidad);
}
