package domain;

import java.util.List;

/** Receta para crear un tipo de partido a partir de una cantidad: minutos o goles*/
public interface CreadorTipoPartido {
    TipoPartido crear(int cantidad) throws DOPOSoccerException;
    List<Integer> getCantidades();
    String describir(int cantidad);
}
